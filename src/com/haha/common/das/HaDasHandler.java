
package com.haha.common.das;

import com.alibaba.fastjson.JSON;
import com.haha.common.cache.HaCacheHandler;
import com.haha.http.HaHttpHandler;
import com.haha.http.HaHttpRequest;
import com.haha.http.HaHttpResponse;

public class HaDasHandler extends HaHttpHandler implements HaCacheHandler {

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
    

    @Override
    public void onSuccess(HaHttpRequest arg0, HaHttpResponse arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFailed(HaHttpRequest arg0, HaHttpResponse arg1) {
        // TODO Auto-generated method stub

    }
    

    @Override
    public void onError(HaHttpRequest arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRetry(HaHttpRequest arg0, String arg1) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSCache(SCache scache) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onECache(ECache ecache) {
        // TODO Auto-generated method stub

    }

}
