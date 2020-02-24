package com.zysoft.tjawshapingapp.bean;

import com.zysoft.tjawshapingapp.R;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-11.
 */
public class OrderDetailBean {


    /**
     * orderInfo : {"id":7,"orderId":"201911111200282530001","projectName":"修形美林-眼部整形美容手术","projectDesc":"东方人有不少人都为单眼皮，或者是很窄的双眼皮（即内双）。这些人当中又有很多人希望通过人工方法形成中等或略宽的双眼皮，来增加眼睛的神韵。内眦赘皮是东方人种的特点，即内眼角的皮褶较紧，可能遮挡部分内眦结构","projectVideo":"","projectIcon":"http://192.168.43.234:8098/static/project/1572694169457.png","orderPrice":1298,"orderPayWay":0,"orderPayPrice":500,"projectEarnestMoney":500,"orderState":9,"expectTime":"2019-11-11 12:04","makeTime":"2019-11-11 12:00","orderCoupons":0,"orderCouponsId":"","projectNum":1,"userId":1,"userNickName":"💮💮夜良人🔥🔥","exceptId":2,"isProduct":1,"recvId":"","recvName":"","recvTel":"","recvAddress":"","shipmentName":"","shipmentId":"","integralPrice":0,"payTime":"","cancelTime":"","returnPriceTime":"","regDate":"2019-11-11 12:00:28","shipmentPrice":0}
     * exceptor : {"id":2,"stateUsable":0,"userNickName":"💮💮医师🔥🔥","userHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132","userPsd":"","userTel":"18222703922","userClientId":"140fe1da9ece6cb5218","userOpenid":"","userAccessToken":"","userRefreshToken":"","userRealState":0,"userLevel":0,"regDate":"2019-11-02 15:13:12","userSign":"这个人很懒，什么都没有留下～","isExpert":1}
     */

    private OrderInfoBean orderInfo;
    private ExceptorBean exceptor;

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public ExceptorBean getExceptor() {
        return exceptor;
    }

    public void setExceptor(ExceptorBean exceptor) {
        this.exceptor = exceptor;
    }

    public static class OrderInfoBean {
        /**
         * id : 7
         * orderId : 201911111200282530001
         * projectName : 修形美林-眼部整形美容手术
         * projectDesc : 东方人有不少人都为单眼皮，或者是很窄的双眼皮（即内双）。这些人当中又有很多人希望通过人工方法形成中等或略宽的双眼皮，来增加眼睛的神韵。内眦赘皮是东方人种的特点，即内眼角的皮褶较紧，可能遮挡部分内眦结构
         * projectVideo :
         * projectIcon : http://192.168.43.234:8098/static/project/1572694169457.png
         * orderPrice : 1298
         * orderPayWay : 0
         * orderPayPrice : 500
         * projectEarnestMoney : 500
         * orderState : 9
         * expectTime : 2019-11-11 12:04
         * makeTime : 2019-11-11 12:00
         * orderCoupons : 0
         * orderCouponsId :
         * projectNum : 1
         * userId : 1
         * userNickName : 💮💮夜良人🔥🔥
         * exceptId : 2
         * isProduct : 1
         * recvId :
         * recvName :
         * recvTel :
         * recvAddress :
         * shipmentName :
         * shipmentId :
         * integralPrice : 0
         * payTime :
         * cancelTime :
         * returnPriceTime :
         * regDate : 2019-11-11 12:00:28
         * shipmentPrice : 0
         */

        private int id;
        private String orderId;
        private String projectName;
        private String projectDesc;
        private String projectVideo;
        private String projectIcon;
        private double orderPrice;
        private int orderPayWay;
        private double orderPayPrice;
        private double projectEarnestMoney;
        private int orderState;
        private String expectTime;
        private String makeTime;
        private int orderCoupons;
        private String orderCouponsId;
        private int projectNum;
        private int userId;
        private String userNickName;
        private int exceptId;
        private int isProduct;
        private String recvId;
        private String recvName;
        private String recvTel;
        private String recvAddress;
        private String shipmentName;
        private String shipmentId;
        private int integralPrice;
        private String payTime;
        private String cancelTime;
        private String returnPriceTime;
        private String regDate;
        private int shipmentPrice;
        private String orderStateName;
        private int isPl;
        private int projectId;

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public int getIsPl() {
            return isPl;
        }

