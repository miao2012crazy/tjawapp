package com.zysoft.tjawshapingapp.bean;

import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.tjawshapingapp.R;

/**
 * Created by mr.miao on 2019/5/22.
 */

public class CouponsBean implements BindingAdapterItem {
    private int id;
    //用户id
    private String userId;
    //状态 0可使用 1已使用 2已过期
    private int couponsState;
    private String couponsName;
    //0商品类 1项目类（指定项目） 2 指定商品（指定商品） 3通用型（全场通用什么都能用） 4指定分类型 （例 面部通用） 5 项目通用
    private int type;
    //指定商品时 携带参数 立即使用跳转到商品详情
    private String productId;
    //指定项目时 携带参数 立即使用跳转到项目详情
    private String projectId;
    //备用字段
    private String couponsIcon;
    //优惠券金额
    private double couponsPrice;
    //过期时间
    private String outTime;
    //获得时间
    private String regDate;
    //项目名称
    private String projectName;
    //商品名称
    private String productName;
    //分类id  type==4 立即使用跳转项目分类列表
    private String optionId;
    //分类名称  可以无视
    private String optionName;
    //是否为满减
    private int isReduce;
    //减80
    private double reducePrice;
    //满200
    private double totalPrice;
    //全场通用/面部项目通用/面膜通用/做个双眼皮项目可使用/购买商品123可使用
    private String couponsDesc;
    private int bg0;
    private int bg1;
    private String typeName;
    private String btnName;

    public String getBtnName() {
        switch (couponsState) {
            case 0:
                return "立即使用";
            case 1:
                return "已使用";

            case 2:
                return "已过期";

        }


        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getTypeName() {
        switch (type) {
            case 0:
                //商品通用
                return isReduce == 1 ? "满减券" : "无门槛";
            case 1:
                //指定项目
                return isReduce == 1 ? "满减券" : "无门槛";
            case 2:
                //指定商品
                return isReduce == 1 ? "满减券" : "无门槛";
            case 3:
                //全场通用
                return isReduce == 1 ? "满减券" : "无门槛";
            case 4:
                //指定分类
                return isReduce == 1 ? "满减券" : optionName + " 无门槛";
            case 5:
                //项目通用
                return isReduce == 1 ? "满减券" : "无门槛";
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getIsReduce() {
        return isReduce;
    }

    public void setIsReduce(int isReduce) {
        this.isReduce = isReduce;
    }

    public double getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(double reducePrice) {
        this.reducePrice = reducePrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCouponsDesc() {
        return couponsDesc;
    }

    public void setCouponsDesc(String couponsDesc) {
        this.couponsDesc = couponsDesc;
    }

    public int getBg0() {
        if (couponsState == 2) {
            return R.mipmap.ic_yhqgq_0;
        }
        switch (id % 3) {
            case 0:
                return R.mipmap.ic_yhq_0_0;
            case 1:
                return R.mipmap.ic_yhq_1_0;
            case 2:
                return R.mipmap.ic_yhq_2_0;
        }
        return bg0;
    }

    public void setBg0(int bg0) {


        this.bg0 = bg0;
    }

    public int getBg1() {
        if (couponsState == 2) {
            return R.mipmap.ic_yhqgq_1;
        }
        switch (id % 3) {
            case 0:
                return R.mipmap.ic_yhq_0_1;
            case 1:
                return R.mipmap.ic_yhq_1_1;
            case 2:
                return R.mipmap.ic_yhq_2_1;
        }


        return bg1;
    }

    public void setBg1(int bg1) {
        this.bg1 = bg1;
    }

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

    public int getCouponsState() {
        return couponsState;
    }

    public void setCouponsState(int couponsState) {
        this.couponsState = couponsState;
    }

    public String getCouponsName() {
        return couponsName;
    }

    public void setCouponsName(String couponsName) {
        this.couponsName = couponsName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCouponsIcon() {
        return couponsIcon;
    }

    public void setCouponsIcon(String couponsIcon) {
        this.couponsIcon = couponsIcon;
    }

    public double getCouponsPrice() {
        return couponsPrice;
    }

    public void setCouponsPrice(double couponsPrice) {
        this.couponsPrice = couponsPrice;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public int getViewType() {
        return R.layout.item_coupons;
    }
}
