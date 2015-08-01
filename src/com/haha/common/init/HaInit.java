package com.haha.common.init;

import android.content.Context;

import com.haha.common.cache.HaCache;
import com.haha.common.config.HaConfig;
import com.haha.common.config.HaDirMgmt;
import com.haha.common.config.HaPreference;
import com.haha.common.haudid.HaAppType;
import com.haha.common.task.HaExecutor;

public final class HaInit {
    
    public static void init(Context context,HaAppType appType,String versionName){
        
        //initialize configure singleton instance
        HaConfig.getInstance().init(context);
        
        //initialize the funshion udid
        //TODO
        
        //init the executor service
        HaExecutor.getInstance().init();
        
        //initialize the preference singleton instance
        HaPreference.getInstance().init(context);
        
        //initialize directory management singleton instance, depends on:HaPreference module
        HaDirMgmt.getInstance().init(context, appType);
        
        //initialize the cache module,
        HaCache.getInstance().init(context);
    }

}
