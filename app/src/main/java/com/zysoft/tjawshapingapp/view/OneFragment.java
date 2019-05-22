package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.FragmentOneBinding;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


/**
 * Created by mr.miao on 2019/5/21.
 */

public class OneFragment extends CustomBaseFragment {

    private FragmentOneBinding bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }

}
