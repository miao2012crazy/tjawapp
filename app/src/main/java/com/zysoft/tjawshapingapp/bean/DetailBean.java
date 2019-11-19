package com.zysoft.tjawshapingapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-06.
 */
public class DetailBean {

    /**
     * productLoop : [{"id":1,"productId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://192.168.43.234:8098/static/loop/1573039678286.jpeg","isState":0,"regDate":"2019-11-06 19:27:58","isDetail":0},{"id":2,"productId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://192.168.43.234:8098/static/loop/1573039678331.png","isState":0,"regDate":"2019-11-06 19:27:58","isDetail":0},{"id":3,"productId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://192.168.43.234:8098/static/loop/1573039694177.jpeg","isState":0,"regDate":"2019-11-06 19:28:14","isDetail":0}]
     * productManage : {"id":1,"stateUsable":0,"productName":"这是个面膜","productDesc":"这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜","productOrignPrice":598,"projectMemberPrice":368,"productFirstPrice":338,"productSecondPrice":298,"isShip":0,"shipmentPrice":10,"productIcon":"http://192.168.43.234:8098/static/product/1573028728984.png","productNum":11111,"productSellNum":111,"productLookNum":0,"productOption":6,"productOptionName":"面膜","regDate":"2019-11-06 16:25:29","productPrice":398}
     * productDetail : [{"id":4,"productId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://192.168.43.234:8098/static/detail/1573039969357.jpeg","isState":0,"regDate":"2019-11-06 19:32:49","isDetail":1},{"id":5,"productId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://192.168.43.234:8098/static/detail/1573039969400.jpeg","isState":0,"regDate":"2019-11-06 19:32:49","isDetail":1}]
     */

    private ProductManageBean productManage;
    private List<ProductLoopBean> productLoop;
    private List<ProductDetailBean> productDetail;

    public ProductManageBean getProductManage() {
        return productManage;
    }

    public void setProductManage(ProductManageBean productManage) {
        this.productManage = productManage;
    }

    public List<ProductLoopBean> getProductLoop() {
        return productLoop;
    }

    public void setProductLoop(List<ProductLoopBean> productLoop) {
        this.productLoop = productLoop;
    }

    public List<ProductDetailBean> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<ProductDetailBean> productDetail) {
        this.productDetail = productDetail;
    }

    public static class ProductManageBean implements Serializable {
        /**
         * id : 1
         * stateUsable : 0
         * productName : 这是个面膜
         * productDesc : 这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜
         * productOrignPrice : 598
         * projectMemberPrice : 368
         * productFirstPrice : 338
         * productSecondPrice : 298
         * isShip : 0
         * shipmentPrice : 10
         * productIcon : http://192.168.43.234:8098/static/product/1573028728984.png
         * productNum : 11111
         * productSellNum : 111
         * productLookNum : 0
         * productOption : 6
         * productOptionName : 面膜
         * regDate : 2019-11-06 16:25:29
         * productPrice : 398
         */

        private int id;
        private int stateUsable;
        private String productName;
        private String productDesc;
        private int productOrignPrice;
        private int projectMemberPrice;
        private int productFirstPrice;
        private int productSecondPrice;
        private int isShip;
        private int shipmentPrice;
        private String productIcon;
        private int productNum;
        private int productSellNum;
        private int productLookNum;
        private int productOption;
        private String productOptionName;
        private String regDate;
        private int productPrice;

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public int getProductOrignPrice() {
            return productOrignPrice;
        }

        public void setProductOrignPrice(int productOrignPrice) {
            this.productOrignPrice = productOrignPrice;
        }

        public int getProjectMemberPrice() {
            return projectMemberPrice;
        }

        public void setProjectMemberPrice(int projectMemberPrice) {
            this.projectMemberPrice = projectMemberPrice;
        }

        public int getProductFirstPrice() {
            return productFirstPrice;
        }

        public void setProductFirstPrice(int productFirstPrice) {
            this.productFirstPrice = productFirstPrice;
        }

        public int getProductSecondPrice() {
            return productSecondPrice;
        }

        public void setProductSecondPrice(int productSecondPrice) {
            this.productSecondPrice = productSecondPrice;
        }

        public int getIsShip() {
            return isShip;
        }

        public void setIsShip(int isShip) {
            this.isShip = isShip;
        }

        public int getShipmentPrice() {
            return shipmentPrice;
        }

        public void setShipmentPrice(int shipmentPrice) {
            this.shipmentPrice = shipmentPrice;
        }

        public String getProductIcon() {
            return productIcon;
        }

        public void setProductIcon(String productIcon) {
            this.productIcon = productIcon;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public int getProductSellNum() {
            return productSellNum;
        }

        public void setProductSellNum(int productSellNum) {
            this.productSellNum = productSellNum;
        }

        public int getProductLookNum() {
            return productLookNum;
        }

        public void setProductLookNum(int productLookNum) {
            this.productLookNum = productLookNum;
        }

        public int getProductOption() {
            return productOption;
        }

        public void setProductOption(int productOption) {
            this.productOption = productOption;
        }

        public String getProductOptionName() {
            return productOptionName;
        }

        public void setProductOptionName(String productOptionName) {
            this.productOptionName = productOptionName;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }
    }

    public static class ProductLoopBean {
        /**
         * id : 1
         * productId : 1
         * stateUsable : 0
         * imgName :
         * imgDesc :
         * imgPath : http://192.168.43.234:8098/static/loop/1573039678286.jpeg
         * isState : 0
         * regDate : 2019-11-06 19:27:58
         * isDetail : 0
         */

        private int id;
        private int productId;
        private int stateUsable;
        private String imgName;
        private String imgDesc;
        private String imgPath;
        private int isState;
        private String regDate;
        private int isDetail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getStateUsable() {
            return stateUsable;
        }

        public void setStateUsable(int stateUsable) {
            this.stateUsable = stateUsable;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgDesc() {
            return imgDesc;
        }

        public void setImgDesc(String imgDesc) {
            this.imgDesc = imgDesc;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getIsState() {
            return isState;
        }

        public void setIsState(int isState) {
            this.isState = isState;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public int getIsDetail() {
            return isDetail;
        }

        public void setIsDetail(int isDetail) {
            this.isDetail = isDetail;
        }
    }

    public static class ProductDetailBean {
        /**
         * id : 4
         * productId : 1
         * stateUsable : 0
         * imgName :
         * imgDesc :
         * imgPath : http://192.168.43.234:8098/static/detail/1573039969357.jpeg
         * isState : 0
         * regDate : 2019-11-06 19:32:49
         * isDetail : 1
         */

        private int id;
        private int productId;
        private int stateUsable;
        private String imgName;
        private String imgDesc;
        private String imgPath;
        private int isState;
        private String regDate;
        private int isDetail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getStateUsable() {
            return stateUsable;
        }

        public void setStateUsable(int stateUsable) {
            this.stateUsable = stateUsable;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgDesc() {
            return imgDesc;
        }

        public void setImgDesc(String imgDesc) {
            this.imgDesc = imgDesc;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getIsState() {
            return isState;
        }

        public void setIsState(int isState) {
            this.isState = isState;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public int getIsDetail() {
            return isDetail;
        }

        public void setIsDetail(int isDetail) {
            this.isDetail = isDetail;
        }
    }
}
