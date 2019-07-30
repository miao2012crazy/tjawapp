package com.zysoft.tjawshapingapp.bean;


/**
 * Created by mr.miao on 2019/5/23.
 */

public class OrderBean {

    /**
     * id : 1
     * orderId : 201905231341455050001
     * projectName : 3S精雕美鼻 打造东方女性鼻尖鼻根唇角3S精致弧度
     * projectDesc :
     * projectVideo :
     * projectIcon : http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg
     * projectNum : 1
     * orderPrice : 1298
     * orderPayWay : 0
     * orderPayPrice : 500
     * projectEarnestMoney : 500
     * orderState : 0
     * expectTime : 2019-05-23 13:30:08
     * makeTime :
     * orderCoupons : 0
     * orderCouponsId :
     * isProduct : 1
     * recvId :
     * recvName :
     * recvTel :
     * recvAddress :
     * regDate : 2019-05-23 13:41:45
     * userId : 2
     */

    private int id;
    private String orderId;
    private String projectName;
    private String projectDesc;
    private String projectVideo;
    private String projectIcon;
    private int projectNum;
    private int orderPrice;
    private int orderPayWay;
    private int orderPayPrice;
    private int projectEarnestMoney;
    private int orderState;
    private String expectTime;
    private String makeTime;
    private int orderCoupons;
    private String orderCouponsId;
    private int isProduct;
    private String recvId;
    private String recvName;
    private String recvTel;
    private String recvAddress;
    private String regDate;
    private String userId;

    private String orderStateName;

    /**
     * 0待付款 1正在分配 2已完成 3已评价，4已取消 5待发货 6待配送 7待确认收货
     *
     * @return
     */
    public String getOrderStateName() {
        switch (orderState) {
            case 0:
                return "待付款";
            case 1:
                return "正在分配";
            case 2:
                return "待收货";
            case 3:
                return "待评价";
            case 4:
                return "已取消";
            case 5:
                return "待发货";
            case 6:
                return "待配送";
            case 7:
                return "待确认收货";
            case 8:
                return "已完成";
        }


        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
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

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderPayWay() {
        return orderPayWay;
    }

    public void setOrderPayWay(int orderPayWay) {
        this.orderPayWay = orderPayWay;
    }

    public int getOrderPayPrice() {
        return orderPayPrice;
    }

    public void setOrderPayPrice(int orderPayPrice) {
        this.orderPayPrice = orderPayPrice;
    }

    public int getProjectEarnestMoney() {
        return projectEarnestMoney;
    }

    public void setProjectEarnestMoney(int projectEarnestMoney) {
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
