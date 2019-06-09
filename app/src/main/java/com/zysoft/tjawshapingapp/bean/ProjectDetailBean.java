package com.zysoft.tjawshapingapp.bean;

import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.tjawshapingapp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/19.
 */

public class ProjectDetailBean {

    /**
     * projectInfo : {"id":1,"stateUsable":0,"projectName":"3S精雕美鼻 打造东方女性鼻尖鼻根唇角3S精致弧度","projectDesc":"","productIcon":"http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","projectVideo":"","projectOrginPrice":1298,"projectMemberPrice":1200,"projectFirstPrice":1100,"projectSecondPrice":1008,"projectEarnestMoney":0,"regDate":"","isRecomment":1,"projectOption":2,"projectOptionName":"鼻子","projectSellNum":288,"projectTag":"【假体隆鼻】"}
     * loop : [{"id":1,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""},{"id":2,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""},{"id":3,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""}]
     * imgDetail : [{"id":1,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""},{"id":2,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""},{"id":3,"detailId":1,"stateUsable":0,"imgName":"","imgDesc":"","imgPath":"http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg","regDate":""}]
     */

    private ProjectInfoBean projectInfo;
    private List<LoopBean> loop;
    private List<ImgDetailBean> imgDetail;

    public ProjectInfoBean getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfoBean projectInfo) {
        this.projectInfo = projectInfo;
    }

    public List<LoopBean> getLoop() {
        return loop;
    }

    public void setLoop(List<LoopBean> loop) {
        this.loop = loop;
    }

    public List<ImgDetailBean> getImgDetail() {
        return imgDetail;
    }

    public void setImgDetail(List<ImgDetailBean> imgDetail) {
        this.imgDetail = imgDetail;
    }

    public static class ProjectInfoBean implements Serializable {
        /**
         * id : 1
         * stateUsable : 0
         * projectName : 3S精雕美鼻 打造东方女性鼻尖鼻根唇角3S精致弧度
         * projectDesc :
         * productIcon : http://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg
         * projectVideo :
         * projectOrginPrice : 1298
         * projectMemberPrice : 1200
         * projectFirstPrice : 1100
         * projectSecondPrice : 1008
         * projectEarnestMoney : 0
         * regDate :
         * isRecomment : 1
         * projectOption : 2
         * projectOptionName : 鼻子
         * projectSellNum : 288
         * projectTag : 【假体隆鼻】
         */

        private int id;
        private int stateUsable;
        private String projectName;
        private String projectDesc;
        private String productIcon;
        private String projectVideo;
        private double projectOrginPrice;
        private double projectMemberPrice;
        private double projectFirstPrice;
        private double projectSecondPrice;
        private double projectEarnestMoney;
        private String regDate;
        private int isRecomment;
        private int projectOption;
        private String projectOptionName;
        private int projectSellNum;
        private String projectTag;

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

        public String getProductIcon() {
            return productIcon;
        }

        public void setProductIcon(String productIcon) {
            this.productIcon = productIcon;
        }

        public String getProjectVideo() {
            return projectVideo;
        }

        public void setProjectVideo(String projectVideo) {
            this.projectVideo = projectVideo;
        }

        public double getProjectOrginPrice() {
            return projectOrginPrice;
        }

        public void setProjectOrginPrice(int projectOrginPrice) {
            this.projectOrginPrice = projectOrginPrice;
        }

        public double getProjectMemberPrice() {
            return projectMemberPrice;
        }

        public void setProjectMemberPrice(int projectMemberPrice) {
            this.projectMemberPrice = projectMemberPrice;
        }

        public double getProjectFirstPrice() {
            return projectFirstPrice;
        }

        public void setProjectFirstPrice(int projectFirstPrice) {
            this.projectFirstPrice = projectFirstPrice;
        }

        public double getProjectSecondPrice() {
            return projectSecondPrice;
        }

        public void setProjectSecondPrice(int projectSecondPrice) {
            this.projectSecondPrice = projectSecondPrice;
        }

        public double getProjectEarnestMoney() {
            return projectEarnestMoney;
        }

        public void setProjectEarnestMoney(int projectEarnestMoney) {
            this.projectEarnestMoney = projectEarnestMoney;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public int getIsRecomment() {
            return isRecomment;
        }

        public void setIsRecomment(int isRecomment) {
            this.isRecomment = isRecomment;
        }

        public int getProjectOption() {
            return projectOption;
        }

        public void setProjectOption(int projectOption) {
            this.projectOption = projectOption;
        }

        public String getProjectOptionName() {
            return projectOptionName;
        }

        public void setProjectOptionName(String projectOptionName) {
            this.projectOptionName = projectOptionName;
        }

        public int getProjectSellNum() {
            return projectSellNum;
        }

        public void setProjectSellNum(int projectSellNum) {
            this.projectSellNum = projectSellNum;
        }

        public String getProjectTag() {
            return projectTag;
        }

        public void setProjectTag(String projectTag) {
            this.projectTag = projectTag;
        }
    }

    public static class LoopBean {
        /**
         * id : 1
         * detailId : 1
         * stateUsable : 0
         * imgName :
         * imgDesc :
         * imgPath : http://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg
         * regDate :
         */

        private int id;
        private int detailId;
        private int stateUsable;
        private String imgName;
        private String imgDesc;
        private String imgPath;
        private String regDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDetailId() {
            return detailId;
        }

        public void setDetailId(int detailId) {
            this.detailId = detailId;
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

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }

    public static class ImgDetailBean implements BindingAdapterItem {
        /**
         * id : 1
         * detailId : 1
         * stateUsable : 0
         * imgName :
         * imgDesc :
         * imgPath : http://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/statichttp://101.37.64.119/tjawserverapp/static/project/1/imageDetail.jpg
         * regDate :
         */

        private int id;
        private int detailId;
        private int stateUsable;
        private String imgName;
        private String imgDesc;
        private String imgPath;
        private String regDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDetailId() {
            return detailId;
        }

        public void setDetailId(int detailId) {
            this.detailId = detailId;
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

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        @Override
        public int getViewType() {
            return R.layout.item_img;
        }
    }
}
