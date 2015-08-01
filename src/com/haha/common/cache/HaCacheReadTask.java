package com.haha.common.cache;

import com.haha.common.cache.HaCacheHandler.ECache;
import com.haha.common.cache.HaCacheHandler.SCache;
import com.haha.common.logger.Logcat;

/**
 * task of reading json from cahce 
 * @author xj
 *
 */
public class HaCacheReadTask implements Runnable {
    
    private final static String TAG = "HaCacheReadTask";
    
    private String url;
    private HaCacheHandler handler;
    private boolean expired;
    
    public HaCacheReadTask(String url, HaCacheHandler handler, boolean expired){
        this.url = url;
        this.handler = handler;
        this.expired = expired;
    }

    @Override
    public void run() {    
        long stime = System.currentTimeMillis();
        try{           
            String content = HaCacheFiles.getInstance().read(this.url);
            long etime = System.currentTimeMillis();
            handler.onSCache(new SCache(this.url, etime-stime, expired, content));
        }catch(Exception e){
            long etime = System.currentTimeMillis();
            handler.onECache(new ECache(this.url, etime-stime, ECache.ERROR_CACHE, e.getMessage()));
            Logcat.d(TAG, e.getMessage());
        }
    }

}
