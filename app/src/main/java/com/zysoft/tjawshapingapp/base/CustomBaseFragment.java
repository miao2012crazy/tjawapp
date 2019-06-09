package com.zysoft.tjawshapingapp.base;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.zysoft.baseapp.base.BaseFragment;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/18.
 */

public class CustomBaseFragment extends BaseFragment {
    protected HashMap<String, Object> map = new HashMap();
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());


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


}
