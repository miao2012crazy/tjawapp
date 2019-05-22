package com.zysoft.tjawshapingapp;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.view.LoginActivity;
import com.zysoft.tjawshapingapp.viewmodule.MainVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class MainActivity extends CustomBaseActivity {
    private HashMap<String, Object> map = new HashMap<>();
    private String msg;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);
        String login = (String) SPUtils.getParam(UIUtils.getContext(), "USER_INFO", "");
        if (login.equals("")) {
            startActivityBase(LoginActivity.class);
        } else {
            AppConstant.USER_INFO_BEAN = GsonUtil.GsonToBean(login, UserInfoBean.class);
            login();
//            regeditUserIM();
        }
        MainVM mainVM = new MainVM(binding);
        String registrationID = JPushInterface.getRegistrationID(this);
        LogUtils.e(registrationID);
    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "LOGIN_SUCCESS":
                regeditUserIM();
                login();
                break;
        }
    }

    private void login() {
        JMessageClient.login(AppConstant.USER_INFO_BEAN.getUserTel(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    myInfo.setNickname(AppConstant.USER_INFO_BEAN.getUserNickName());
                    File file = CommonUtil.drawableToFile(MainActivity.this,R.mipmap.btn_next,new File("head.png"));
                    JMessageClient.updateUserAvatar(file,"png" ,new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            LogUtils.e("ceshitouxiang"+i+"L:::"+s);
                        }
                    });
                    JMessageClient.updateMyInfo(UserInfo.Field.all, myInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {

                        }
                    });
                    LogUtils.e(i + ":::" + s);
                    UIUtils.showToast("登录成功");

                }else {
                    UIUtils.showToast("登录失败");
                }

            }
        });

    }

    private void regeditUserIM() {
        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        JMessageClient.register(userInfoBean.getUserTel(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    UIUtils.showToast("注册成功");
                    JMessageClient.getUserInfo(userInfoBean.getUserTel(), new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            LogUtils.e(i+"::"+s+"::"+userInfo.toJson());
                        }
                    });
                }

                LogUtils.e(i + ":::::" + s);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
