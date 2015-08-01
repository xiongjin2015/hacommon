package com.haha.common.cache;

import android.content.Context;

public abstract class HaCache {
    
    /*singleton instance of cache*/
    private static HaCache cache;
    
    public static HaCache getInstance(){
        if(cache == null){
            cache = new HaCacheImpl();
        }
        return cache;
    }
    
    /**
     * initialize the cache object
     */
    public abstract void init(Context context);
    
    /**
     * put url content to cache
     * @param url
     * @param content
     */
    public abstract void put(String url, String content);
    
    /**
     * get url content from cache asynchronized
     * @param url
     * @param handler
     * @return
     * true if hit the cache, otherwise false returned
     */
    public abstract boolean get(String url, HaCacheHandler handler);

}
