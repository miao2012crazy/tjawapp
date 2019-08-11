package com.zysoft.tjawshapingapp.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/18.
 */

public class CustomBaseFragment extends Fragment {
    protected HashMap<String, Object> map = new HashMap();
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());
    protected Bundle bundle=new Bundle();

}
