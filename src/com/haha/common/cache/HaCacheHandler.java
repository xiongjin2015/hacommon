package com.haha.common.cache;

public interface HaCacheHandler {
    
    public static class SCache{
        private String url;
        private long timeUsed;
        private boolean expired;
        private String content;
        
        public SCache(String url, long timeUsed, boolean expired, String content){
            this.url = url;
            this.timeUsed = timeUsed;
            this.expired = expired;
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public long getTimeUsed() {
            return timeUsed;
        }

        public String getContent() {
            return content;
        }

        public boolean isExpired() {
            return expired;
        }
    }
    
    public static class ECache{
        public final static int ERROR_CACHE = 401;
        
        private String url;
        private long timeUsed;
        private int errCode;
        private String errMsg;
        
        public ECache(String url, long timeUsed, int errCode, String errMsg){
            this.url = url;
            this.timeUsed = timeUsed;
            this.errCode = errCode;
            this.errMsg = errMsg;
        }

        public int getErrCode() {
            return errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public String getUrl() {
            return url;
        }

        public long getTimeUsed() {
            return timeUsed;
        }
    }
    
    /**
     * recall method for success cache accessing
     * @param scache
     */
    public void onSCache(SCache scache);
    
    /**
     * recall method for error cache accessing
     * @param ecache
     */
    public void onECache(ECache ecache);

}
