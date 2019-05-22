package com.zysoft.tjawshapingapp.applaction;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.zysoft.baseapp.BaseApplaction.BaseApplaction;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.ui.widget.MyFileNameGenerator;

import org.greenrobot.eventbus.EventBus;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by mr.miao on 2019/5/6.
 */

public class CustomApplaction extends BaseApplaction{
    private HttpProxyCacheServer proxy;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(true);
        JMessageClient.init(this);
        JMessageClient.init(this, true);
        JMessageClient.registerEventReceiver(this);
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        CustomApplaction app = (CustomApplaction) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .fileNameGenerator(new MyFileNameGenerator())
                .build();
    }


    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();
        EventBus.getDefault().post(new NetResponse("MSG",msg));
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                textContent.getText();
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()) {
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                }
                break;
        }

    }

}
