package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.DetailBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityConfirmOrderBinding;
import com.zysoft.tjawshapingapp.databinding.ActivityConfirmProductOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.AmountView;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/19.
 */
public class ConfirmProductOrderActivity extends CustomBaseActivity {


    private DetailBean.ProductManageBean projectInfo;
    private ActivityConfirmProductOrderBinding binding;
    private List<CouponsBean> couponsBeans;
    private int position = 0;
    private AddressBean addressBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_product_order);
        EventBus.getDefault().register(this);
        String product_num = getIntent().getExtras().getString("count");
        projectInfo = (DetailBean.ProductManageBean) getIntent().getSerializableExtra("product");
        initView(product_num, projectInfo);

        binding.title.qmTopBar.setTitle("确认支付");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("productId", projectInfo.getId());
        NetModel.getInstance().getAllData("COUPONS", HttpUrls.GETUSERCOUPONSFORPRODUCT, map);
        initPrice(projectInfo.getProductPrice(), 0, binding.amountView.getAmount());
        initClick();
        initPay(true, false, false);
    }

    private void initClick() {

        binding.tvWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                initPay(true, false, false);

            }
        });


        binding.tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                initPay(false, true, false);

            }
        });


        binding.tvYl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;

                initPay(false, false, true);

            }
        });
        binding.llSelectAddr.setOnClickListener(v->{
            Intent intent = new Intent(ConfirmProductOrderActivity.this, AddressManageActivity.class);
            bundle.clear();
            bundle.putString("isSelect", "1");
            intent.putExtras(bundle);
            startActivityForResult(intent, 999);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && AppConstant.SELECT_ADDR != null) {
            binding.tvNoAddr.setVisibility(View.GONE);
            addressBean = AppConstant.SELECT_ADDR;
            binding.tvAddrRecv.setText(AppConstant.SELECT_ADDR.getRecvName() + "    " + AppConstant.SELECT_ADDR.getRecvTel());
            binding.tvAddrDetail.setText(AppConstant.SELECT_ADDR.getAddressDetail());
        } else {
            binding.tvNoAddr.setVisibility(View.VISIBLE);
        }
    }
    private void initPay(boolean isWechat, boolean isAlipay, boolean isYl) {
        binding.tvWechat.setBackgroundResource(isWechat ? R.mipmap.ic_wechat_check : R.mipmap.ic_wechat_normal);
        binding.tvAlipay.setBackgroundResource(isAlipay ? R.mipmap.ic_ali_check : R.mipmap.ic_ali_normal);
        binding.tvYl.setBackgroundResource(isYl ? R.mipmap.ic_yl_check : R.mipmap.ic_yl_normal);

    }

    private void initView(String product_num, DetailBean.ProductManageBean projectInfo) {
        binding.amountView.setGoods_storage(999);

        binding.amountView.setAmount(product_num);
        binding.tvProjectName.setText(projectInfo.getProductName());
        GlideApp.with(ConfirmProductOrderActivity.this).load(projectInfo.getProductIcon()).error(R.mipmap.sample_add_dl).into(binding.ivIcon);
        binding.tvProjectOption.setText(projectInfo.getProductName());

//        binding.tvPreparePrice.setText("¥" + projectInfo.getProjectEarnestMoney() + "");
        String s = projectInfo.getIsShip() == 0 ? projectInfo.getShipmentPrice() + "" : "0";
        binding.tvShipment.setText("¥" +s);
        binding.tvPrice.setText("¥" + projectInfo.getProductOrignPrice() + "");
        binding.tvPayPrice.setText("¥" + projectInfo.getProductPrice()+projectInfo.getShipmentPrice());

        binding.tvConpoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmProductOrderActivity.this, CouponsSelectActivity.class);
                //选择优惠券
                bundle.putString("coupons", String.valueOf(projectInfo.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                initPrice(projectInfo.getProductPrice(),
                        AppConstant.Coupons == null ? 0 : AppConstant.Coupons.getCouponsPrice(), binding.amountView.getAmount());
            }
        });
        //提交订单 换取支付
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("productId", projectInfo.getId());
                map.put("addressId", addressBean.getId());
                map.put("couponsId", AppConstant.Coupons == null ? "" : AppConstant.Coupons.getId());
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                map.put("type", position);
                map.put("count", binding.amountView.getAmount());

                NetModel.getInstance().getDataFromNet("CREATE_ORDER", HttpUrls.CREATEPRODUCTORDER, map);

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
                //创建订单成功 吊起微信支付 appid=wx87d3cc6a3943c5a9&partnerid=1507805611&prepayid=wx22230113723921f2e39958473523105817&package=Sign=WXPay&noncestr=2301137008&timestamp=1558537273&sign=38F0EF19CFFB3391CB5BBBAC16DA7056
                String data = (String) netResponse.getData();
                String[] split = data.split("&");
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
//                builder.setAppId(split[0].split("=")[1])
//                        .setPartnerId(split[1].split("=")[1])
//                        .setPrepayId(split[2].split("=")[1])
//                        .setPackageValue("Sign=WXPay")
//                        .setNonceStr(split[4].split("=")[1])
//                        .setTimeStamp(split[5].split("=")[1])
//                        .setSign(split[6].split("=")[1])
//                        .build().toWXPayNotSign(ConfirmProductOrderActivity.this, split[0].split("=")[1]);

//                    LogUtils.e(String.valueOf(netResponse.getData()));


                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.Coupons != null) {
            binding.tvConpoun.setText("- ¥" + AppConstant.Coupons.getCouponsPrice());
            initPrice(projectInfo.getProductPrice(), AppConstant.Coupons.getCouponsPrice(), binding.amountView.getAmount());
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
