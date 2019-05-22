package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivitySettingPsdBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by mr.miao on 2019/5/17.
 */

public class SettingPsdActivity extends CustomBaseActivity {

    private String userPsd1 = "";
    private String userPsd2 = "";
    private ActivitySettingPsdBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_psd);
        EventBus.getDefault().register(this);
        binding.etUserPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                userPsd1 = editable.toString();
                if (userPsd1.length() == userPsd2.length() && userPsd1.length() != 0) {
                    binding.btnCommit.setEnabled(true);
                } else {
                    binding.btnCommit.setEnabled(false);
                }
            }
        });

        binding.etUserPsdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                userPsd2 = editable.toString();
                if (userPsd1.length() == userPsd2.length() && userPsd1.length() != 0) {
                    binding.btnCommit.setEnabled(true);
                    binding.btnCommit.setBackgroundResource(R.mipmap.btn_next);
                } else {
                    binding.btnCommit.setEnabled(false);
                    binding.btnCommit.setBackgroundResource(R.mipmap.btn_next_true);

                }


            }
        });
        binding.btnCommit.setOnClickListener(view -> {
            if (userPsd1.length() < 6) {
                UIUtils.showToast("密码长度至少6位");
                return;
            }
            if (!userPsd1.equals(userPsd2)) {
                UIUtils.showToast("两次密码输入不一致！");
                return;
            }
            map.clear();
            map.put("userTel", AppConstant.USER_PHONE);
            map.put("userPsd", userPsd1);
            if (AppConstant.IS_REGEDIT) {
                NetModel.getInstance().getDataFromNet("REGEDIT", HttpUrls.REGEDIT, map);
            } else {
                NetModel.getInstance().getDataFromNet("REGEDIT", HttpUrls.UPDATEPSD, map);

            }


        });
    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "REGEDIT":
                String data = (String) netResponse.getData();
                AppConstant.USER_INFO_BEAN = GsonUtil.GsonToBean(data, UserInfoBean.class);
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS","登录成功！"));

                //成功！
                finish();

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
