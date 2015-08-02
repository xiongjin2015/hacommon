package com.haha.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * main page entity for haha video
 * @author xj
 *
 */
public class MainEntity implements Serializable {

    private static final long serialVersionUID = -3962678903036536689L;
    
    private List<MainBlock> slices; //*
    
    private String nsclick_p;

    public List<MainBlock> getSlices() {
        return slices;
    }

    public void setSlices(List<MainBlock> slices) {
        this.slices = slices;
    }

    public String getNsclick_p() {
        return nsclick_p;
    }

    public void setNsclick_p(String nsclick_p) {
        this.nsclick_p = nsclick_p;
    }
    
    

}
