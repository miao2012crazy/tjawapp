package com.zysoft.tjawshapingapp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/18.
 */

public abstract class CustomBaseActivity extends AppCompatActivity {

    protected CustomHandlerEvent handlerEvent = new CustomHandlerEvent(UIUtils.getContext());
    protected Bundle bundle = new Bundle();
    protected HashMap<String, Object> map = new HashMap<>();
    private QMUIDialog.MessageDialogBuilder messageDialogBuilder;
    private QMUITipDialog.Builder builder;
    private QMUITipDialog qmuiTipDialog;

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
        builder = new QMUITipDialog.Builder(this);

    }



    /**
     * 提示tip弹窗
     * @param title
     * @param content
     * @return
     */
    protected QMUIDialog.MessageDialogBuilder showTipWhisBtn(String title, String content) {

        if (messageDialogBuilder == null) {
            messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(this);
            messageDialogBuilder
                    .setTitle(title == null ? "提示" : title)
                    .setMessage(content)
                    .addAction("确定", (dialog, index) -> dialog.dismiss());
        } else {
            messageDialogBuilder
                    .setTitle(title == null ? "提示" : title)
                    .setMessage(content);
        }
        return messageDialogBuilder;
    }


    protected void showTipe(int type, String tipStr) {
        switch (type) {
            case 0:
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord(tipStr).create();
                qmuiTipDialog.show();

                break;
            case 1:
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(tipStr).create();
                qmuiTipDialog.show();
                break;
            case 2:
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING).setTipWord(tipStr).create();
                qmuiTipDialog.show();
                break;

        }
        new Handler().postDelayed(() -> {
            if (qmuiTipDialog.isShowing()) {
                qmuiTipDialog.dismiss();
                EventBus.getDefault().post(new NetResponse("TIP_DISMISS", ""));
            }
        }, 1500);


    }

}
