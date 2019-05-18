package com.zysoft.tjawshapingapp.receive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class JpushReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }


        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            Log.d(TAG,"自定义消息，EXTRA_TITLE="+bundle.getString(JPushInterface.EXTRA_TITLE));
            Log.d(TAG,"自定义消息，EXTRA_MESSAGE="+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.d(TAG,"自定义消息，EXTRA_EXTRA="+bundle.getString(JPushInterface.EXTRA_EXTRA));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            Log.d(TAG,"通知，EXTRA_NOTIFICATION_TITLE="+bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            Log.d(TAG,"通知，EXTRA_MESSAGE="+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.d(TAG,"通知，EXTRA_ALERT="+bundle.getString(JPushInterface.EXTRA_ALERT));
            Log.d(TAG,"通知，EXTRA_EXTRA="+bundle.getString(JPushInterface.EXTRA_EXTRA));

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");

            Log.d(TAG,"通知，EXTRA_NOTIFICATION_TITLE="+bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            Log.d(TAG,"通知，EXTRA_MESSAGE="+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.d(TAG,"通知，EXTRA_ALERT="+bundle.getString(JPushInterface.EXTRA_ALERT));
            Log.d(TAG,"通知，EXTRA_EXTRA="+bundle.getString(JPushInterface.EXTRA_EXTRA));
            Log.d(TAG, "用户点击打开了通知");

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}