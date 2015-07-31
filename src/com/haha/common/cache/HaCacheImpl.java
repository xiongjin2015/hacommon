package com.haha.common.cache;

import android.content.Context;

public class HaCacheImpl extends HaCache {
    
    private static final String TAG = "HaCacheImpl";
    
    /*cache rules*/
    HaCacheRules rules = new HaCacheRules();

    @Override
    public void init(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean get(String url, HaCacheHandler handler) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
    public static class Rule{
        private String pattern;
        private long expireInMillis;
        private boolean strong;//是强缓存还是弱缓存
        
        public boolean match(String url){
            if(url == null)
                return false;
            
            return url.matches(this.pattern);
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public long getExpireInMillis() {
            return expireInMillis;
        }

        public void setExpireInMillis(long expireInMillis) {
            this.expireInMillis = expireInMillis;
        }

        public boolean isStrong() {
            return strong;
        }

        public void setStrong(boolean strong) {
            this.strong = strong;
        }
        
    }

}
