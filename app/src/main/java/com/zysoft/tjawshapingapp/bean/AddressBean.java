package com.zysoft.tjawshapingapp.bean;

import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.tjawshapingapp.R;

import java.io.Serializable;

/**
 * Created by mr.miao on 2019/5/27.
 */

public class AddressBean implements BindingAdapterItem, Serializable {


    private int id;
    private String recvTel;
    private String recvName;
    private String addressA;
    private String addressB;
    private String detailAddr;
    private int isDefault;
    private String userId;
    private int isUsable;
    private String regDate;

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

    public String getAddressA() {
        return addressA;
    }

    public void setAddressA(String addressA) {
        this.addressA = addressA;
    }

    public String getAddressB() {
        return addressB;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public int getViewType() {
        return R.layout.item_address;
    }
}
