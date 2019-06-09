package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/5/25.
 */

public class JPushBean {


    /**
     * data : {"desc":"您有一笔订单等待付款，请及时付款！","id":"201905252311035260001","linkurl":"","pushIcon":"http://101.37.64.119/tjawserverapp/static/push/jpush_notification_icon.png","regDate":"2019-05-25 23:11:03","title":"下单成功通知","type":"0"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

}
