package com.zysoft.tjawshapingapp.view.webView;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.LayoutWebViewBinding;

/**
 * Created by mr.miao on 2019/8/18.
 */

public class WebViewActivity extends CustomBaseActivity {


    private LayoutWebViewBinding binding;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.layout_web_view);
        binding = (LayoutWebViewBinding) viewDataBinding;
        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        binding.title.qmTopBar.setTitle(title);
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());


        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.llContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }
}
