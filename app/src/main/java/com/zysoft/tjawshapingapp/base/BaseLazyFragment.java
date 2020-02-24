package com.zysoft.tjawshapingapp.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/23/15.
 */
public class BaseLazyFragment extends Fragment {
    protected Bundle bundle=new Bundle();
    private static final String TAG = BaseLazyFragment.class.getSimpleName();
    private boolean isPrepared;
    protected HashMap<String, Object> map = new HashMap();
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());
    private QMUITipDialog.Builder builder;
    private QMUITipDialog qmuiTipDialog;
    private QMUIDialog.MessageDialogBuilder messageDialogBuilder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        builder = new QMUITipDialog.Builder(getActivity());
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

    protected void showTipe(int type, String tipStr) {

        switch (type) {
            case 0:
                if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
                    return;
                }
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord(tipStr).create();
                qmuiTipDialog.show();
                new Handler().postDelayed(() -> {
                    if (qmuiTipDialog.isShowing()) {
                        qmuiTipDialog.dismiss();
//                        EventBus.getDefault().post(new NetResponse("TIP_DISMISS", ""));
                    }
                }, 2000);
                break;
            case 1:
                if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
                    return;
                }
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(tipStr).create();
                qmuiTipDialog.show();
                new Handler().postDelayed(() -> {
                    if (qmuiTipDialog.isShowing()) {
                        qmuiTipDialog.dismiss();
//                        EventBus.getDefault().post(new NetResponse("TIP_DISMISS", ""));
                    }
                }, 2000);
                break;
            case 2:
                if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
                    return;
                }
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING).setTipWord(tipStr).create();
                qmuiTipDialog.show();
                break;
            case 3:
                if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
                    return;
                }
                qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING).setTipWord(tipStr).create();
                qmuiTipDialog.show();

                new Handler().postDelayed(() -> {
                    if (qmuiTipDialog!=null&&qmuiTipDialog.isShowing()) {
                        qmuiTipDialog.dismiss();
//                        EventBus.getDefault().post(new NetResponse("TIP_DISMISS", ""));
                    }
                }, 2000);
                break;
        }


    }

    /**
     * 提示tip弹窗
     *
     * @param title
     * @param content
     * @return
     */
    protected QMUIDialog.MessageDialogBuilder showTipWhisBtn(String title, String content) {

        if (messageDialogBuilder == null) {
            messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(getActivity());
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

    protected void closeDialog() {
        if (qmuiTipDialog!=null&&qmuiTipDialog.isShowing()) {
            qmuiTipDialog.dismiss();
//                EventBus.getDefault().post(new NetResponse("TIP_DISMISS", ""));
        }
    }
    protected void startActivityBase(Class clazz,Bundle bundle1) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}