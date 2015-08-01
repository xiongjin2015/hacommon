package com.haha.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;

public final class HaConfig {
    
    private final static String TAG = "HaConfig";
    
    //config ini file name
    private final String CONFIG_FILE_NAME = "config.ini";
    
    //properties object of the configure
    private Properties properties = new Properties();
    
  //singleton instance of configure object
    private static HaConfig instance;
    
    public static HaConfig getInstance(){
        if(instance == null)
            instance = new HaConfig();
        return instance;
    }
    
    /**
     * initialize the {@properties} object, must be invoked first before access anything
     * of this class
     * @param path
     */
    public void init(Context context){
        /*initialize the inner properties with inner configure file*/
        this.initWithInner(context);
        
        /*initialize the outer properties with outer configure file*/
        //this.initWithOuter();
    }
    
    private void initWithInner(Context context){
        InputStream is = null;
        try{
            AssetManager am = context.getAssets();
            is = am.open(CONFIG_FILE_NAME);
            properties.load(is);
        }catch(Exception e){
            if(is != null){
                try {
                    is.close();
                } catch (IOException e1) {}
            }
        }
    }
    
    public long getLong(ConfigID id){
        return this.getLong(id, Long.valueOf(id.getDefaultValue()).longValue());
    }
    
    public long getLong(ConfigID id, long defaultValue){
        String val = properties.getProperty(id.getKey(), null);
        if(val != null){
            try{
                return Long.valueOf(val).longValue();
            }catch(Exception e){
                return defaultValue;
            }
        }
        
        return defaultValue;
    }
    
    public enum ConfigID{
        CACHE_RULE_UPDATE_TIME_LIMIT("cache_rule_update_time_limit", "259200000");//in million seconds
        
        private String key;
        private String defaultValue;
        
        ConfigID(String key,String defaultValue){
            this.key = key;
            this.defaultValue = defaultValue;
        }
        
        public String getKey(){
            return key;
        }
        
        public String getDefaultValue(){
            return defaultValue;
        }
    }

}