        public void setIsPl(int isPl) {
            this.isPl = isPl;
        }

        /**
         * 0待付款 1正在分配 2已完成 3已评价，4已取消 5待发货 6待配送 7待确认收货
         *0待付款 1正在分配 2已完成 待评价，4已取消   5 待发货 6 待收货 7 待退款 8 已退款
         * @return
         */
        public String getOrderStateName() {
            switch (orderState) {
                case 0:
                    return "待付款";
                case 1:
                    return "正在分配";
                case 3:
                    return "待评价";
                case 4:
                    return "已取消";
                case 5:
                    return "待发货";
                case 6:
                    return "待收货";
                case 7:
                    return "待确认收货";
                case 8:
                    if (isPl==0){
                        return "待评价";
                    }
                    return "已完成";
                case 9:
                    return "已分配";
                case 10:
                    return "退款中";
                case 11:
                    return "已退款";

            }
            return orderStateName;
        }
        private int orderStateImg;

        public void setOrderStateImg(int orderStateImg) {
            this.orderStateImg = orderStateImg;
        }

        /**
         * 0待付款 1正在分配 2已完成 3已评价，4已取消 5待发货 6待配送 7待确认收货
         *0待付款 1正在分配 2已完成 待评价，4已取消   5 待发货 6 待收货 7 待退款 8 已退款
         * @return
         */
        public int getOrderStateImg() {
            switch (orderState) {
                case 0:
                    return R.mipmap.dfk;
                case 1:
                    return R.mipmap.zzfp;


                case 3:
                    return R.mipmap.dpj;

                case 4:
                    return R.mipmap.yqx;

                case 5:
                    return R.mipmap.dfh;

                case 6:
                    return R.mipmap.dsh;
                case 8:
                    if (isPl==0){
                        return R.mipmap.dpj;
                    }
                    return R.mipmap.ywc;

                case 10:
                    return R.mipmap.dtk;

                case 11:
                    return R.mipmap.ytk;
                case 9:
                    return R.mipmap.yfp;
            }
            return R.mipmap.dfk;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectDesc() {
            return projectDesc;
        }

        public void setProjectDesc(String projectDesc) {
            this.projectDesc = projectDesc;
        }

        public String getProjectVideo() {
            return projectVideo;
        }

        public void setProjectVideo(String projectVideo) {
            this.projectVideo = projectVideo;
        }

        public String getProjectIcon() {
            return projectIcon;
        }

        public void setProjectIcon(String projectIcon) {
            this.projectIcon = projectIcon;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getOrderPayWay() {
            return orderPayWay;
        }

        public void setOrderPayWay(int orderPayWay) {
            this.orderPayWay = orderPayWay;
        }

        public double getOrderPayPrice() {
            return orderPayPrice;
        }

        public void setOrderPayPrice(double orderPayPrice) {
            this.orderPayPrice = orderPayPrice;
        }

        public double getProjectEarnestMoney() {
            return projectEarnestMoney;
        }

        public void setProjectEarnestMoney(double projectEarnestMoney) {
            this.projectEarnestMoney = projectEarnestMoney;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getExpectTime() {
            return expectTime;
        }

        public void setExpectTime(String expectTime) {
            this.expectTime = expectTime;
        }

        public String getMakeTime() {
            return makeTime;
        }

        public void setMakeTime(String makeTime) {
            this.makeTime = makeTime;
        }

        public int getOrderCoupons() {
            return orderCoupons;
        }

        public void setOrderCoupons(int orderCoupons) {
            this.orderCoupons = orderCoupons;
        }

        public String getOrderCouponsId() {
            return orderCouponsId;
        }

        public void setOrderCouponsId(String orderCouponsId) {
            this.orderCouponsId = orderCouponsId;
        }

        public int getProjectNum() {
            return projectNum;
        }

        public void setProjectNum(int projectNum) {
            this.projectNum = projectNum;
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

        public int getExceptId() {
            return exceptId;
        }

        public void setExceptId(int exceptId) {
            this.exceptId = exceptId;
        }

        public int getIsProduct() {
            return isProduct;
        }

        public void setIsProduct(int isProduct) {
            this.isProduct = isProduct;
        }

        public String getRecvId() {
            return recvId;
        }

        public void setRecvId(String recvId) {
            this.recvId = recvId;
        }

        public String getRecvName() {
            return recvName;
        }

        public void setRecvName(String recvName) {
            this.recvName = recvName;
        }

        public String getRecvTel() {
            return recvTel;
        }

        public void setRecvTel(String recvTel) {
            this.recvTel = recvTel;
        }

        public String getRecvAddress() {
            return recvAddress;
        }

        public void setRecvAddress(String recvAddress) {
            this.recvAddress = recvAddress;
        }

        public String getShipmentName() {
            return shipmentName;
        }

        public void setShipmentName(String shipmentName) {
            this.shipmentName = shipmentName;
        }

        public String getShipmentId() {
            return shipmentId;
        }

        public void setShipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
        }

        public int getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(int integralPrice) {
            this.integralPrice = integralPrice;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getReturnPriceTime() {
            return returnPriceTime;
        }

        public void setReturnPriceTime(String returnPriceTime) {
            this.returnPriceTime = returnPriceTime;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public int getShipmentPrice() {
            return shipmentPrice;
        }

        public void setShipmentPrice(int shipmentPrice) {
            this.shipmentPrice = shipmentPrice;
        }

        public void setOrderStateName(String orderStateName) {
            this.orderStateName = orderStateName;
        }
    }

    public static class ExceptorBean {
        /**
         * id : 2
         * stateUsable : 0
         * userNickName : 💮💮医师🔥🔥
         * userHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132
         * userPsd :
         * userTel : 18222703922
         * userClientId : 140fe1da9ece6cb5218
         * userOpenid :
         * userAccessToken :
         * userRefreshToken :
         * userRealState : 0
         * userLevel : 0
         * regDate : 2019-11-02 15:13:12
         * userSign : 这个人很懒，什么都没有留下～
         * isExpert : 1
         */

        private int id;
        private int stateUsable;
        private String userNickName;
        private String userHeadImg;
        private String userPsd;
        private String userTel;
        private String userClientId;
        private String userOpenid;
        private String userAccessToken;
        private String userRefreshToken;
        private int userRealState;
        private int userLevel;
        private String regDate;
        private String userSign;
        private int isExpert;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStateUsable() {
            return stateUsable;
        }

        public void setStateUsable(int stateUsable) {
            this.stateUsable = stateUsable;
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

        public String getUserPsd() {
            return userPsd;
        }

        public void setUserPsd(String userPsd) {
            this.userPsd = userPsd;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public String getUserClientId() {
            return userClientId;
        }

        public void setUserClientId(String userClientId) {
            this.userClientId = userClientId;
        }

        public String getUserOpenid() {
            return userOpenid;
        }

        public void setUserOpenid(String userOpenid) {
            this.userOpenid = userOpenid;
        }

        public String getUserAccessToken() {
            return userAccessToken;
        }

        public void setUserAccessToken(String userAccessToken) {
            this.userAccessToken = userAccessToken;
        }

        public String getUserRefreshToken() {
            return userRefreshToken;
        }

        public void setUserRefreshToken(String userRefreshToken) {
            this.userRefreshToken = userRefreshToken;
        }

        public int getUserRealState() {
            return userRealState;
        }

        public void setUserRealState(int userRealState) {
            this.userRealState = userRealState;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public int getIsExpert() {
            return isExpert;
        }

        public void setIsExpert(int isExpert) {
            this.isExpert = isExpert;
        }
    }
}
