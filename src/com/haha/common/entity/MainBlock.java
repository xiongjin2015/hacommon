
package com.haha.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * main page recommend block entity
 * 
 * @author xj
 */
public class MainBlock implements Serializable {

    private static final long serialVersionUID = -2229217294590371088L;

    private String name;// "轮播图"、"今日焦点"、"电影大放送"  //*
    private String type;// "index_flash"  //*
    private String tag;// "index_flash"   //*
    private String nav_icon;//
    private String url_cond;
    private String up_bg_img;
    private String down_bg_img;
    private String nsclick_v;
    private List<MainMedia> hot; //*

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNav_icon() {
        return nav_icon;
    }

    public void setNav_icon(String nav_icon) {
        this.nav_icon = nav_icon;
    }

    public String getUrl_cond() {
        return url_cond;
    }

    public void setUrl_cond(String url_cond) {
        this.url_cond = url_cond;
    }

    public String getUp_bg_img() {
        return up_bg_img;
    }

    public void setUp_bg_img(String up_bg_img) {
        this.up_bg_img = up_bg_img;
    }

    public String getDown_bg_img() {
        return down_bg_img;
    }

    public void setDown_bg_img(String down_bg_img) {
        this.down_bg_img = down_bg_img;
    }

    public String getNsclick_v() {
        return nsclick_v;
    }

    public void setNsclick_v(String nsclick_v) {
        this.nsclick_v = nsclick_v;
    }

    public List<MainMedia> getHot() {
        return hot;
    }

    public void setHot(List<MainMedia> hot) {
        this.hot = hot;
    }

}
