package com.haha.common.config;

import android.content.Context;

/**
 * some public param for app
 * @author xj
 *
 */
public class HaApp {
    
    private final static String DEFAULT_MAC = "000000000000";
    
    private Context context;
    private String type = "unknown";//app type:aphone、apad;
    private String version = "0.0.0.0";//app version
    private String mac = DEFAULT_MAC;//mac address
    private String hudid = "";//device id or user id;
    private NetType net = NetType.UNKNOWN;
    private String sid = "0";//publish market id;
    
    private static HaApp instance;
    
    public static HaApp getInstance(){
        if(instance == null)
            instance = new HaApp();
        return instance;
    }
    
    public void init(Context context,String type,String version,String hudid,String sid){
        this.context = context;
        this.type = type;
        this.version = version;
        this.mac = getInitMac();
        this.hudid = hudid;
        this.net = getNetType();
        this.sid = sid;
    }
    
    private String getInitMac(){
        return DEFAULT_MAC;
    }
    
    private NetType getNetType(){
        return NetType.WIFI;
    }
    

    public enum NetType{
        UNKNOWN(-1,"unknown"),
        WIFI(1,"wifi"),
        MOBILE(2,"mobile");
        
        private int code;
        private String name;
        
        NetType(int code,String name){
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
