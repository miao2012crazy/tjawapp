package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.ResultBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.bean.WXBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.SPUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
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

        binding.btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                CustomApplaction.getWXApi().sendReq(req);
            }
        });
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
            case "WXLOGINCODE":
                // code
                WXBean data = (WXBean) netResponse.getData();
                //上传code 换取用户信息
                map.put("code",data.getCode());
                NetModel.getInstance().getDataFromNet("WXDATA",HttpUrls.WXLOGIN,map);
                break;
            case "WXDATA":
                String data1 = (String) netResponse.getData();
                UserInfoBean userInfoBean = GsonUtil.GsonToBean(data1, UserInfoBean.class);
                AppConstant.USER_INFO_BEAN = userInfoBean;
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data1);
//                Intent intent = new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityBase(MainActivity.class);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS","登录成功！"));
                finish();
                break;
            case AppConstant.STATE_BIND_TEL:
                //TODO 绑定手机号
                startActivityBase(LoginActivity.class);


                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
