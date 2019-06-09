package com.zysoft.tjawshapingapp.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.zysoft.baseapp.base.BaseFragment;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import java.util.HashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/23/15.
 */
public class BaseLazyFragment extends BaseFragment {
    private static final String TAG = BaseLazyFragment.class.getSimpleName();
    private boolean isPrepared;
    protected HashMap<String, Object> map = new HashMap();
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {

    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {

    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {

    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {

    }
    protected void setStatusBar(String rgbStr) {
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getActivity().getWindow().getDecorView();
            this.getActivity().getWindow().clearFlags(201326592);
            int option = 1280;
            decorView.setSystemUiVisibility(option);
            this.getActivity().getWindow().addFlags(-2147483648);
            this.getActivity().getWindow().setStatusBarColor(Color.parseColor(rgbStr));
        } else if(Build.VERSION.SDK_INT >= 19) {
            WindowManager.LayoutParams localLayoutParams = this.getActivity().getWindow().getAttributes();
            localLayoutParams.flags |= 67108864;
        }

        if(Build.VERSION.SDK_INT >= 23) {
            this.getActivity().getWindow().getDecorView().setSystemUiVisibility(9216);
        }

    }
    protected static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}