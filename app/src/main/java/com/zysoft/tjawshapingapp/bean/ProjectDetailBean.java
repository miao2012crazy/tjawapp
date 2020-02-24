package com.zysoft.tjawshapingapp.bean;


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

    public static class ImgDetailBean {
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



    /**
     * projectInfo : {"id":23,"stateUsable":0,"projectName":"眼部","projectDesc":"测试爱吃测试爱吃测试爱吃测试爱吃测试爱吃测试爱吃","productIcon":"http://awapp.beauty521.com/static/project/1574052718264.png","projectVideo":"","projectOrginPrice":1200,"projectMemberPrice":1200,"projectFirstPrice":1200,"projectSecondPrice":1200,"projectEarnestMoney":500,"regDate":"2019-11-18 12:51:58","isRecomment":1,"projectOption":7,"projectOptionName":"眼部","projectSellNum":0,"projectTag":"【小打小闹】"}
     * loop : []
     * imgDetail : []
     * userPLCount : 1
     * userPL : {"userPl":{"id":6,"userId":1,"userNickName":"💮💮夜良人🔥🔥","userTel":"15585513651","userHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132","plContent":"啊啊啊啊啊啊啊","projectId":23,"projectName":"眼部","isState":0,"regDate":"2019-12-04 21:04:06"},"plImgList":[{"id":1,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646149.jpg","regDate":"2019-12-04 21:04:06"},{"id":2,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646252.jpg","regDate":"2019-12-04 21:04:06"},{"id":3,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646349.jpg","regDate":"2019-12-04 21:04:06"},{"id":4,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646461.jpg","regDate":"2019-12-04 21:04:06"}]}
     */

    private int userPLCount;
    private UserPLBean userPL;

    public int getUserPLCount() {
        return userPLCount;
    }

    public void setUserPLCount(int userPLCount) {
        this.userPLCount = userPLCount;
    }

    public UserPLBean getUserPL() {
        return userPL;
    }

    public void setUserPL(UserPLBean userPL) {
        this.userPL = userPL;
    }


    public static class UserPLBean {
        /**
         * userPl : {"id":6,"userId":1,"userNickName":"💮💮夜良人🔥🔥","userTel":"15585513651","userHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132","plContent":"啊啊啊啊啊啊啊","projectId":23,"projectName":"眼部","isState":0,"regDate":"2019-12-04 21:04:06"}
         * plImgList : [{"id":1,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646149.jpg","regDate":"2019-12-04 21:04:06"},{"id":2,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646252.jpg","regDate":"2019-12-04 21:04:06"},{"id":3,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646349.jpg","regDate":"2019-12-04 21:04:06"},{"id":4,"plId":6,"stateUsable":0,"imgPath":"http://awapp.beauty521.com/static/pl/1575464646461.jpg","regDate":"2019-12-04 21:04:06"}]
         */

        private UserPLBean.UserPlBean userPl;
        private List<UserPLBean.PlImgListBean> plImgList;

        public UserPLBean.UserPlBean getUserPl() {
            return userPl;
        }

        public void setUserPl(UserPLBean.UserPlBean userPl) {
            this.userPl = userPl;
        }

        public List<UserPLBean.PlImgListBean> getPlImgList() {
            return plImgList;
        }

        public void setPlImgList(List<UserPLBean.PlImgListBean> plImgList) {
            this.plImgList = plImgList;
        }

        public static class UserPlBean {
            /**
             * id : 6
             * userId : 1
             * userNickName : 💮💮夜良人🔥🔥
             * userTel : 15585513651
             * userHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9j3nfutDyVZgXL6US5lekF6KxdIjlXmKZgTviciaw3WyVdNvibSUbZtJ4fQdNBrHvHU9fudHlianxaA/132
             * plContent : 啊啊啊啊啊啊啊
             * projectId : 23
             * projectName : 眼部
             * isState : 0
             * regDate : 2019-12-04 21:04:06
             */

            private int id;
            private int userId;
            private String userNickName;
            private String userTel;
            private String userHeadImg;
            private String plContent;
            private int projectId;
            private String projectName;
            private int isState;
            private String regDate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getUserTel() {
                return userTel;
            }

            public void setUserTel(String userTel) {
                this.userTel = userTel;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getPlContent() {
                return plContent;
            }

            public void setPlContent(String plContent) {
                this.plContent = plContent;
            }

            public int getProjectId() {
                return projectId;
            }

            public void setProjectId(int projectId) {
                this.projectId = projectId;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
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
        }

        public static class PlImgListBean {
            /**
             * id : 1
             * plId : 6
             * stateUsable : 0
             * imgPath : http://awapp.beauty521.com/static/pl/1575464646149.jpg
             * regDate : 2019-12-04 21:04:06
             */

            private int id;
            private int plId;
            private int stateUsable;
            private String imgPath;
            private String regDate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPlId() {
                return plId;
            }

            public void setPlId(int plId) {
                this.plId = plId;
            }

            public int getStateUsable() {
                return stateUsable;
            }

            public void setStateUsable(int stateUsable) {
                this.stateUsable = stateUsable;
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
    }

}
