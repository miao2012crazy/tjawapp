package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.UserCustomerAdapter;
import com.zysoft.tjawshapingapp.adapter.UserTeamAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserCustomerBean;
import com.zysoft.tjawshapingapp.bean.UserTeamBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityUserCustomerBinding;
import com.zysoft.tjawshapingapp.databinding.ActivityUserTeamBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-30.
 */
public class UserTeamActivity extends CustomBaseActivity {


    private ActivityUserTeamBinding binding;
    private int index = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private List<UserTeamBean.TeamListBeansBean> mainList = new ArrayList<>();
    private UserTeamAdapter userCustomerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_team);
        binding = (ActivityUserTeamBinding) dataBinding;
        binding.title.qmTopBar.setTitle("我的团队");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        EventBus.getDefault().register(this);

        getData(index);
        binding.smartRefresh.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            index = 0;
            getData(index);
        });
        binding.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            index = index + 1;
            getData(index);
        });

        userCustomerAdapter = new UserTeamAdapter(mainList);
        userCustomerAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        userCustomerAdapter.openLoadAnimation();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        binding.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.setAdapter(userCustomerAdapter);
//        userCustomerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//
//            }
//        });
    }

    private void getData(int index) {
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("index", index);
        NetModel.getInstance().getDataFromNet("GET_USER_TEAM", HttpUrls.GET_USER_TEAM, map);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_USER_TEAM":
                String data = (String) netResponse.getData();
                UserTeamBean userCartBeans = GsonUtil.GsonToBean(data, UserTeamBean.class);
                if (isRefresh) {
                    mainList.clear();
                    binding.smartRefresh.finishRefresh(true);
                    binding.smartRefresh.setNoMoreData(false);

                    isRefresh = false;
                }
                if (isLoadMore) {
                    if (userCartBeans.getTeamListBeans().size() == 0) {
                        binding.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        binding.smartRefresh.finishLoadMore(true);
                    }
                    isLoadMore = false;
                }
                binding.tvCustomerCount.setText(String.valueOf(userCartBeans.getCount()));
                binding.tvCustomerIncome.setText(String.valueOf(userCartBeans.getPrice()));
                mainList.addAll(userCartBeans.getTeamListBeans());
                if (userCustomerAdapter != null) {
                    userCustomerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
