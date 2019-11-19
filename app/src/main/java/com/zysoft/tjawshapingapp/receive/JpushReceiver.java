package com.zysoft.tjawshapingapp.receive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zysoft.tjawshapingapp.MainActivity;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.bean.JPushBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.gen.DataBeanDao;
import com.zysoft.tjawshapingapp.view.NoticeActivity;

import java.util.Map;

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

//            Log.d(TAG,"通知，EXTRA_NOTIFICATION_TITLE="+bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
//            Log.d(TAG,"通知，EXTRA_MESSAGE="+bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            Log.d(TAG,"通知，EXTRA_ALERT="+bundle.getString(JPushInterface.EXTRA_ALERT));
//            Log.d(TAG,"通知，EXTRA_EXTRA="+bundle.getString(JPushInterface.EXTRA_EXTRA));
            String string = bundle.getString(JPushInterface.EXTRA_EXTRA);

            Map<String, Object> stringObjectMap = GsonUtil.GsonToMaps(string);
            String s = (String) stringObjectMap.get("androidNotification extras key");
            Log.d(TAG,"点击了通知"+s);
            JPushBean jPushBean = GsonUtil.GsonToBean(s, JPushBean.class);
            DataBeanDao dataBeanDao = CustomApplaction.getSession().getDataBeanDao();
            dataBeanDao.insert(jPushBean.getData());
            //直接跳转到消息列表


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");

//            Log.d(TAG,"通知，EXTRA_NOTIFICATION_TITLE="+bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
//            Log.d(TAG,"通知，EXTRA_MESSAGE="+bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            Log.d(TAG,"通知，EXTRA_ALERT="+bundle.getString(JPushInterface.EXTRA_ALERT));

            Intent intent1 = new Intent(context, NoticeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}