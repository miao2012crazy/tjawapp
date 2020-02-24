package com.zysoft.tjawshapingapp.bean;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-29.
 */
public class UserTeamBasicBean {


    /**
     * userId : 1
     * userNickName : 💮💮夜良人🔥🔥
     * userHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132
     * userLevel : 1
     * userAllIncome : 70
     * customerIncome : 20
     * teamIncome : 50
     */

    private int userId;
    private String userNickName;
    private String userHeadImg;
    private int userLevel;
    private double userAllIncome;
    private double customerIncome;
    private double teamIncome;

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

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public double getUserAllIncome() {
        return userAllIncome;
    }

    public void setUserAllIncome(double userAllIncome) {
        this.userAllIncome = userAllIncome;
    }

    public double getCustomerIncome() {
        return customerIncome;
    }

    public void setCustomerIncome(double customerIncome) {
        this.customerIncome = customerIncome;
    }

    public double getTeamIncome() {
        return teamIncome;
    }

    public void setTeamIncome(double teamIncome) {
        this.teamIncome = teamIncome;
    }
}
