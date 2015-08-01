
package com.haha.common.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.haha.common.config.HaDirMgmt;
import com.haha.common.config.HaDirMgmt.WorkDir;
import com.haha.common.logger.Logcat;
import com.haha.common.utils.HaFile;

public class HaCacheRules {

    private final static String TAG = "HaCacheRules";

    // local cache file name
    private final static String LOCAL_RULE_FILE = "cache.rules";

    // local rule file absolute path
    private String localRuleFilePath;

    // wrapper rule object,default no rule
    private Rules rules;
    
    public void init(Context context){
        try{
            localRuleFilePath = HaDirMgmt.getInstance().getPath(WorkDir.CONFIG)+"/"+LOCAL_RULE_FILE;
            //local rule file object
            File fileRule = new File(localRuleFilePath);
            if(!fileRule.exists() || fileRule.length() == 0){   
                //local rule file not exist, create default rule file
                rules = useDefaultRuleFile(context);
            }else{
                //initialized rules from local rule file
                String content = HaFile.read(fileRule);
                rules = (Rules) JSON.parseObject(content, Rules.class);
            }
            
            //check if need to update the configure file
//            long lastUpdateTM = HaPreference.getInstance().getLong(PrefID.PREF_CACHE_RULES_LAST_UPDATE_TIME);
//            if(System.currentTimeMillis() - lastUpdateTM > HaConfig.getInstance().getLong(ConfigID.CACHE_RULE_UPDATE_TIME_LIMIT)){
//                updateLocalRuleFile(localRuleFilePath);
//                HaPreference.getInstance().putLong(PrefID.PREF_CACHE_RULES_LAST_UPDATE_TIME, System.currentTimeMillis());
//            }
        }catch(Exception e){
            Logcat.d(TAG, e.getMessage());
            if(rules == null){
                rules = new Rules();
            }
        }
    }
    
    private Rules useDefaultRuleFile(Context context){
        
        InputStream is = null;
        try {
            AssetManager am = context.getAssets();
            is = am.open(LOCAL_RULE_FILE);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[16*1024];
            int rdsz = is.read(buffer);
            while(rdsz != -1){
                baos.write(buffer, 0, rdsz);
                rdsz = is.read(buffer);
            }
            is.close();
            
            String content = new String(baos.toByteArray());
            
            return (Rules)JSON.parseObject(content, Rules.class);
        } catch (Exception e) {
            Logcat.d(TAG, e.getMessage());
        } finally{
            try{
                if(is != null)
                    is.close();
            }catch(Exception e){
                Logcat.d(TAG, e.getMessage());
            }
        }
        //use empty rules, means no cache
        return new Rules();
    }
    
    public boolean needCache(String url){
        return rules.match(url);
    }
    
    public Rule getRule(String url){
        return rules.getRule(url);
    }

    public static class Rules {

        private List<Rule> rules;

        public List<Rule> getRules() {
            return rules;
        }

        public void setRules(List<Rule> rules) {
            this.rules = rules;
        }

        public Rule getRule(String url) {
            if (url == null || url.equals(""))
                return null;
            Iterator<Rule> iter = rules.iterator();
            while (iter.hasNext()) {
                Rule rule = iter.next();
                if (rule.match(url)) {
                    return rule;
                }
            }

            return null;
        }

        public boolean match(String url) {
            if (url == null || url.equals(""))
                return false;
            Iterator<Rule> iter = rules.iterator();
            while (iter.hasNext()) {
                Rule rule = iter.next();
                if (rule.match(url))
                    return true;
            }

            return false;
        }
    }

    public static class Rule {
        private String pattern;
        private long expireInMillis;
        private boolean strong;// 是强缓存还是弱缓存

        public boolean match(String url) {
            if (url == null)
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
