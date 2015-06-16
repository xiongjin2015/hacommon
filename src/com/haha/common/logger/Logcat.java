
package com.haha.common.logger;

import android.util.Log;

public class Logcat {

    private final static String TAG = "haha";

    private static boolean DEBUG = true;
    
    
    public static void i(String subTag,String msg){
        if(DEBUG)
            Log.i(TAG, wrapMsg(subTag,msg));
    }
    
    public static void d(String subTag,String msg){
        if(DEBUG)
            Log.d(TAG, wrapMsg(subTag,msg));
    }
    
    public static void w(String subTag,String msg){
        if(DEBUG)
            Log.w(TAG, wrapMsg(subTag,msg));
    }
    
    public static void e(String subTag,String msg){
        if(DEBUG)
            Log.e(TAG, wrapMsg(subTag,msg));
    }
    
    public static void e(String subTag,String msg,Throwable tr){
        if(DEBUG)
            Log.e(TAG, wrapMsg(subTag,msg),tr);
    }
    
    private static String wrapMsg(String subTag,String msg){
        StringBuilder sb = new StringBuilder()
        .append("{").append(Thread.currentThread().getName()).append("}")
        .append("[").append(subTag).append("] ")
        .append(msg);

        return sb.toString();
    }
}
