package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityQrCodeBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/4.
 */

public class UserQrCodeActivity extends CustomBaseActivity {

    private ActivityQrCodeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_qr_code);
        binding = (ActivityQrCodeBinding) viewDataBinding;
EventBus.getDefault().register(this);
        binding.qmTopBar.setTitle("推广码");
        binding.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        map.clear();
        map.put("lastUserId", AppConstant.USER_INFO_BEAN.getUserId());
        NetModel.getInstance().getDataFromNet("GET_QR_CODE", HttpUrls.GET_QR_CODE, map);
        binding.btnSave.setOnClickListener(v -> {
            //保存到本地


        });
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_QR_CODE":
                String data = (String) netResponse.getData();
                if (!data.equals(binding.ivQrCode.getTag())){
                    binding.ivQrCode.setTag(null);
                    GlideApp.with(binding.ivQrCode.getContext())
                            .load(data)
                            .centerCrop()
                            .transform(new GlideRoundTransform(4))
                            .into(binding.ivQrCode);
                    binding.ivQrCode.setTag(data);
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
