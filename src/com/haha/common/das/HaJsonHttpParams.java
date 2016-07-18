package com.haha.common.das;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xj
 *         Created by xj on 16/6/23.
 */
public class HaJsonHttpParams {

    private String action;
    private List<HaJsonHttpParams.Param> params = new ArrayList();
    private JSONObject jb = new JSONObject();

    public HaJsonHttpParams() {
    }

    public static HaJsonHttpParams newParams() {
        return new HaJsonHttpParams();
    }

    public HaJsonHttpParams setAction(String action){
        this.action = action;
        return this;
    }

    public HaJsonHttpParams put(String key, String value) {
        if(key != null) {
            if(value == null) {
                this.params.add(new HaJsonHttpParams.Param(key, ""));
            } else {
                this.params.add(new HaJsonHttpParams.Param(key, value));
            }
        }

        return this;
    }

    protected String build() throws JSONException {

        //params
        JSONObject paramss = new JSONObject();
        for(Param param:params){
            paramss.put(param.key,param.value);
        }

        //device
        JSONObject device = new JSONObject();
        //device.put("appVersion", SSApp.getInstance().getVerion());
        //device.put("userToken",SSApp.getInstance().getUserToken());
        //device.put("channelCode",SSApp.getInstance().getSid());
        //device.put("systemVersion",SSApp.getInstance().getsVerison());
        //device.put("deviceType",SSApp.getInstance().getType());


        jb.put("action", action);
        jb.put("params", paramss);
        jb.put("device", device);

        return jb.toString();
    }

    private static final class Param {
        private String key;
        private String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}
