package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityApplyDlBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-18.
 */
public class ApplyDLActivity extends CustomBaseActivity {


    private ActivityApplyDlBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_apply_dl);
        binding = (ActivityApplyDlBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.tvApplyFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请会员
                commitOrder(1);
            }
        });

        binding.tvApplyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请一级代理
                commitOrder(2);

            }
        });


        binding.tvApplyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请二级代理
                commitOrder(3);

            }
        });


    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "APPLY_MEMBER":
                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);
                recharge(orderResultBean,0);
                break;
        }
    }


    private void recharge(OrderResultBean orderResultBean, int position) {
        switch (position){
            case 0:
                AppConstant.PAY_TYPE=2;
                //微信
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId(orderResultBean.getAppid())
                        .setPartnerId(orderResultBean.getPartnerid())
                        .setPrepayId(orderResultBean.getPrepayid())
                        .setPackageValue("Sign=WXPay")
                        .setNonceStr(orderResultBean.getNoncestr())
                        .setTimeStamp(String.valueOf(orderResultBean.getTimestamp()))
                        .setSign(orderResultBean.getSign())
                        .build().toWXPayNotSign(ApplyDLActivity.this, orderResultBean.getAppid());
                break;
            case 1:
                //支付宝



                break;
            case 2:
                //银联






                break;



        }

    }

    /**
     * @param level 1 会员 2一级代理 3二级代理
     */
    private void commitOrder(int level) {
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("level", level);
        map.put("payWay", 0);
        NetModel.getInstance().getDataFromNet("APPLY_MEMBER", HttpUrls.APPLY_MEMBER, map);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
