package com.zysoft.tjawshapingapp.bean;

import java.util.List;

/**
 * Created by mr.miao on 2019/8/14.
 */

public class UserWalletBean {


    /**
     * wallet : {"id":42,"walletBalance":0,"integralBalance":0,"regDate":"2019-11-04 18:00:00","userId":1}
     * history : [{"id":1,"type":0,"tradingPrice":0,"tradingDesc":"预约项目'修形美林-眼部整形美容手术'定金：¥1.0E-4","regDate":"2019-11-14 17:19:20","userId":1,"userTel":"15585513651","isAdd":1}]
     */

    private WalletBean wallet;
    private List<HistoryBean> history;

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class WalletBean {
        /**
         * id : 42
         * walletBalance : 0
         * integralBalance : 0
         * regDate : 2019-11-04 18:00:00
         * userId : 1
         */

        private int id;
        private double walletBalance;
        private double integralBalance;
        private String regDate;
        private int userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(double walletBalance) {
            this.walletBalance = walletBalance;
        }

        public double getIntegralBalance() {
            return integralBalance;
        }

        public void setIntegralBalance(double integralBalance) {
            this.integralBalance = integralBalance;
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

    public static class HistoryBean {
        /**
         * id : 1
         * type : 0
         * tradingPrice : 0
         * tradingDesc : 预约项目'修形美林-眼部整形美容手术'定金：¥1.0E-4
         * regDate : 2019-11-14 17:19:20
         * userId : 1
         * userTel : 15585513651
         * isAdd : 1
         */

        private int id;
        private int type;
        private double tradingPrice;
        private String tradingDesc;
        private String regDate;
        private int userId;
        private String userTel;
        private int isAdd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getTradingPrice() {
            return tradingPrice;
        }

        public void setTradingPrice(double tradingPrice) {
            this.tradingPrice = tradingPrice;
        }

        public String getTradingDesc() {
            return tradingDesc;
        }

        public void setTradingDesc(String tradingDesc) {
            this.tradingDesc = tradingDesc;
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

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public int getIsAdd() {
            return isAdd;
        }

        public void setIsAdd(int isAdd) {
            this.isAdd = isAdd;
        }
    }
}
