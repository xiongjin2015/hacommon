
package com.haha.common.das;

import android.content.Context;
import com.haha.http.HaHttpParams;

/**
 * @author xj
 */
public abstract class HaDas {

    /**
     * single instance object
     */
    private static HaDas mInstance;

    public static HaDas getInstance() {
        if (mInstance == null)
            mInstance = new HaDasImpl();
        return mInstance;
    }

    /**
     * initialize the public parameters for all request, must not be null this method must be
     * invoked before any other method to be used
     */
    public abstract void init(Context context);

    /**
     * request data by the specified type with relate parameters, the handler will be recalled when
     * the request finished or failed.
     * 
     * @param type: type of the request
     * @param params: parameters for the request
     * @param handler: recall handler after request finished
     * @throws Exception
     */
    public abstract String get(HaDasReq type, HaHttpParams params, HaHandler handler)
            throws Exception;

    /**
     * request data by the specified type with relate parameters, the handler will be recalled when
     * the request finished or failed.
     * 
     * @param type: type of the request
     * @param params: parameters for the request
     * @param handler: recall handler after request finished
     * @param disableCache: disable cache flag
     * @throws Exception
     */
    public abstract String get(HaDasReq type, HaHttpParams params, HaHandler handler,
            boolean enableCache) throws Exception;

    /**
     * request data by the specified type with relate parameters, the handler will be recalled when
     * the request finished or failed.
     * 
     * @param url
     * @param params
     * @param entityClass
     * @param handler
     * @throws Exception
     */
    public abstract String get(String url, Class<?> entityClass, HaHandler handler)
            throws Exception;

    /**
     * request data by the specified type with relate parameters, the handler will be recalled when
     * the request finished or failed.
     * 
     * @param url
     * @param params
     * @param entityClass
     * @param handler
     * @throws Exception
     */
    public abstract String get(String url, Class<?> entityClass, int maxRetryCount,
            HaHandler handler) throws Exception;

    /**
     * request data by the specified type with relate parameters, the handler will be recalled when
     * the request finished or failed.
     * 
     * @param url
     * @param params
     * @param entityClass
     * @param handler
     * @param enbleCache
     * @throws Exception
     */
    public abstract String get(String url, Class<?> entityClass, int maxRetryCount,
            HaHandler handler, boolean enableCache) throws Exception;

    /**
     * enable cache
     */
    public abstract void enableCache();

    /**
     * disable cache
     */
    public abstract void disableCache();
}
