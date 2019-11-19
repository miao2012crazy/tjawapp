package com.zysoft.tjawshapingapp.bean;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-06.
 */
public class ProductHomeBean {


    /**
     * productLoop : [{"loopImgPath":"http://101.37.64.119/tjawserverapp/static/loop/banner.png","loopLink":"","isProduct":0,"productId":""},{"loopImgPath":"http://101.37.64.119/tjawserverapp/static/loop/banner1.png","loopLink":"","isProduct":0,"productId":""},{"loopImgPath":"http://101.37.64.119/tjawserverapp/static/loop/banner2.png","loopLink":"","isProduct":0,"productId":""}]
     * gg : {"id":2,"projectImg":"http://101.37.64.119/tjawserverapp/static/other/adddl.png","link":"www.baidu.com","appDesc":"ceshk","title":"ceshk"}
     * productRecommend : [{"id":1,"stateUsable":0,"productName":"这是个面膜","productDesc":"这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜","productOrignPrice":598,"projectMemberPrice":368,"productFirstPrice":338,"productSecondPrice":298,"isShip":0,"shipmentPrice":10,"productIcon":"http://192.168.43.234:8098/static/product/1573028728984.png","productNum":11111,"productSellNum":111,"productLookNum":0,"productOption":6,"productOptionName":"面膜","regDate":"2019-11-06 16:25:29","productPrice":398},{"id":2,"stateUsable":0,"productName":"商品2","productDesc":"商品2商品2商品2商品2商品2商品2商品2商品2","productOrignPrice":11111,"projectMemberPrice":111,"productFirstPrice":111,"productSecondPrice":111,"isShip":1,"shipmentPrice":10,"productIcon":"http://192.168.43.234:8098/static/product/1573028912262.png","productNum":11111,"productSellNum":111,"productLookNum":0,"productOption":6,"productOptionName":"面膜","regDate":"2019-11-06 16:28:32","productPrice":1111}]
     * productList : [{"id":1,"stateUsable":0,"productName":"这是个面膜","productDesc":"这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜这是个面膜","productOrignPrice":598,"projectMemberPrice":368,"productFirstPrice":338,"productSecondPrice":298,"isShip":0,"shipmentPrice":10,"productIcon":"http://192.168.43.234:8098/static/product/1573028728984.png","productNum":11111,"productSellNum":111,"productLookNum":0,"productOption":6,"productOptionName":"面膜","regDate":"2019-11-06 16:25:29","productPrice":398},{"id":2,"stateUsable":0,"productName":"商品2","productDesc":"商品2商品2商品2商品2商品2商品2商品2商品2","productOrignPrice":11111,"projectMemberPrice":111,"productFirstPrice":111,"productSecondPrice":111,"isShip":1,"shipmentPrice":10,"productIcon":"http://192.168.43.234:8098/static/product/1573028912262.png","productNum":11111,"productSellNum":111,"productLookNum":0,"productOption":6,"productOptionName":"面膜","regDate":"2019-11-06 16:28:32","productPrice":1111}]
     */

    private GgBean gg;
    private List<ProductLoopBean> productLoop;
    private List<ProductRecommendBean> productRecommend;
    private List<ProductListBean> productList;

    public GgBean getGg() {
        return gg;
    }

    public void setGg(GgBean gg) {
        this.gg = gg;
    }

    public List<ProductLoopBean> getProductLoop() {
        return productLoop;
    }

    public void setProductLoop(List<ProductLoopBean> productLoop) {
        this.productLoop = productLoop;
    }

    public List<ProductRecommendBean> getProductRecommend() {
        return productRecommend;
    }

    public void setProductRecommend(List<ProductRecommendBean> productRecommend) {
        this.productRecommend = productRecommend;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class GgBean {
        /**
         * id : 2
         * projectImg : http://101.37.64.119/tjawserverapp/static/other/adddl.png
         * link : www.baidu.com
         * appDesc : ceshk
         * title : ceshk
         */

        private int id;
        private String projectImg;
        private String link;
        private String appDesc;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProjectImg() {
            return projectImg;
        }

        public void setProjectImg(String projectImg) {
            this.projectImg = projectImg;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAppDesc() {
            return appDesc;
        }

        public void setAppDesc(String appDesc) {
            this.appDesc = appDesc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ProductLoopBean {
        /**
         * loopImgPath : http://101.37.64.119/tjawserverapp/static/loop/banner.png
         * loopLink :
         * isProduct : 0
         * productId :
         */

        private String loopImgPath;
        private String loopLink;
        private int isProduct;
        private String productId;

        public String getLoopImgPath() {
            return loopImgPath;
        }

        public void setLoopImgPath(String loopImgPath) {
            this.loopImgPath = loopImgPath;
        }

        public String getLoopLink() {
            return loopLink;
        }

        public void setLoopLink(String loopLink) {
            this.loopLink = loopLink;
        }

        public int getIsProduct() {
            return isProduct;
        }

        public void setIsProduct(int isProduct) {
            this.isProduct = isProduct;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }

    public static class ProductRecommendBean {
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

    public static class ProductListBean {
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
}
