
package com.haha.common.das;

import com.haha.common.entity.TestEntity;

/**
 * all requests set
 * @author xj
 *
 */
public enum HaDasReq {
    
    TEST("",1,TestEntity.class)
    ;

    private String path;
    private int maxRetryCount;
    private Class<?> entityClass;

    private HaDasReq(String path, int maxRetryCount, Class<?> entityClass) {
        this.path = path;
        this.maxRetryCount = maxRetryCount;
        this.entityClass = entityClass;
    }

    public String getPath() {
        return path;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
    
    

}
