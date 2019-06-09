package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/5/12.
 */

public class WXBean {


    /**
     * code : 001rXARM0lw86c2WIjQM0JwjRM0rXARl
     * country : CN
     * lang : zh_CN
     * state : wechat_sdk_微信登录
     * url : wxe47df26aa9b192a6://oauth?code=001rXARM0lw86c2WIjQM0JwjRM0rXARl&state=wechat_sdk_%E5%BE%AE%E4%BF%A1%E7%99%BB%E5%BD%95
     * errCode : 0
     */

    private String code;
    private String country;
    private String lang;
    private String state;
    private String url;
    private int errCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
