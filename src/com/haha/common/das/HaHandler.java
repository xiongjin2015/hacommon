
package com.haha.common.das;

import com.haha.common.logger.Logcat;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

public abstract class HaHandler implements Callback {
    
    private final static String TAG = "HaHandler";

    /* msg id defined for send message */
    private final static int SUCCESS_MSG = 1;
    private final static int FAILED_MSG = 2;

    private Handler handler;

    /* for store some object need */
    protected Object obj = null;

    public HaHandler() {
        if (Looper.myLooper() != null)
            handler = new Handler(this);
    }

    public HaHandler(Object obj) {
        this();
        this.obj = obj;
    }

    public HaHandler(Handler handler, Object obj) {
        this.handler = handler;
        this.obj = obj;
    }

    /**
     * overwrite this method for handle success response
     * 
     * @param sresp:success response entity
     */
    public abstract void onSuccess(SResp sresp);

    /**
     * overwrite this method for handle failed response,which response code is not 200 OK or fail to
     * access the server
     * 
     * @param eresp:error response entity
     */
    public abstract void onFailed(EResp eresp);

    public void processSuccess(SResp sresp) {
        if (handler != null) {
            /* send message to caller through handler */
            Message msg = handler.obtainMessage(SUCCESS_MSG, sresp);
            handler.sendMessage(msg);
        } else {
            onSuccess(sresp);
        }
    }

    public void processFailed(EResp eresp) {
        if (handler != null) {
            /* send failed message to caller when exception */
            Message msg = handler.obtainMessage(FAILED_MSG, eresp);
            handler.sendMessage(msg);
        } else {
            onFailed(eresp);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        try{
            switch (msg.what) {
                case SUCCESS_MSG:
                    onSuccess((SResp)msg.obj);
                    break;
                case FAILED_MSG:
                    onFailed((EResp)msg.obj);
                    break;
                default:
                    break;
            }  
        }catch(Exception e){
            Logcat.e(TAG, e.getMessage());
        }
        return true;
    }

    /**
     * success response entity
     */
    public static class SResp {
        public enum Type {
            CACHE, SERVER
        };

        private Type type;
        private boolean expired; // is expired or not;be used only when type is cache
        private String url;
        private long timeUsed = -1;
        private Object entity;

        public SResp(Type type, boolean expired, String url, long timeUsed, Object entity) {
            this.type = type;
            this.expired = expired;
            this.url = url;
            this.timeUsed = timeUsed;
            this.entity = entity;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getTimeUsed() {
            return timeUsed;
        }

        public void setTimeUsed(long timeUsed) {
            this.timeUsed = timeUsed;
        }

        public Object getEntity() {
            return entity;
        }

        public void setEntity(Object entity) {
            this.entity = entity;
        }
    }

    /**
     * error response entity
     */
    public static class EResp {
        private String url;
        private int errCode;
        private int httpCode;
        private String errMsg;

        public EResp(String url, int errCode, int httpCode, String errMsg) {
            this.url = url;
            this.errCode = errCode;
            this.httpCode = httpCode;
            this.errMsg = errMsg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }
}
