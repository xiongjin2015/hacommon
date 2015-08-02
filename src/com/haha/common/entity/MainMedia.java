
package com.haha.common.entity;

import java.io.Serializable;

/**
 * single media for main page
 * 
 * @author xj
 */
public class MainMedia implements Serializable {

    private static final long serialVersionUID = 5791887872286290247L;

    private int work_id; // *
    private int terminal_type; // *
    private String title; // *
    private String url; // *
    private String imgh_url; // *
    private String imgv_url;
    private String duration; // *
    private String update; // "更新至xxx集" //*
    private String rate;// 评分：0、1、2、3、4、5 //*
    private String tag; // *
    private String works_type; // tvplay //*
    private String lable; // *
    private String brief;// 简短描述 //*
    private String channel;
    private int coprctl_small_window;
    private int is_adver; // 0 or 1;
    private String corner_mark;
    private String third_imgh_url;
    private String third_imgv_url;
    private String mark_url;
    private int is_long_video;// 0 or 1;
    private String filter_version;
    private String advertiser;
    private String advertiser_dot;
    private int is_promotion;
    private String advertiser_dot_show;
    private String nsclick_v;
    private String nsclick_p;

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public int getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(int terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgh_url() {
        return imgh_url;
    }

    public void setImgh_url(String imgh_url) {
        this.imgh_url = imgh_url;
    }

    public String getImgv_url() {
        return imgv_url;
    }

    public void setImgv_url(String imgv_url) {
        this.imgv_url = imgv_url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWorks_type() {
        return works_type;
    }

    public void setWorks_type(String works_type) {
        this.works_type = works_type;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getCoprctl_small_window() {
        return coprctl_small_window;
    }

    public void setCoprctl_small_window(int coprctl_small_window) {
        this.coprctl_small_window = coprctl_small_window;
    }

    public int getIs_adver() {
        return is_adver;
    }

    public void setIs_adver(int is_adver) {
        this.is_adver = is_adver;
    }

    public String getCorner_mark() {
        return corner_mark;
    }

    public void setCorner_mark(String corner_mark) {
        this.corner_mark = corner_mark;
    }

    public String getThird_imgh_url() {
        return third_imgh_url;
    }

    public void setThird_imgh_url(String third_imgh_url) {
        this.third_imgh_url = third_imgh_url;
    }

    public String getThird_imgv_url() {
        return third_imgv_url;
    }

    public void setThird_imgv_url(String third_imgv_url) {
        this.third_imgv_url = third_imgv_url;
    }

    public String getMark_url() {
        return mark_url;
    }

    public void setMark_url(String mark_url) {
        this.mark_url = mark_url;
    }

    public int getIs_long_video() {
        return is_long_video;
    }

    public void setIs_long_video(int is_long_video) {
        this.is_long_video = is_long_video;
    }

    public String getFilter_version() {
        return filter_version;
    }

    public void setFilter_version(String filter_version) {
        this.filter_version = filter_version;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getAdvertiser_dot() {
        return advertiser_dot;
    }

    public void setAdvertiser_dot(String advertiser_dot) {
        this.advertiser_dot = advertiser_dot;
    }

    public int getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(int is_promotion) {
        this.is_promotion = is_promotion;
    }

    public String getAdvertiser_dot_show() {
        return advertiser_dot_show;
    }

    public void setAdvertiser_dot_show(String advertiser_dot_show) {
        this.advertiser_dot_show = advertiser_dot_show;
    }

    public String getNsclick_v() {
        return nsclick_v;
    }

    public void setNsclick_v(String nsclick_v) {
        this.nsclick_v = nsclick_v;
    }

    public String getNsclick_p() {
        return nsclick_p;
    }

    public void setNsclick_p(String nsclick_p) {
        this.nsclick_p = nsclick_p;
    }

}
