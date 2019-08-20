package com.zysoft.tjawshapingapp.applaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.request.target.ViewTarget;
import com.danikula.videocache.HttpProxyCacheServer;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.gen.DaoMaster;
import com.zysoft.tjawshapingapp.gen.DaoSession;
import com.zysoft.tjawshapingapp.ui.widget.MyFileNameGenerator;
import com.zysoft.tjawshapingapp.wxapi.WXIDConstants;

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

public class CustomApplaction extends MultiDexApplication{
    private HttpProxyCacheServer proxy;
    private DaoMaster.DevOpenHelper dbHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static IWXAPI api;
    private static Context app;


    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.indexTag);
        MultiDex.install(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(true);
//        JMessageClient.init(this);
        JMessageClient.init(this, true);
        JMessageClient.registerEventReceiver(this);
        initDatabass();
        api = WXAPIFactory.createWXAPI(this, WXIDConstants.APP_ID,true);
        //将应用的appid注册到微信
        api.registerApp(WXIDConstants.APP_ID);
        app=getApplicationContext();

    }

    public static IWXAPI getWXApi(){
        return api;
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initDatabass() {
        //这里之后会修改，关于升级数据库
        dbHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = dbHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public static DaoSession getSession(){
        return mDaoSession;
    }
    public SQLiteDatabase getDb(){
        return db;
    }

    public static Context getContext() {
        return app;
    }
}
