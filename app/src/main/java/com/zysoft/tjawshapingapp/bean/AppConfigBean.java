package com.zysoft.tjawshapingapp.bean;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-12.
 */
public class AppConfigBean {


    /**
     * kf : miao2012crazy@163.com
     * center_ad : {"id":2,"projectImg":"http://101.37.64.119/tjawserverapp/static/other/adddl.png","link":"www.baidu.com","appDesc":"ceshk","title":"ceshk"}
     */

    private String kf;
    private CenterAdBean center_ad;

    public String getKf() {
        return kf;
    }

    public void setKf(String kf) {
        this.kf = kf;
    }

    public CenterAdBean getCenter_ad() {
        return center_ad;
    }

    public void setCenter_ad(CenterAdBean center_ad) {
        this.center_ad = center_ad;
    }

    public static class CenterAdBean {
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
}
