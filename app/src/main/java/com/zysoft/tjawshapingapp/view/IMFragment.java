package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomPagerAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.FragmentImBinding;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by mr.miao on 2019/5/20.
 */

public class IMFragment extends BaseLazyFragment {


    private FragmentImBinding bind;
    //    private Conversation singleConversation;
    private List<BaseLazyFragment> fragmentList = new ArrayList<>();
    private List<String> list_Title = new ArrayList<>();
    private CustomPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_im, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAndroidNativeLightStatusBar(getActivity(),true);
        initTab();
    }

    private void initTab() {
        fragmentList.clear();
        list_Title.clear();
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        list_Title.add("官方通知");
        list_Title.add("我的消息");
        adapter = new CustomPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList, list_Title);
        bind.viewpager.setAdapter(adapter);
        bind.tablayout.setupWithViewPager(bind.viewpager);
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();

    }
}
