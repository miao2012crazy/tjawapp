package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityInputPsdBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by mr.miao on 2019/5/16.
 */

public class InputPsdActivity extends CustomBaseActivity {

    private ActivityInputPsdBinding binding;
    private boolean isCheck = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_psd);
        EventBus.getDefault().register(this);
        binding.rlSeePsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = !isCheck;
                if (isCheck) {
                    //显示
                    binding.ivShow.setBackgroundResource(R.mipmap.ic_psd_check);
                    binding.etUserPsd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.etUserPsd.setTransformationMethod(PasswordTransformationMethod.getInstance());


                } else {
                    binding.ivShow.setBackgroundResource(R.mipmap.ic_psd_default);
                    binding.etUserPsd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.etUserPsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                binding.etUserPsd.setSelection(binding.etUserPsd.getText().toString().length());


            }
        });
        binding.etUserPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("")) {
                    binding.btnCommit.setEnabled(true);
                    binding.btnCommit.setBackgroundResource(R.mipmap.btn_next);
                } else {
                    binding.btnCommit.setEnabled(false);
                    binding.btnCommit.setBackgroundResource(R.mipmap.btn_next_true);
                }
            }
        });

        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = binding.etUserPsd.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    UIUtils.showToast("请输入密码！");
                    return;
                }
                map.clear();
                map.put("userTel", AppConstant.USER_PHONE);
                map.put("userPsd", str);
                NetModel.getInstance().getDataFromNet("LOGIN", HttpUrls.LOGIN, map);
            }
        });
        binding.tvForgetPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送短信验证码
                map.clear();
                map.put("userTel", AppConstant.USER_PHONE);
                NetModel.getInstance().getDataFromNet("GET_VERIFY_CODE", HttpUrls.GET_CODE, map);

            }
        });
    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "LOGIN":
                String data = (String) netResponse.getData();
                UserInfoBean userInfoBean = GsonUtil.GsonToBean(data, UserInfoBean.class);
                AppConstant.USER_INFO_BEAN = userInfoBean;
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data);
//                Intent intent = new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityBase(MainActivity.class);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS","登录成功！"));
                finish();
                break;
            case "GET_VERIFY_CODE":
                AppConstant.IS_REGEDIT = false;
                AppConstant.MsgId = (String) netResponse.getData();
                startActivityBase(RegeditActivity.class);
                finish();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
