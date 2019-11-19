package com.zysoft.tjawshapingapp.view.feedback;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityFeedBackBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mr.miao on 2019/7/2.
 */

public class FeedBackActivity extends CustomBaseActivity {


    private ActivityFeedBackBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_feed_back);
        binding = (ActivityFeedBackBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.title.qmTopBar.setTitle("意见反馈");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        binding.btnCommit.setOnClickListener(v -> {
            String s = binding.etContent.getText().toString();
            String name = binding.etName.getText().toString();
            String s1 = binding.etContentType.getText().toString();
            if (TextUtils.isEmpty(name)) {
                showTipe(0, "请输入姓名！");
                return;
            }

            if (TextUtils.isEmpty(s)) {
                showTipe(0, "请输入内容！");
                return;
            }
            map.clear();
            map.put("userId", AppConstant.USER_INFO_BEAN == null ? "" : AppConstant.USER_INFO_BEAN.getUserId());
            map.put("content", s);
            map.put("userName", name);
            map.put("contractType", s1);
            NetModel.getInstance().getDataFromNet("FEED_BACK", HttpUrls.COMMIT_FEED_BACK, map);
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "FEED_BACK":
                showTipWhisBtn(null, (String) netResponse.getData()).show().setOnDismissListener(dialog -> finish());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
