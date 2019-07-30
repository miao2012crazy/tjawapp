package com.zysoft.tjawshapingapp.constants;


/**
 * Created by mr.miao on 2018/7/30.
 */

public class NetResponse {

    private String tag;
    private Object data;

    public NetResponse(String tag, Object data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
