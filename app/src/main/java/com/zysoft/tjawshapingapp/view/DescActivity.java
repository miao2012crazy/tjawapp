package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.ActivityDescBinding;

/**
 * Created by mr.miao on 2019/8/18.
 */

public class DescActivity extends CustomBaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_desc);
        ActivityDescBinding binding = (ActivityDescBinding) viewDataBinding;
        String type = getIntent().getExtras().getString("type");

        binding.title.qmTopBar.setTitle(type.equalsIgnoreCase("0")?"余额说明":"积分说明");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
    }
}
