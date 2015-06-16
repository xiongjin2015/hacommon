
package com.haha.common.das;

import com.haha.common.entity.TestEntity;

/**
 * all requests set
 * @author xj
 *
 */
public enum DasReq {
    
    TEST("",1,TestEntity.class)
    ;

    private String url;
    private int maxRetryCount;
    private Class<?> entityClass;

    private DasReq(String url, int maxRetryCount, Class<?> entityClass) {
        this.url = url;
        this.maxRetryCount = maxRetryCount;
        this.entityClass = entityClass;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
    
    

}
