package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.alipay.AliPay;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
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
    private int position = 0;
    private int level = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_apply_dl);
        binding = (ActivityApplyDlBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.title.qmTopBar.setTitle("加入爱薇");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        binding.rgGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_vip:
                    //申请会员
                    level = 1;
                    binding.tvPrice.setText("2980");
                    break;
                case R.id.rb_hhr:
                    //
                    binding.tvPrice.setText("19800");

                    level = 2;

                    break;
                case R.id.rb_ds:
                    //
                    binding.tvPrice.setText("39800");
                    level = 3;

                    break;
            }
        });
        binding.tvWechat.setOnClickListener(v -> {
            position = 0;
            initPay(true, false, false, false);
        });


        binding.tvAlipay.setOnClickListener(v -> {
            position = 1;
            initPay(false, true, false, false);

        });


        binding.tvYl.setOnClickListener(v -> {
            position = 2;
            initPay(false, false, true, false);
        });
        binding.tvPay.setOnClickListener(v -> {
            commitOrder(level);
        });
        initPay(true, false, false, false);

    }


    private void initPay(boolean isWechat, boolean isAlipay, boolean isYl, boolean isWallet) {
        binding.tvWechat.setBackgroundResource(isWechat ? R.mipmap.ic_wechat_check : R.mipmap.ic_wechat_normal);
        binding.tvAlipay.setBackgroundResource(isAlipay ? R.mipmap.ic_ali_check : R.mipmap.ic_ali_normal);
        binding.tvYl.setBackgroundResource(isYl ? R.mipmap.ic_yl_check : R.mipmap.ic_yl_normal);
//        binding.tvWallet.setBackgroundResource(isWallet ? R.mipmap.ic_wallet_check : R.mipmap.ic_wallet_normal);

    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "APPLY_MEMBER":
                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);
                recharge(orderResultBean, position);
                break;
        }
    }


    private void recharge(OrderResultBean orderResultBean, int position) {
        switch (position) {
            case 0:
                AppConstant.PAY_TYPE = 2;
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

    /**
     * @param level1 1 会员 2一级代理 3二级代理
     */
    private void commitOrder(int level1) {
        if (level1 == 0) {
            showTipe(0, "请选择方案");
            return;
        }

        map.clear();
        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean==null){
            return;
        }
        map.put("userId", userInfoBean.getUserId());
        map.put("level", level1);
        map.put("payWay", position);
        NetModel.getInstance().getDataFromNet("APPLY_MEMBER", HttpUrls.APPLY_MEMBER, map);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
