package com.zysoft.tjawshapingapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.kongzue.kongzueupdatesdk.UpdateInfo;
import com.kongzue.kongzueupdatesdk.UpdateUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.proxy.IUpdateProxy;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AppConfigBean;
import com.zysoft.tjawshapingapp.bean.AppVersionBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.SPUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.http.HttpConstant;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.http.NovateUtil;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.LoginActivity;
import com.zysoft.tjawshapingapp.view.imglook.ImgLookActivity;
import com.zysoft.tjawshapingapp.viewmodule.MainVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import me.jessyan.autosize.utils.LogUtils;

public class MainActivity extends CustomBaseActivity {
    private HashMap<String, Object> map = new HashMap<>();
    private String msg;
    private ViewDataBinding bind;
    private boolean isLogin = false;
    private ActivityMainBinding binding;
    private QMUIDialog show;
    private boolean isDownload = false;

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
//startActivityBase(ImgLookActivity.class);
        NetModel.getInstance().getDataFromNet("GET_APPOTHER_CONFIG", HttpUrls.GET_APPOTHER_CONFIG, map);

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
//                NovateUtil.initBuilder();
                //连接时间 可以忽略
                if (isLogin) {
                    return;
                }
                login();
                break;
            case "GET_APPOTHER_CONFIG":
                String data = String.valueOf(netResponse.getData());
                AppConfigBean appConfigBean = GsonUtil.GsonToBean(data, AppConfigBean.class);
                AppConstant.APP_CONFIG_BEAN = appConfigBean;
                break;
        }
    }

    private void login() {
//        AppConstant.USER_INFO_BEAN.getUserTel()
        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean == null) {
            return;
        }
        JMessageClient.login(userInfoBean.getUserTel(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                LogUtils.e(i + ":::::" + s);
                if (i == 0) {
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    myInfo.setNickname(userInfoBean.getUserNickName());
                    //获取头像
                    String userHeadImg = userInfoBean.getUserHeadImg();
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

                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, myInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            //更新用户信息
                            LogUtils.e("更新用户信息" + i + ":" + s);
                        }
                    });

                    LogUtils.e(i + ":::" + s);
//                    UIUtils.showToast("登录成功");
                    isLogin = true;
                } else if (i == 801003) {
                    regeditUserIM();
//                    UIUtils.showToast("登录失败");
                } else {
//                    UIUtils.showToast("登录失败");

                }

            }

        });

        //绑定bindJPushClientID
        map.put("clientId", JPushInterface.getRegistrationID(this));
        map.put("userTel", AppConstant.USER_INFO_BEAN.getUserTel());
        NetModel.getInstance().getDataFromNet("bindJPushClientID", HttpUrls.bindJPushClientID, map);

    }


    @Override
    protected void onResume() {
        super.onResume();
        checkAppVersion();


//        XUpdate.newBuild(this, HttpUrls.getBaseUrl() + "getAppVersion")
//                .supportBackgroundUpdate(true)
//                .update();
    }

    private void checkAppVersion() {
        if (!isDownload) {
            NetModel.getInstance().getDataFromNet("UPDATE_VERSION", HttpUrls.UPDATE_VERSION, map);

        }
    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "bindJPushClientID":
                String data = (String) netResponse.getData();
                LogUtils.e("绑定" + data);
                break;
            case AppConstant.STATE_TIMEOUT:
                if (show != null && show.isShowing()) {
                    break;
                }
                show = showTipWhisBtn("登录超时", "请重新登录").show();
                show.setOnDismissListener(dialog -> {
                    dialog.dismiss();
                    startActivityBase(LoginActivity.class);
                });
                break;
            case "LOOK_IMG":
                ArrayList<String> imgList = (ArrayList<String>) netResponse.getData();
                Intent intent = new Intent(UIUtils.getContext(), ImgLookActivity.class);
                intent.putStringArrayListExtra("pathList", imgList);
                Bundle bundle = new Bundle();
                bundle.putString("selectId", AppConstant.LOOK_IMG_ID);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case HttpConstant.PROGRESS_DIALOG:
                showTipe(2, "正在加载中...");
                break;
            case HttpConstant.PROGRESS_DIALOG_DISMISS:
                closeDialog();
                break;
            case HttpConstant.STATE_ERROR:
                showTipe(3, String.valueOf(netResponse.getData()));
                break;
            case HttpConstant.STATE_RELOGIN:
                startActivityBase(LoginActivity.class);
                break;
            case "UPDATE_VERSION":
                String data1 = (String) netResponse.getData();
                AppVersionBean appVersionBean = GsonUtil.GsonToBean(data1, AppVersionBean.class);
                UpdateInfo updateInfo = new UpdateInfo()
                        .setInfo(appVersionBean.getUpdateContent())
                        .setVer(appVersionBean.getVersionName())
                        .setDownloadUrl(appVersionBean.getLinkUrl());
                if (appVersionBean.getVersionCode() > CommonUtil.getVersionCode(MainActivity.this)) {
                    showTipWhisBtn("发现新版本" + appVersionBean.getVersionName(), appVersionBean.getUpdateContent(),false).show().setOnDismissListener(dialog -> {
                        UpdateUtil updateUtil = new UpdateUtil(MainActivity.this,BuildConfig.APPLICATION_ID).setOnDownloadListener(new UpdateUtil.OnDownloadListener() {
                            @Override
                            public void onStart(long downloadId) {

                            }

                            @Override
                            public void onDownloading(long downloadId, int progress) {

                            }

                            @Override
                            public void onSuccess(long downloadId) {

                            }

                            @Override
                            public void onCancel(long downloadId) {
                                LogUtils.e("取消下载");
                                isDownload = false;
                            }
                        });
                        updateUtil.doUpdate(updateInfo);
                        isDownload = true;
                    });
                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
