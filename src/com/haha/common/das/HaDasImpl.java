
package com.haha.common.das;

import android.content.Context;

import com.haha.common.logger.Logcat;
import com.haha.http.HaHttp;
import com.haha.http.HaHttpClient;
import com.haha.http.HaHttpParams;

public class HaDasImpl extends HaDas {

    private final static String TAG = "HaDasImpl";

    private boolean useCache = true;

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get(String url, Class<?> entityClass, int maxRetryCount, HaHandler handler)
            throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if(this.useCache){
            boolean hit = true;
            if(!hit){
                this.httpclient.get(url, maxRetryCount, dasHandler);    
            }
        }else{
            this.httpclient.get(url, maxRetryCount, dasHandler);
        }
        
        Logcat.d(TAG, "request: "+url);
        return url;
    }
    
    @Override
    public String get(String url, Class<?> entityClass, int maxRetryCount, HaHandler handler,
            boolean enableCache) throws Exception {
        HaDasHandler dasHandler = new HaDasHandler(handler, entityClass);
        if(this.useCache && enableCache){
            boolean hit = true;
            if(!hit){
                this.httpclient.get(url, maxRetryCount, dasHandler);    
            }
        }else{
            this.httpclient.get(url, maxRetryCount, dasHandler);
        }
        
        Logcat.d(TAG, "request: "+url);
        return url;
    }
    

    private synchronized String buildUrl(HaDasReq type, HaHttpParams params) throws Exception {
        String url = type.getPath() + "?" + HaHttpParams.newParams().mergeToEnd(params).encode();
        return url;
    }



}
