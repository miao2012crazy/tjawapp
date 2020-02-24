package com.zysoft.tjawshapingapp.bean;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-06.
 */
public class UserTeamBean {


    /**
     * teamListBeans : [{"userId":3,"userNickName":"莹莹","userHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIqB293AAwVib083BebhwaGBYcRXFLAffibMc8dEXOG2ehrrDXFbaUH45HGOAd7zickVtcTgz44BTk7A/132","userLevel":1,"userConPrice":0,"teamIncome":1500,"teamCount":0,"regDate":"2019-11-29 11:51:57"}]
     * count : 1
     * price : 1550
     */

    private int count;
    private double price;
    private List<TeamListBeansBean> teamListBeans;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<TeamListBeansBean> getTeamListBeans() {
        return teamListBeans;
    }

    public void setTeamListBeans(List<TeamListBeansBean> teamListBeans) {
        this.teamListBeans = teamListBeans;
    }

    public static class TeamListBeansBean {
        /**
         * userId : 3
         * userNickName : 莹莹
         * userHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIqB293AAwVib083BebhwaGBYcRXFLAffibMc8dEXOG2ehrrDXFbaUH45HGOAd7zickVtcTgz44BTk7A/132
         * userLevel : 1
         * userConPrice : 0
         * teamIncome : 1500
         * teamCount : 0
         * regDate : 2019-11-29 11:51:57
         */

        private int userId;
        private String userNickName;
        private String userHeadImg;
        private int userLevel;
        private double userConPrice;
        private double teamIncome;
        private int teamCount;
        private String regDate;

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

        public double getUserConPrice() {
            return userConPrice;
        }

        public void setUserConPrice(double userConPrice) {
            this.userConPrice = userConPrice;
        }

        public double getTeamIncome() {
            return teamIncome;
        }

        public void setTeamIncome(double teamIncome) {
            this.teamIncome = teamIncome;
        }

        public int getTeamCount() {
            return teamCount;
        }

        public void setTeamCount(int teamCount) {
            this.teamCount = teamCount;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }
}
