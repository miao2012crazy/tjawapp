package com.zysoft.tjawshapingapp.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.tjawshapingapp.MainActivity;
import com.zysoft.tjawshapingapp.R;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/7/3.
 */

public class SplashActivity extends Activity {
    private HashMap<String, Object> map = new HashMap<>();
    private QMUITipDialog.Builder builder;
    private QMUITipDialog qmuiTipDialog;
    private QMUIDialog.MessageDialogBuilder messageDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*首先启动该Activity，并判断是否是第一次启动,注意，需要添加默认值,
        * 如果是第一次启动，则先进入功能引导页*/
//        boolean isFirstOpen = (boolean) SPUtils.getParam(this, "IS_FIRST", true);
//        if (isFirstOpen) {
//            Intent intent = new Intent(this, WelcomeGuideActivity.class);
//            startActivity(intent);
//            /*注意，需要使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
//            finish();
//            return;
//        }
        /*如果不是第一次启动app，则启动页*/
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(this::enterHomeActivity, 1500);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
