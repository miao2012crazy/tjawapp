package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivitySettingBinding;

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
        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.USER_INFO_BEAN=null;
                SPUtils.clear(UIUtils.getContext());
                startActivityBase(LoginActivity.class);
                finish();
            }
        });
        CustomTitleBean customTitleBean = new CustomTitleBean("设置", "", true, -1);
        binding.title.setItem(customTitleBean);
        binding.title.toolbar.setBackgroundColor(Color.WHITE);
        initTitle(binding.title.tvReturn, null);
    }
}
