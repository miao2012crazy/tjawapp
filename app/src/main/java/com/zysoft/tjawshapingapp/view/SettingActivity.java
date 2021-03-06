package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.SPUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivitySettingBinding;
import com.zysoft.tjawshapingapp.view.feedback.FeedBackActivity;

/**
 * Created by mr.miao on 2019/5/26.
 */

public class SettingActivity extends CustomBaseActivity{

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding = (ActivitySettingBinding) viewDataBinding;
        binding.btnReturn.setOnClickListener(v -> {
            AppConstant.USER_INFO_BEAN=null;
            SPUtils.clear(UIUtils.getContext());
            startActivityBase(LoginActivity.class);
            finish();
        });
        binding.title.qmTopBar.setTitle("设置");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        binding.tvUserInfo.setOnClickListener(v -> {
            //个人信息
            UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
            if (userInfoBean==null){
                return;
            }
            startActivityBase(UserInfoActivity.class);
        });

        binding.tvUpdatePsd.setOnClickListener(v -> startActivityBase(UpdatePsdActivity.class));
        binding.tvFeedBack.setOnClickListener(v -> startActivityBase(FeedBackActivity.class));
        binding.tvVersion.setText("当前版本：v"+UIUtils.getVerName());
        binding.llCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
