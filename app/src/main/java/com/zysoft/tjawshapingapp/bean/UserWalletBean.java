package com.zysoft.tjawshapingapp.bean;

import java.util.List;

/**
 * Created by mr.miao on 2019/8/14.
 */

public class UserWalletBean {


    /**
     * wallet : {"id":28,"walletBalance":0,"integralBalance":0,"regDate":"2019-05-26 14:35:28","userTel":"15585513651"}
     * history : [{"id":1,"type":0,"tradingPrice":500,"tradingDesc":"不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给的订单预付款","regDate":"2019-08-14 23:11:39","userId":10,"userTel":"15585513651"}]
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
         * id : 28
         * walletBalance : 0
         * integralBalance : 0
         * regDate : 2019-05-26 14:35:28
         * userTel : 15585513651
         */

        private int id;
        private int walletBalance;
        private int integralBalance;
        private String regDate;
        private String userTel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(int walletBalance) {
            this.walletBalance = walletBalance;
        }

        public int getIntegralBalance() {
            return integralBalance;
        }

        public void setIntegralBalance(int integralBalance) {
            this.integralBalance = integralBalance;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }
    }

    public static class HistoryBean {
        /**
         * id : 1
         * type : 0
         * tradingPrice : 500
         * tradingDesc : 不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给钱不给的订单预付款
         * regDate : 2019-08-14 23:11:39
         * userId : 10
         * userTel : 15585513651
         */

        private int id;
        private int type;
        private int tradingPrice;
        private String tradingDesc;
        private String regDate;
        private int userId;
        private String userTel;

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

        public int getTradingPrice() {
            return tradingPrice;
        }

        public void setTradingPrice(int tradingPrice) {
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
    }
}
