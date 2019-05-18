package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityLoginBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by mr.miao on 2019/5/16.
 */

public class LoginActivity extends CustomBaseActivity {


    private HashMap<String, Object> map = new HashMap<>();
    private int RC_CAMERA_AND_LOCATION = 99;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        EventBus.getDefault().register(this);

        binding.etUserTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    binding.btnCheck.setEnabled(true);
                    binding.btnCheck.setBackgroundResource(R.mipmap.btn_next);


                    return;
                } else {
                    binding.btnCheck.setEnabled(false);
                    binding.btnCheck.setBackgroundResource(R.mipmap.btn_next_true);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.btnCheck.setOnClickListener(view -> {
            String trim = binding.etUserTel.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                UIUtils.showToast("请输入手机号码！");
                return;
            }

            if (!UIUtils.isTelPhoneNumber(trim)) {
                UIUtils.showToast("请输入正确手机号码！");
                return;
            }
            AppConstant.USER_PHONE = trim;

            map.clear();
            map.put("userTel", trim);
            NetModel.getInstance().getDataFromNet("LOGIN_CHECK", HttpUrls.CHECK, map);

        });
        String[] perms = {Manifest.permission.LOCATION_HARDWARE, ACCESS_COARSE_LOCATION, READ_PHONE_STATE, ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE, CHANGE_WIFI_STATE};

        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {


        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "LOGIN_CHECK":
                //用户已存在 输入密码
                startActivityBase(InputPsdActivity.class);
                finish();

                break;
            case AppConstant.STATE_USER_NOEXIT:
                AppConstant.IS_REGEDIT = true;
                AppConstant.MsgId = (String) netResponse.getData();
                //用户不存在跳转到注册
                startActivityBase(RegeditActivity.class);
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
