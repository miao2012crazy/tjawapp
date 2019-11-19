package com.zysoft.tjawshapingapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.DeviceUtils;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.SPUtils;
import com.zysoft.tjawshapingapp.common.SoftKeyBoardListener;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.http.NovateUtil;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.LoginActivity;
import com.zysoft.tjawshapingapp.view.im.IMActivity;
import com.zysoft.tjawshapingapp.view.im.TestActivity;
import com.zysoft.tjawshapingapp.viewmodule.MainVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import me.jessyan.autosize.utils.LogUtils;

public class MainActivity extends CustomBaseActivity {
    private HashMap<String, Object> map = new HashMap<>();
    private String msg;
    private ViewDataBinding bind;
    private boolean isLogin = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding = (ActivityMainBinding) this.bind;
        EventBus.getDefault().register(this);
        String login = (String) SPUtils.getParam(UIUtils.getContext(), "USER_INFO", "");
        if (TextUtils.isEmpty(login)) {
            startActivityBase(LoginActivity.class);
        } else {
            AppConstant.USER_INFO_BEAN = GsonUtil.GsonToBean(login, UserInfoBean.class);
            login();
//            regeditUserIM();
        }
        MainVM mainVM = new MainVM(binding);
        String registrationID = JPushInterface.getRegistrationID(this);
        LogUtils.e(registrationID);
        //
//startActivityBase(IMActivity.class);
    }

    private void regeditUserIM() {
        JMessageClient.register(AppConstant.USER_INFO_BEAN.getUserTel(), "666666", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    login();
                    LogUtils.e("注册成功！" + s);
                } else {
                    LogUtils.e("注册失败！" + s);
                }
            }
        });

    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "LOGIN_SUCCESS":
                NovateUtil.initBuilder();
                //连接时间 可以忽略
                if (isLogin) {
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
//        AppConstant.USER_INFO_BEAN.getUserTel()
        JMessageClient.login(AppConstant.USER_INFO_BEAN.getUserTel(), "666666", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                LogUtils.e(i + ":::::" + s);
                if (i == 0) {
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    myInfo.setNickname(AppConstant.USER_INFO_BEAN.getUserNickName());
                    //获取头像
                    String userHeadImg = AppConstant.USER_INFO_BEAN.getUserHeadImg();
                    NovateUtil.getInstance().download(userHeadImg, new DownLoadCallBack() {
                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onSucess(String key, String path, String name, long fileSize) {
                            LogUtils.e("下载完成" + key + ":" + path + ":" + name + ":" + fileSize);
                            //上传
                            File file = new File(path + name);
                            JMessageClient.updateUserAvatar(file, "jpg", new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    LogUtils.e("ceshitouxiang" + i + "L:::" + s);
                                }
                            });
                        }
                    });

                    JMessageClient.updateMyInfo(UserInfo.Field.all, myInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {

                        }
                    });
                    LogUtils.e(i + ":::" + s);
                    UIUtils.showToast("登录成功");
                    isLogin = true;
                } else if (i == 801003) {
                    regeditUserIM();
//                    UIUtils.showToast("登录失败");
                } else {
                    UIUtils.showToast("登录失败");
                }

            }

        });

        //绑定bindJPushClientID
        map.put("clientId", JPushInterface.getRegistrationID(this));
        NetModel.getInstance().getDataFromNet("bindJPushClientID", HttpUrls.bindJPushClientID, map);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "bindJPushClientID":
                String data = (String) netResponse.getData();
                LogUtils.e("绑定"+data);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
