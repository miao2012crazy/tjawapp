package com.zysoft.tjawshapingapp.bean;


import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.common.UIUtils;

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
    private double orderPrice;
    private int orderPayWay;
    private double orderPayPrice;
    private double projectEarnestMoney;
    private int orderState;
    private String expectTime;
    private String makeTime;
    private double orderCoupons;
    private String orderCouponsId;
    private int projectNum;
    private long userId;
    private String userNickName;
    private long exceptId;
    private int isProduct;
    private String recvId;
    private String recvName;
    private String recvTel;
    private String recvAddress;
    private String shipmentName;
    private String shipmentId;
    private double integralPrice;
    private String payTime;
    private String cancelTime;
    private String returnPriceTime;
    private String regDate;
    private double shipmentPrice;

    public double getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(double shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderPayPrice(double orderPayPrice) {
        this.orderPayPrice = orderPayPrice;
    }

    public void setProjectEarnestMoney(double projectEarnestMoney) {
        this.projectEarnestMoney = projectEarnestMoney;
    }

    public void setOrderCoupons(double orderCoupons) {
        this.orderCoupons = orderCoupons;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public long getExceptId() {
        return exceptId;
    }

    public void setExceptId(long exceptId) {
        this.exceptId = exceptId;
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

    public double getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(double integralPrice) {
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

    private String orderStateName;

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
            case 2:
                return "已完成";
            case 3:
                return "待评价";
            case 4:
                return "已取消";
            case 5:
                return "待发货";
            case 6:
                return "待收货";
            case 7:
                return "待退款";
            case 8:
                return "已退款";
            case 9:
                return "已分配";
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
            case 2:
                return R.mipmap.ywc;
            case 3:
                return R.mipmap.dpj;

            case 4:
                return R.mipmap.yqx;

            case 5:
                return R.mipmap.dfh;

            case 6:
                return R.mipmap.dsh;

            case 7:
                return R.mipmap.dtk;

            case 8:
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

    public int getOrderPayWay() {
        return orderPayWay;
    }

    public void setOrderPayWay(int orderPayWay) {
        this.orderPayWay = orderPayWay;
    }

    public double getOrderPayPrice() {
        return orderPayPrice;
    }

    public double getProjectEarnestMoney() {
        return projectEarnestMoney;
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

    public double getOrderCoupons() {
        return orderCoupons;
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

    public long getUserId() {
        return userId;
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

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }
}
