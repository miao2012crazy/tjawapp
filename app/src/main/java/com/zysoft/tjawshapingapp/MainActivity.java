package com.zysoft.tjawshapingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends CustomBaseActivity {
    private HashMap<String, Object> map = new HashMap<>();
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);

        String registrationID = JPushInterface.getRegistrationID(this);
        NetModel.getInstance().getAllData("HOME_DATA",HttpUrls.GET_HOME_DATA,map);
    }

    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "HOME_DATA":
                String data = (String) netResponse.getData();
                HomeDataBean homeDataBean = GsonUtil.GsonToBean(data, HomeDataBean.class);



                LogUtils.e(data);


                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
