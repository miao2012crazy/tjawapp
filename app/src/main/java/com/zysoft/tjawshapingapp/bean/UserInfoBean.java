package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/5/17.
 */

public class UserInfoBean {
    private int userId;
    private String userNickName;
    private String userHeadImg;
    private String userTel;
    private String openId;
    private String wxtoken;
    private int realState;
    private int userLevel;
    private String userSign;
    private int userSex;

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public UserInfoBean(int userId, String userNickName, String userHeadImg, String userTel, String openId, String wxtoken, int realState, int userLevel, String userSign, int userSex) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userHeadImg = userHeadImg;
        this.userTel = userTel;
        this.openId = openId;
        this.wxtoken = wxtoken;
        this.realState = realState;
        this.userLevel = userLevel;
        this.userSign = userSign;
        this.userSex = userSex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxtoken() {
        return wxtoken;
    }

    public void setWxtoken(String wxtoken) {
        this.wxtoken = wxtoken;
    }

    public int getRealState() {
        return realState;
    }

    public void setRealState(int realState) {
        this.realState = realState;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }
}
