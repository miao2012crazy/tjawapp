package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.alipay.AliPay;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.bean.ResultBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityRechargeBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-18.
 */
public class RechargeActivity extends CustomBaseActivity {

    private ActivityRechargeBinding binding;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recharge);
        binding = (ActivityRechargeBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.title.qmTopBar.setTitle("充值");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        binding.tvWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                initPay(true, false, false, false);
            }
        });


        binding.tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                initPay(false, true, false, false);

            }
        });


        binding.tvYl.setOnClickListener(v -> {
            position = 2;
            initPay(false, false, true, false);
        });

        binding.btnRecharge.setOnClickListener(v -> {
            String s = binding.etPrice.getText().toString();
            if (TextUtils.isEmpty(s)) {
                showTipe(0, "请输入金额");
                return;
            }
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            map.put("price", s);
            map.put("payWay", position);
            NetModel.getInstance().getDataFromNet("RECHARGE", HttpUrls.RECHARGE, map);


        });
        initPay(true, false, false, false);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "RECHARGE":
                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);
                recharge(orderResultBean,position);


                break;
        }
    }

    private void recharge(OrderResultBean orderResultBean, int position) {
        switch (position){
            case 0:
                AppConstant.PAY_TYPE=1;
                //微信
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId(orderResultBean.getAppid())
                        .setPartnerId(orderResultBean.getPartnerid())
                        .setPrepayId(orderResultBean.getPrepayid())
                        .setPackageValue("Sign=WXPay")
                        .setNonceStr(orderResultBean.getNoncestr())
                        .setTimeStamp(String.valueOf(orderResultBean.getTimestamp()))
                        .setSign(orderResultBean.getSign())
                        .build().toWXPayNotSign(RechargeActivity.this, orderResultBean.getAppid());
                break;
            case 1:
                //支付宝
                AliPay.Builder builder1 = new AliPay.Builder(this);
                //支付宝
                builder1.setPayCallBackListener((status, resultStatus, progress) -> {
                    if (status!=9000){
                        showTipe(3,"已取消支付！");

                        return;
                    }
                });
                //支付
                builder1.pay2(orderResultBean.getAlipayBody());
                break;
            case 2:
                //银联
                break;

        }

    }

    private void initPay(boolean isWechat, boolean isAlipay, boolean isYl, boolean isWallet) {
        binding.tvWechat.setBackgroundResource(isWechat ? R.mipmap.ic_wechat_check : R.mipmap.ic_wechat_normal);
        binding.tvAlipay.setBackgroundResource(isAlipay ? R.mipmap.ic_ali_check : R.mipmap.ic_ali_normal);
        binding.tvYl.setBackgroundResource(isYl ? R.mipmap.ic_yl_check : R.mipmap.ic_yl_normal);
    }

}
