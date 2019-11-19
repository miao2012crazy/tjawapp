package com.zysoft.tjawshapingapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/15.
 */

public class OrderResultBean {


    /**
     * appid : wx06e8a9d5844ed113
     * isPay : true
     * noncestr : njohDQP4andEIHiYgVSeOlwnaR3SD2XT
     * package : Sign=WXPay
     * partnerid : 1520250021
     * prepayid : wx17134954616085c9980179251430024000
     * sign : 5FAEB62668958BDA647023702C46B904
     * timestamp : 1571291394
     */

    private String appid;
    private boolean isPay;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private int timestamp;
    private String orderId;


    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public boolean isIsPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
