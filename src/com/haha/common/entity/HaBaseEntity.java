package com.haha.common.entity;

import java.io.Serializable;

public class HaBaseEntity implements Serializable {

    private static final long serialVersionUID = 8134835100455467000L;
    
    /**
     * return code by server
     */
    private String retcode;
    
    /**
     * return message by server,maybe that is null
     */
    private String retmsg;
    
    /**
     * check if the return message is 200 ok, if not there must be return 
     * error, use the retmsg to get the error information
     * @return
     */
    public boolean isOK(){
        if(retcode == null)
            return true;
        
        if(retcode.equals("200")){
            return true;
        }
        
        return false;
    }
    
    public String getRetcode() {
        return retcode;
    }
    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }
    public String getRetmsg() {
        return retmsg;
    }
    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

}
