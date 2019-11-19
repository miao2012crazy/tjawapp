package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityUpdatePsdBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/16.
 */

public class UpdatePsdActivity extends CustomBaseActivity {

    private ActivityUpdatePsdBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_psd);
        binding = (ActivityUpdatePsdBinding) viewDataBinding;
        binding.title.qmTopBar.setTitle("修改密码");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        binding.btnCheck.setOnClickListener(v -> {
            String userTel = binding.etUserOldPsd.getText().toString();
            String userPsd = binding.etUserNewPsd.getText().toString();
            String confirmPsd = binding.etUserPsdConfirm.getText().toString();
            if (TextUtils.isEmpty(userTel)) {
                showTipe(0, "请输入原密码");
                return;
            }
            if (TextUtils.isEmpty(userPsd)) {
                showTipe(0, "请输入新密码");
                return;
            }
            if (TextUtils.isEmpty(confirmPsd)) {
                showTipe(0, "请输入确认密码");
                return;
            }
            if (!userPsd.equals(confirmPsd)) {
                showTipe(0, "两次密码输入不一致");
                return;
            }

            if (userPsd.length() < 6 || userPsd.length() > 12) {
                showTipe(0, "密码长度6～12位");
                return;
            }

            map.clear();
            map.put("oldPsd", userTel);
            map.put("userNewPsd", userPsd);
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            NetModel.getInstance().getDataFromNet("UPDATE_PSD", HttpUrls.UPDATE_PSD_CENTER, map);
        });
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "UPDATE_PSD":
                //密码修改成功
                showTipWhisBtn(null, "已为您更新密码").show().setOnDismissListener(dialog -> finish());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
