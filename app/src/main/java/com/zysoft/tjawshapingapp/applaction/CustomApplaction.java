package com.zysoft.tjawshapingapp.applaction;

import com.zysoft.baseapp.BaseApplaction.BaseApplaction;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by mr.miao on 2019/5/6.
 */

public class CustomApplaction extends BaseApplaction{

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(true);
    }
}
