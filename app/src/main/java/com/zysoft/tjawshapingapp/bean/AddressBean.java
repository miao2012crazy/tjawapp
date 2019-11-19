package com.zysoft.tjawshapingapp.bean;

import java.io.Serializable;

/**
 * Created by mr.miao on 2019/5/27.
 */

public class AddressBean implements  Serializable {

    private int id;
    private String recvTel;
    private String recvName;
    private String addressClassA;
    private String addressDetail;
    private int isDefault;
    private int isUsable;
    private String regDate;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecvTel() {
        return recvTel;
    }

    public void setRecvTel(String recvTel) {
        this.recvTel = recvTel;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getAddressClassA() {
        return addressClassA;
    }

    public void setAddressClassA(String addressClassA) {
        this.addressClassA = addressClassA;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(int isUsable) {
        this.isUsable = isUsable;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
