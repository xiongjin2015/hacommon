
package com.haha.common.cache;

import com.haha.common.logger.Logcat;

/**
 * task of writing json to cahce  
 * @author xj
 *
 */
public class HaCacheWriteTask implements Runnable {

    private final static String TAG = "HaCacheWriteTask";

    private String url;
    private String content;

    public HaCacheWriteTask(String url, String content) {
        this.url = url;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            long stime = System.currentTimeMillis();
            HaCacheFiles.getInstance().write(this.url, this.content);
            long etime = System.currentTimeMillis();
            Logcat.d(
                    TAG,
                    "write cache for url: " + this.url + ", time used: "
                            + String.valueOf(etime - stime));
        } catch (Exception e) {
            Logcat.d(TAG, "write cache for url error:"+e.getMessage());
        }
    }

}
