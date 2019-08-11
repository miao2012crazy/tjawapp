package com.zysoft.tjawshapingapp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/18.
 */

public abstract class CustomBaseActivity extends AppCompatActivity {

    protected CustomHandlerEvent handlerEvent = new CustomHandlerEvent(UIUtils.getContext());
    protected Bundle bundle = new Bundle();
    protected HashMap<String, Object> map = new HashMap<>();

    protected void startActivituCom(Context context, Class clazz, Bundle bundle1) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    protected void startActivityBase(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
}
