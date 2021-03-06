package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.just.agentweb.LogUtils;
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
import com.zysoft.tjawshapingapp.view.im.util.Constants;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by mr.miao on 2019/5/16.
 */

public class LoginActivity extends CustomBaseActivity implements EasyPermissions.PermissionCallbacks{


    private HashMap<String, Object> map = new HashMap<>();
    private int RC_CAMERA_AND_LOCATION = 99;
    private boolean is_bind_tel = false;
    private String openId="";

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
                showTipe(0,"请输入手机号码！");
                return;
            }

            if (!UIUtils.isTelPhoneNumber(trim)) {
                showTipe(0,"请输入正确手机号码！");
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
            EasyPermissions.requestPermissions(this, "为了您的账户安全，爱薇国际需要获取您的授权",
                    RC_CAMERA_AND_LOCATION, perms);
        }

        binding.btnWechat.setOnClickListener(v -> {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
            req.state = "wechat_sdk_微信登录";
            CustomApplaction.getWXApi().sendReq(req);
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            is_bind_tel = extras.getBoolean("IS_BIND_TEL");
            openId = extras.getString("openId");
            binding.btnWechat.setVisibility(is_bind_tel ? View.GONE : View.VISIBLE);
            binding.tvBindTel.setText(is_bind_tel ? "绑定手机" : "输入手机号码");
        }

        binding.tvUserRegedit.setOnClickListener(v->{
            //用户注册协议
            bundle.clear();
            bundle.putString("title", "官方活动");
            bundle.putString("url", HttpUrls.getBaseUrl()+"user_regedit_agreement");
            startActivityBase(WebViewActivity.class, bundle);
        });
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "LOGIN_CHECK":
                if (is_bind_tel) {
                    showTipe(0, "此手机号已注册！");
                    return;
                }
                //用户已存在 输入密码
                startActivityBase(InputPsdActivity.class);
                finish();

                break;
            case AppConstant.STATE_USER_NOEXIT:
                AppConstant.IS_REGEDIT = true;
                AppConstant.MsgId = (String) netResponse.getData();
                bundle.clear();
                bundle.putString("openId", openId);
                //用户不存在跳转到注册
                startActivityBase(RegeditActivity.class,bundle);
                finish();
                break;
            case "WXLOGINCODE":
                // code
                WXBean data = (WXBean) netResponse.getData();
                //上传code 换取用户信息
                showTipe(2,"正在登录中");
                map.put("code", data.getCode());
                NetModel.getInstance().getDataFromNet("WXDATA", HttpUrls.WXLOGIN, map);
                break;
            case "WXDATA":
                closeDialog();
                String data1 = (String) netResponse.getData();
                UserInfoBean userInfoBean = GsonUtil.GsonToBean(data1, UserInfoBean.class);
                AppConstant.USER_INFO_BEAN = userInfoBean;
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data1);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS", "登录成功！"));
                finish();
                break;
            case AppConstant.STATE_BIND_TEL:
                openId = (String) netResponse.getData();
                //TODO 绑定手机号
                bundle.clear();
                bundle.putBoolean("IS_BIND_TEL", true);
                bundle.putString("openId", openId);
                startActivityBase(LoginActivity.class, bundle);
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //拒绝了权限

    }
}
