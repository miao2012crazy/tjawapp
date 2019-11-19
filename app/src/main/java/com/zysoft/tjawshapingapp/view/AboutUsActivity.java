package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.ActivityAboutUsBinding;
import com.zysoft.tjawshapingapp.databinding.LayoutWebViewBinding;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-07.
 */
public class AboutUsActivity extends CustomBaseActivity {


    private ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        binding = (ActivityAboutUsBinding) viewDataBinding;
        binding.title.qmTopBar.setTitle("关于爱薇");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());



    }
}
