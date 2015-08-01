package com.haha.common.cache;

import android.content.Context;

import com.haha.common.cache.HaCacheRules.Rule;
import com.haha.common.logger.Logcat;
import com.haha.common.task.HaExecutor;

public class HaCacheImpl extends HaCache {
    
    private static final String TAG = "HaCacheImpl";
    
    /*cache rules*/
    HaCacheRules rules = new HaCacheRules();

    @Override
    public void init(Context context) {
        HaCacheFiles.getInstance().init();
        rules.init(context);
    }

    /**
     * put content to cache file with url by key
     * @param key
     * @param content
     */
    @Override
    public void put(String url, String content) {
        if(this.rules.needCache(url)){
            HaExecutor.getInstance().submit(new HaCacheWriteTask(url, content));
            Logcat.d(TAG, "put cache for url: "+url);
        }
    }
    
    @Override
    public boolean get(String url, HaCacheHandler handler) {
        Rule rule = this.rules.getRule(url);
        if(rule != null){
            if(HaCacheFiles.getInstance().isHit(url)){
                boolean expired = HaCacheFiles.getInstance().isExpired(url, rule.getExpireInMillis());
                if(rule.isStrong()){
                    HaExecutor.getInstance().submit(new HaCacheReadTask(url, handler, expired));
                    if(!expired){
                        return true;
                    }
                }else{
                    if(!expired){
                        HaExecutor.getInstance().submit(new HaCacheReadTask(url, handler, expired));
                        return true;
                    }
                }
                
                Logcat.d(TAG, "get cache for url: " + url);
            }
        }
        
        return false;
    }



}
