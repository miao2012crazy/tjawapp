package com.zysoft.tjawshapingapp.bean;

import java.io.Serializable;

/**
 * Created by mr.miao on 2019/5/24.
 */

public class ProjectVideoBean implements Serializable {

    private int id;
    //type==2 项目id
    //1 商品id
    // 0 活动
    private int projectId;
    private int stateUsable;
    //视频名称
    private String videoName;
    //视频说明
    private String videoDesc;
    //视频路径
    private String videoPath;
    //上传日期
    private String regDate;
    //视频 第一针图片
    private String videoImg;
    // 类型 0 活动 1 商品 2 项目
    private int type;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStateUsable() {
        return stateUsable;
    }

    public void setStateUsable(int stateUsable) {
        this.stateUsable = stateUsable;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
