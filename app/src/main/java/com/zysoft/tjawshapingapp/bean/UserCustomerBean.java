package com.zysoft.tjawshapingapp.bean;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-29.
 */
public class UserCustomerBean {

    /**
     * userCustomerIncomBeans : [{"userId":3,"userNickName":"莹莹","userHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIqB293AAwVib083BebhwaGBYcRXFLAffibMc8dEXOG2ehrrDXFbaUH45HGOAd7zickVtcTgz44BTk7A/132","historyDesc":"莹莹","userTel":"15584896066","conPrice":0,"returnPrice":0,"regDate":"2019-11-29 11:51:57"}]
     * count : 1
     * income : 20
     */

    private int count;
    private int income;
    private List<UserCustomerIncomBeansBean> userCustomerIncomBeans;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<UserCustomerIncomBeansBean> getUserCustomerIncomBeans() {
        return userCustomerIncomBeans;
    }

    public void setUserCustomerIncomBeans(List<UserCustomerIncomBeansBean> userCustomerIncomBeans) {
        this.userCustomerIncomBeans = userCustomerIncomBeans;
    }

    public static class UserCustomerIncomBeansBean {
        /**
         * userId : 3
         * userNickName : 莹莹
         * userHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIqB293AAwVib083BebhwaGBYcRXFLAffibMc8dEXOG2ehrrDXFbaUH45HGOAd7zickVtcTgz44BTk7A/132
         * historyDesc : 莹莹
         * userTel : 15584896066
         * conPrice : 0
         * returnPrice : 0
         * regDate : 2019-11-29 11:51:57
         */

        private int userId;
        private String userNickName;
        private String userHeadImg;
        private String historyDesc;
        private String userTel;
        private int conPrice;
        private int returnPrice;
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

        public String getHistoryDesc() {
            return historyDesc;
        }

        public void setHistoryDesc(String historyDesc) {
            this.historyDesc = historyDesc;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public int getConPrice() {
            return conPrice;
        }

        public void setConPrice(int conPrice) {
            this.conPrice = conPrice;
        }

        public int getReturnPrice() {
            return returnPrice;
        }

        public void setReturnPrice(int returnPrice) {
            this.returnPrice = returnPrice;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }
}
