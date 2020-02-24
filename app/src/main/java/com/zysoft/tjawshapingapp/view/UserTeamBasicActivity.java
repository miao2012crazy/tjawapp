package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserTeamBasicBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityTeamBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/4.
 */

public class UserTeamBasicActivity extends CustomBaseActivity {

    private ActivityTeamBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_team);
        binding = (ActivityTeamBinding) viewDataBinding;
        binding.qmuiCenter.setRadiusAndShadow(15, 25, 1f);
        binding.ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.qmuiCenter.setShadowColor(Color.parseColor("#80d4ff"));
        EventBus.getDefault().register(this);
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        NetModel.getInstance().getDataFromNet("GET_USER_TEAM_BASIC_INFO", HttpUrls.GET_USER_TEAM_BASIC_INFO, map);
        binding.llUserTeam.setOnClickListener(v -> {
            //团队
            startActivityBase(UserTeamActivity.class);
        });

        binding.llUserCustomer.setOnClickListener(v -> {
            //直客
            startActivityBase(UserCustomerActivity.class);

        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_USER_TEAM_BASIC_INFO":
                String data = (String) netResponse.getData();
                UserTeamBasicBean userTeamBasicBean = GsonUtil.GsonToBean(data, UserTeamBasicBean.class);
                binding.setItem(userTeamBasicBean);
                binding.tvCustomerIncome.setText("直客收益：" + userTeamBasicBean.getCustomerIncome());
                binding.tvTeamIncome.setText("团队收益：" + userTeamBasicBean.getTeamIncome());
                setLevelImg(userTeamBasicBean.getUserLevel());
                break;
        }
    }

    private void setLevelImg(int userLevel) {
        int userlevelDrawable = 0;
        switch (userLevel) {
            case 0:
                userlevelDrawable = R.mipmap.ic_level_0;
                break;
            case 1:
                userlevelDrawable = R.mipmap.ic_level_1;
                break;
            case 2:
                userlevelDrawable = R.mipmap.ic_level_2;
                break;
            case 3:
                userlevelDrawable = R.mipmap.ic_level_3;
                break;
        }
        binding.ivUserLevel.setImageResource(userlevelDrawable);
//        GlideApp.with(binding.ivUserLevel.getContext()).load(userlevelDrawable).into(binding.ivUserLevel);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
