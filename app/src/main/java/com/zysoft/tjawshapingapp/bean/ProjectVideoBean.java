package com.zysoft.tjawshapingapp.bean;

import java.io.Serializable;

/**
 * Created by mr.miao on 2019/5/24.
 */

public class ProjectVideoBean implements Serializable {



    private int id;
    private int projectId;
    private int stateUsable;
    private String videoName;
    private String videoDesc;
    private String videoPath;
    private String regDate;
    private String videoImg;

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

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
}
