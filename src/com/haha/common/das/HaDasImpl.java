
package com.haha.common.das;

import android.content.Context;

import com.haha.common.cache.HaCache;
import com.haha.common.logger.Logcat;
import com.haha.http.HaHttp;
import com.haha.http.HaHttpClient;
import com.haha.http.HaHttpParams;

public class HaDasImpl extends HaDas {

    private final static String TAG = "HaDasImpl";

    private boolean useCache = true; // defalut true:use Cache;

    /**
     * public parameters for all request
     */
    // private HaHttpParams publicParams = null;

    /**
     * http client object
     */
    private HaHttpClient httpclient = HaHttp.newHttpClient();

    @Override
    public void init(Context context) {
        // TODO Auto-generated method stub
    }

    @Override
    public String get(HaDasReq type, HaHttpParams params, HaHandler handler) throws Exception {
        String url = this.buildUrl(type, params);

        this.get(url, type.getEntityClass(), type.getMaxRetryCount(), handler);

        return url;
    }

    @Override
    public String get(HaDasReq type, HaHttpParams params, HaHandler handler, boolean enableCache)
            throws Exception {
        String url = this.buildUrl(type, params);

        this.get(url, type.getEntityClass(), type.getMaxRetryCount(), handler, enableCache);

        return url;
    }

    @Override
    public String get(String url, Class<?> entityClass, HaHandler handler) throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if (this.useCache) {
            boolean hit = HaCache.getInstance().get(url, dasHandler);
            if (!hit) {
                this.httpclient.get(url, dasHandler);
            }
        } else {
            this.httpclient.get(url, dasHandler);
        }

        Logcat.d(TAG, "request: " + url);
        return url;
    }

    @Override
    public String get(String url, Class<?> entityClass, int maxRetryCount, HaHandler handler)
            throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if (this.useCache) {
            boolean hit = HaCache.getInstance().get(url, dasHandler);
            if (!hit) {
                this.httpclient.get(url, maxRetryCount, dasHandler);
            }
        } else {
            this.httpclient.get(url, maxRetryCount, dasHandler);
        }

        Logcat.d(TAG, "request: " + url);
        return url;
    }

    @Override
    public String get(String url, Class<?> entityClass, int maxRetryCount, HaHandler handler,
            boolean enableCache) throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if (this.useCache && enableCache) {
            boolean hit = HaCache.getInstance().get(url, dasHandler);
            if (!hit) {
                this.httpclient.get(url, maxRetryCount, dasHandler);
            }
        } else {
            this.httpclient.get(url, maxRetryCount, dasHandler);
        }

        Logcat.d(TAG, "request: " + url);
        return url;
    }

    private synchronized String buildUrl(HaDasReq type, HaHttpParams params) throws Exception {
        String url = type.getPath() + "?" + HaHttpParams.newParams().mergeToEnd(params).encode();
        return url;
    }
    
    /**
     * post方式
     * 参数格式：json
     * @param type
     * @param params
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public String post(HaDasReq type, HaJsonHttpParams params, HaHandler handler) throws Exception {
        String url = this.buildUrl(type, params);

        this.post(url, type.getEntityClass(), type.getMaxRetryCount(), handler);

        return url;
    }
    
    @Override
    public String post(String url, Class<?> entityClass, int maxRetryCount, HaHandler handler)
            throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if (this.useCache) {
            boolean hit = HaCache.getInstance().get(url, dasHandler);
            if (!hit) {
                this.httpclient.post(url, maxRetryCount, dasHandler);
            }
        } else {
            this.httpclient.post(url, maxRetryCount, dasHandler);
        }
        return url;
    }
    
    /**
     * 拼接json串
     * @param type
     * @param params
     * @return
     * @throws Exception
     */
    private synchronized String buildUrl(HaDasReq type, HaJsonHttpParams params) throws Exception {
        String url = type.getPath() + "?" + params.build();
        return url;
    }

    @Override
    public void enableCache() {
        this.useCache = true;
    }

    @Override
    public void disableCache() {
        this.useCache = false;
    }

}
