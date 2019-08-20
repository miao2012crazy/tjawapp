package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/8/17.
 */

public class RealBean {

    /**
     * userReal : {"id":1,"userId":"10","userRealName":"苗春良","userRealImgFront":"1565968527122.jpg","userRealImgBg":"1565968527124.jpg","userRealNum":"220822199110110416","userRealReason":"等待审核中","regDate":"2019-08-16 23:15:27"}
     * state : 2
     */

    private UserRealBean userReal;
    private int state;

    public UserRealBean getUserReal() {
        return userReal;
    }

    public void setUserReal(UserRealBean userReal) {
        this.userReal = userReal;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class UserRealBean {
        /**
         * id : 1
         * userId : 10
         * userRealName : 苗春良
         * userRealImgFront : 1565968527122.jpg
         * userRealImgBg : 1565968527124.jpg
         * userRealNum : 220822199110110416
         * userRealReason : 等待审核中
         * regDate : 2019-08-16 23:15:27
         */

        private int id;
        private String userId;
        private String userRealName;
        private String userRealImgFront;
        private String userRealImgBg;
        private String userRealNum;
        private String userRealReason;
        private String regDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserRealImgFront() {
            return userRealImgFront;
        }

        public void setUserRealImgFront(String userRealImgFront) {
            this.userRealImgFront = userRealImgFront;
        }

        public String getUserRealImgBg() {
            return userRealImgBg;
        }

        public void setUserRealImgBg(String userRealImgBg) {
            this.userRealImgBg = userRealImgBg;
        }

        public String getUserRealNum() {
            return userRealNum;
        }

        public void setUserRealNum(String userRealNum) {
            this.userRealNum = userRealNum;
        }

        public String getUserRealReason() {
            return userRealReason;
        }

        public void setUserRealReason(String userRealReason) {
            this.userRealReason = userRealReason;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }
}
