package com.zysoft.tjawshapingapp.bean;

import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.tjawshapingapp.R;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public  class DataBean implements BindingAdapterItem{
    /**
     * desc : 您有一笔订单等待付款，请及时付款！
     * id : 201905252311035260001
     * linkurl :
     * pushIcon : http://101.37.64.119/tjawserverapp/static/push/jpush_notification_icon.png
     * regDate : 2019-05-25 23:11:03
     * title : 下单成功通知
     * type : 0
     */
    @Id(autoincrement = true)
    private Long id;

    private String desc;
    private String jpushId;
    private String linkurl;
    private String pushIcon;
    private String regDate;
    private String title;
    private String type;




    @Generated(hash = 452011131)
    public DataBean(Long id, String desc, String jpushId, String linkurl, String pushIcon,
            String regDate, String title, String type) {
        this.id = id;
        this.desc = desc;
        this.jpushId = jpushId;
        this.linkurl = linkurl;
        this.pushIcon = pushIcon;
        this.regDate = regDate;
        this.title = title;
        this.type = type;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }




    public String getJpushId() {
        return jpushId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getPushIcon() {
        return pushIcon;
    }

    public void setPushIcon(String pushIcon) {
        this.pushIcon = pushIcon;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int getViewType() {
        return R.layout.item_gg;
    }
}