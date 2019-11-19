package com.zysoft.tjawshapingapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/18.
 */

public class CustomBaseFragment extends Fragment {
    protected HashMap<String, Object> map = new HashMap();
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());
    protected Bundle bundle=new Bundle();
    private QMUITipDialog.Builder builder;
    private QMUITipDialog qmuiTipDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QMUIStatusBarHelper.translucent(getActivity());
        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
        builder = new QMUITipDialog.Builder(getActivity());

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

    protected void startActivityBase(Class clazz,Bundle bundle1) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    protected void startActivityCom(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

}
