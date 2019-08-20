package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/7/3.
 */

public class CommonBean {

    private String result;
    private String data;
    private String msg;


    public CommonBean(String result, String data, String msg) {
        this.result = result;
        this.data = data;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
