package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.UserWalletAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserWalletBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityWalletBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

/**
 * Created by mr.miao on 2019/5/28.
 */

public class UserWalletActivity extends CustomBaseActivity {


    private ActivityWalletBinding binding;
    private List<UserWalletBean.HistoryBean> mainList=new ArrayList<>();
    private UserWalletAdapter userWalletAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        binding = (ActivityWalletBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        userWalletAdapter = new UserWalletAdapter(mainList);
        userWalletAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        userWalletAdapter.openLoadAnimation();

        binding.recyclerListWallet.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerListWallet.setAdapter(userWalletAdapter);

        binding.llBalance.setOnClickListener(v -> {
            bundle.clear();
            bundle.putString("type","0");
            startActivituCom(this,DescActivity.class,bundle);
        });
        binding.llIntegral.setOnClickListener(v -> {
            bundle.clear();
            bundle.putString("type","1");
            startActivituCom(this,DescActivity.class,bundle);
        });
        binding.btnRecharge.setOnClickListener(v -> {
            startActivityBase(RechargeActivity.class);
        });
    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_USERWALLET":
                String data = (String) netResponse.getData();
                LogUtils.e(data);
                UserWalletBean userWalletBean = GsonUtil.GsonToBean(data, UserWalletBean.class);
                binding.setItem(userWalletBean.getWallet());
                mainList.clear();
                mainList.addAll(userWalletBean.getHistory());
                userWalletAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取订单详情
        map.clear();
        NetModel.getInstance().getAllData("GET_USERWALLET", HttpUrls.GET_USER_WALLET, map);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
