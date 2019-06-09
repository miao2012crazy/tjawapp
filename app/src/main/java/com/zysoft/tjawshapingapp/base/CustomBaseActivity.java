package com.zysoft.tjawshapingapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zysoft.baseapp.base.BaseActivity;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

/**
 * Created by mr.miao on 2019/5/18.
 */

public abstract class CustomBaseActivity extends BaseActivity {

    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());
    protected Bundle bundle=new Bundle();

    protected void startActivituCom(Context context, Class clazz, Bundle bundle1){
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle1);
        startActivity(intent);
    }





}
