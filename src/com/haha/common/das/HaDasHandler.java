
package com.haha.common.das;

import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.haha.common.cache.HaCache;
import com.haha.common.cache.HaCacheHandler;
import com.haha.common.das.HaHandler.EResp;
import com.haha.common.das.HaHandler.SResp;
import com.haha.common.logger.Logcat;
import com.haha.http.HaHttpHandler;
import com.haha.http.HaHttpRequest;
import com.haha.http.HaHttpResponse;

public class HaDasHandler extends HaHttpHandler implements HaCacheHandler {

    private final static String TAG = "HaDasHandler";

    /* handler for send message to relate activity */
    private HaHandler handler;

    /* for parse the response to entity */
    private Class<?> entityClass;

    /* flag indicate if network access return success */
    private boolean networkAccessSuccess = false;

    public HaDasHandler(HaHandler handler, Class<?> entityClass) {
        this.handler = handler;
        this.entityClass = entityClass;
    }

    /**
     * for parse the response content to entity object
     * 
     * @param content: response content
     * @return entity object parsed
     * @throws Exception
     */
    private Object onParse(final String content) throws Exception {
        if (this.entityClass == null)
            return null;

        if (isJsonArray(content)) {
            return JSON.parseArray(content, this.entityClass);
        } else {
            return JSON.parseObject(content, this.entityClass);
        }

    }

    /**
     * check if the content is an json array
     * 
     * @param content
     * @return true if json array, otherwise return false
     */
    private boolean isJsonArray(String content) {
        if (content.matches("^\\s*\\[.*")) {
            return true;
        }

        return false;
    }

    /**
     * notify the invoker that the request has success,which means the http response code is 200 ok
     * 
     * @param req:the http request
     * @param resp:the relate response
     */
    @Override
    public void onSuccess(HaHttpRequest req, HaHttpResponse resp) {
        try {
            /* TODO: decrypt the response message */

            /* decode the data to string */
            String charset = "UTF-8";
            String content = new String(resp.getContent(), charset);

            /* parse the data to entity object */
            Object entity = this.onParse(content);

            /* check if success response */
            if (entity == null || resp.getCode() != 200) {

                this.handler.processFailed(new EResp(req.getUrlString(), HaDasError.ERROR_REQUEST,
                        resp.getCode(), resp.getMsg()));
                Logcat.d(
                        TAG,
                        "query url: " + req.getUrlString() + " success, respcode:"
                                + resp.getCode() + ", msg:" + resp.getMsg() + "time used:"
                                + resp.getTimeUsed());
                return;
            }

            /* create the response message object */
            SResp sresp = new SResp(SResp.Type.SERVER, false, req.getUrlString(),
                    resp.getTimeUsed(), entity);
            this.handler.processSuccess(sresp);

            /* cache the response data */
            HaCache.getInstance().put(req.getUrlString(), content);

            /* change the network access state flag */
            this.networkAccessSuccess = true;

            Logcat.d(
                    TAG,
                    "query url: " + req.getUrlString() + " success, time used:"
                            + resp.getTimeUsed());
        } catch (Exception e) {
            this.handler.processFailed(new EResp(req.getUrlString(), HaDasError.ERROR_RESPONSE,
                    resp
                            .getCode(), e.getMessage()));
            Logcat.d(TAG, "query url: " + req.getUrlString() + " failed, errMsg:" + e.getMessage());
        }
    }

    /**
     * notify the invoker that the request has failed, which means the there is response, but the
     * response code is not 200 OK
     * 
     * @param req: the http request
     * @param resp: the relate response
     */
    @Override
    public void onFailed(HaHttpRequest req, HaHttpResponse resp) {
        this.handler.processFailed(new EResp(req.getUrlString(), HaDasError.ERROR_RESPONSE, resp
                .getCode(), resp.getMsg()));
        Logcat.d(TAG,
                "query url: " + req.getUrlString() + " failed. response code:" + resp.getCode()
                        + ", message:" + resp.getMsg() + ", time used:" + resp.getTimeUsed());
    }

    /**
     * notify the invoker that the request has an error, which means the request has not executed
     * 
     * @param req: the http request
     * @param errMsg: the relate error message
     */
    @Override
    public void onError(HaHttpRequest req, String errMsg) {
        this.handler.processFailed(new EResp(req.getUrlString(), HaDasError.ERROR_NETWORK, 499,
                errMsg));
        Logcat.d(TAG, "query url: " + req.getUrlString() + " failed, errMsg:" + errMsg);
    }

    @Override
    public void onRetry(HaHttpRequest req, String reason) {
        Logcat.d(TAG, "request: " + req.getUrlString() + " failed, reason: " + reason
                + ", will retry later.");
    }

    @Override
    public void onSCache(SCache scache) {
        try {
            /* if network access has succeed, just ignore the cache data */
            if (this.networkAccessSuccess) {
                return;
            }

            /* parse the data to entity object */
            Object entity = this.onParse(scache.getContent());

            /* create the response statistic object */
            SResp sresp = new SResp(SResp.Type.CACHE, scache.isExpired(), scache.getUrl(),
                    scache.getTimeUsed(), entity);
            this.handler.processSuccess(sresp);

            Logcat.d(
                    TAG,
                    "query cache: " + scache.getUrl() + " success, time used:"
                            + scache.getTimeUsed());
        } catch (Exception e) {
            this.handler.processFailed(new EResp(scache.getUrl(), HaDasError.ERROR_CACHE, 400, e
                    .getMessage()));
            Logcat.d(TAG, "query cache: " + scache.getUrl() + " failed, errMsg:" + e.getMessage()
                    + ", time used:" + scache.getTimeUsed());
        }
    }

    @Override
    public void onECache(ECache ecache) {
        Logcat.d(TAG, "query cache: " + ecache.getUrl() + " failed. errCode:" + ecache.getErrCode()
                + ", errMsg:" + ecache.getErrMsg() + ", time used:" + ecache.getTimeUsed());
    }

}
