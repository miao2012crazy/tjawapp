package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.alipay.AliPay;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.bean.ResultBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityConfirmOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.AmountView;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by mr.miao on 2019/5/19.
 */
public class ConfirmOrderActivity extends CustomBaseActivity {


    private ProjectDetailBean.ProjectInfoBean projectInfo;
    private ActivityConfirmOrderBinding binding;
    private List<CouponsBean> couponsBeans;
    private int position = 0;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order);
        EventBus.getDefault().register(this);
        projectInfo = AppConstant.PROJECT_INFO;
        String product_num = getIntent().getExtras().getString("count");
        time = getIntent().getExtras().getString("time");
        projectInfo = (ProjectDetailBean.ProjectInfoBean) getIntent().getSerializableExtra("project");
        initView(product_num, time, projectInfo);

        binding.title.qmTopBar.setTitle("确认支付");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        map.clear();

        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean==null){
            return;
        }
        map.put("userId", userInfoBean.getUserId());
        map.put("projectId", projectInfo.getId());
        NetModel.getInstance().getAllData("COUPONS", HttpUrls.GETUSERCOUPONS, map);
        initPrice(projectInfo.getProjectEarnestMoney(), 0, binding.amountView.getAmount());
        initClick();
        initPay(true, false, false, false);
    }

    private void initClick() {

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

        binding.tvWallet.setOnClickListener(v -> {
            position = 3;
            initPay(false, false, false, true);

        });
    }

    private void initPay(boolean isWechat, boolean isAlipay, boolean isYl, boolean isWallet) {
        binding.tvWechat.setBackgroundResource(isWechat ? R.mipmap.ic_wechat_check : R.mipmap.ic_wechat_normal);
        binding.tvAlipay.setBackgroundResource(isAlipay ? R.mipmap.ic_ali_check : R.mipmap.ic_ali_normal);
        binding.tvYl.setBackgroundResource(isYl ? R.mipmap.ic_yl_check : R.mipmap.ic_yl_normal);
        binding.tvWallet.setBackgroundResource(isWallet ? R.mipmap.ic_wallet_check : R.mipmap.ic_wallet_normal);

    }

    private void initView(String product_num, String time, ProjectDetailBean.ProjectInfoBean projectInfo) {
        binding.amountView.setGoods_storage(999);

        binding.amountView.setAmount(product_num);
        binding.tvProjectName.setText(projectInfo.getProjectName());
        GlideApp.with(ConfirmOrderActivity.this).load(projectInfo.getProductIcon()).error(R.mipmap.sample_add_dl).into(binding.ivIcon);
        binding.tvProjectOption.setText(projectInfo.getProjectName());

        binding.tvPreparePrice.setText("¥" + projectInfo.getProjectEarnestMoney() + "");

        binding.tvPrice.setText("¥" + projectInfo.getProjectOrginPrice() + "");
        binding.tvPayPrice.setText("¥" + projectInfo.getProjectEarnestMoney());

        String s = CommonUtil.ms2date("MM-dd HH:mm", Long.parseLong(time));
        binding.tvTime.setText(s);
        binding.tvConpoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, CouponsSelectActivity.class);
                //选择优惠券
                bundle.putString("coupons", String.valueOf(projectInfo.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                initPrice(projectInfo.getProjectEarnestMoney(),
                        AppConstant.Coupons == null ? 0 : AppConstant.Coupons.getCouponsPrice(), binding.amountView.getAmount());
            }
        });
        //提交订单 换取支付
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("projectId", projectInfo.getId());
                map.put("exceptTime", time);
                map.put("couponsId", AppConstant.Coupons == null ? "" : AppConstant.Coupons.getId());
                UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
                if (userInfoBean==null){
                    return;
                }
                map.put("userId", userInfoBean.getUserId());
                map.put("type", position);
                map.put("count", binding.amountView.getAmount());

                NetModel.getInstance().getDataFromNet("CREATE_ORDER", HttpUrls.CREATEORDER, map);

            }
        });


    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "COUPONS":
                couponsBeans = GsonUtil.GsonToList((String) netResponse.getData(), CouponsBean.class);
                if (couponsBeans.size() != 0) {
                    binding.tvConpoun.setText("有 " + couponsBeans.size() + " 张可用");
                } else {
                    binding.tvConpoun.setEnabled(false);
                    binding.tvConpoun.setText("暂无可用优惠券");
                }
                break;
            case "CREATE_ORDER":
                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);
                initAppPay(position, orderResultBean);
                //创建订单成功 吊起微信支付 appid=wx87d3cc6a3943c5a9&partnerid=1507805611&prepayid=wx22230113723921f2e39958473523105817&package=Sign=WXPay&noncestr=2301137008&timestamp=1558537273&sign=38F0EF19CFFB3391CB5BBBAC16DA7056


                break;
        }
    }

    private void initAppPay(int position, OrderResultBean orderResultBean) {
        switch (position) {
            case 0:
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId(orderResultBean.getAppid())
                        .setPartnerId(orderResultBean.getPartnerid())
                        .setPrepayId(orderResultBean.getPrepayid())
                        .setPackageValue("Sign=WXPay")
                        .setNonceStr(orderResultBean.getNoncestr())
                        .setTimeStamp(String.valueOf(orderResultBean.getTimestamp()))
                        .setSign(orderResultBean.getSign())
                        .build().toWXPayNotSign(ConfirmOrderActivity.this, orderResultBean.getAppid());
                break;
            case 1:
                AliPay.Builder builder1 = new AliPay.Builder(this);
                //支付宝
                builder1.setPayCallBackListener((status, resultStatus, progress) -> {
                    if (status!=9000){
                        showTipe(0,"支付已取消！");
                        return;
                    }

                });
                //支付
                builder1.pay2(orderResultBean.getAlipayBody());
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.Coupons != null) {
            binding.tvConpoun.setText("- ¥" + AppConstant.Coupons.getCouponsPrice());
            initPrice(projectInfo.getProjectEarnestMoney(), AppConstant.Coupons.getCouponsPrice(), binding.amountView.getAmount());
        }
    }

    private void initPrice(double projectOrginPrice, double couponsPrice, int count) {
        double v = projectOrginPrice * count - couponsPrice;
        binding.tvPayPrice.setText("¥" + v);
        binding.tvPay.setText("¥" + v);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
