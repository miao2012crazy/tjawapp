package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/3/27.
 */

public class UserCartBean {
    private int id;
    private String productId;
    private String productImg;
    private double productOrignPrice;
    private double projectMemberPrice;
    private double productFirstPrice;
    private double productSecondPrice;
    private long productNum;
    private String productDesc;
    private String productTitle;
    private String userId;
    private int isUsable;
    private String regDate;
    private boolean isSelect=false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public double getProductOrignPrice() {
        return productOrignPrice;
    }

    public void setProductOrignPrice(double productOrignPrice) {
        this.productOrignPrice = productOrignPrice;
    }

    public double getProjectMemberPrice() {
        return projectMemberPrice;
    }

    public void setProjectMemberPrice(double projectMemberPrice) {
        this.projectMemberPrice = projectMemberPrice;
    }

    public double getProductFirstPrice() {
        return productFirstPrice;
    }

    public void setProductFirstPrice(double productFirstPrice) {
        this.productFirstPrice = productFirstPrice;
    }

    public double getProductSecondPrice() {
        return productSecondPrice;
    }

    public void setProductSecondPrice(double productSecondPrice) {
        this.productSecondPrice = productSecondPrice;
    }

    public long getProductNum() {
        return productNum;
    }

    public void setProductNum(long productNum) {
        this.productNum = productNum;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(int isUsable) {
        this.isUsable = isUsable;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
