package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.databinding.ActivityNoticeBinding;

/**
 * Created by mr.miao on 2019/5/26.
 */

public class NoticeActivity extends CustomBaseActivity{

    private ActivityNoticeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        binding= (ActivityNoticeBinding) viewDataBinding;
        CustomTitleBean customTitleBean = new CustomTitleBean("我的消息", "", true, -1);
        binding.title.setItem(customTitleBean);
        binding.title.toolbar.setBackgroundColor(Color.WHITE);
        initTitle(binding.title.tvReturn, null);

    }
}
