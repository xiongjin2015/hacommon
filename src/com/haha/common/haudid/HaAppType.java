package com.haha.common.haudid;

/**
 * app type
 * @author xj
 *
 */
public enum HaAppType {
    
    APHONE((short)1,"aphone"),APAD((short)2,"apad");
    
    private short type;
    private String name;
    
    private HaAppType(short type,String name){
        this.type = type;
        this.name = name;
    }
    
    public short getType(){
        return this.type;
    }
    
    public String getName(){
        return this.name;
    }
    
}
