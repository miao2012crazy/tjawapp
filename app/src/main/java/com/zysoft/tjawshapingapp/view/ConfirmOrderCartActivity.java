package com.zysoft.tjawshapingapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.bean.UserCartBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityConfirmCartOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpConstant;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by mr.miao on 2019/5/19.
 */
public class ConfirmOrderCartActivity extends CustomBaseActivity {


    private ActivityConfirmCartOrderBinding binding;
    private int position = 0;
    private int recvtype = 1;
    private AddressBean addressBean;
    private List<UserCartBean> selectCartList;
    private double v;
    private double totalPrice = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_cart_order);
        binding.llSelectAddr.setOnClickListener(v -> {
            //选择收货地址
            Intent intent = new Intent(ConfirmOrderCartActivity.this, AddressManageActivity.class);
            bundle.clear();
            bundle.putString("isSelect", "1");
            intent.putExtras(bundle);
            startActivityForResult(intent, 999);
        });

        binding.llSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"自提", "物流"};
                new QMUIDialog.CheckableDialogBuilder(ConfirmOrderCartActivity.this)
                        .setCheckedIndex(recvtype)
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recvtype = which;
                                updateUI(recvtype);
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });

        selectCartList = AppConstant.SELECT_CART_LIST;
        for (UserCartBean product : selectCartList) {
            View inflate = UIUtils.inflate(R.layout.item_order_product);
            ImageView ivProductImg = inflate.findViewById(R.id.tv_prouct_img);
            TextView tv_product_title = inflate.findViewById(R.id.tv_product_title);
            TextView tv_product_desc = inflate.findViewById(R.id.tv_product_desc);
            TextView tv_price = inflate.findViewById(R.id.tv_price);
            TextView amount_view = inflate.findViewById(R.id.amount_view);
            TextView tvjf = inflate.findViewById(R.id.tv_jf);
            TextView tv_is_agent = inflate.findViewById(R.id.tv_is_agent);
//            tv_is_agent.setVisibility(product.getIsAgent()==0?View.INVISIBLE:View.VISIBLE);
//            tvjf.setText("购买返还"+product.getProductReturnPrice()+"积分");

            if (!product.getProductImg().equals(ivProductImg.getTag())) {
                ivProductImg.setTag(null);
                GlideApp.with(ivProductImg.getContext())
                        .load(product.getProductImg())
                        .centerCrop()
                        .transform(new GlideRoundTransform(4))
                        .into(ivProductImg);
                ivProductImg.setTag(product.getProductImg());
            }
            amount_view.setText("X" + product.getProductNum());
            tv_price.setText("¥ " + product.getProductOrignPrice());
            tv_product_title.setText(product.getProductTitle());
            tv_product_desc.setText(product.getProductDesc());
            //金额
            totalPrice += (product.getProductNum() * product.getProductOrignPrice());

            binding.llContainer.addView(inflate);

        }


        binding.tvPayPrice.setText("¥ " + totalPrice);
        binding.tvShipPrice.setText("¥0");

        binding.title.qmTopBar.setTitle("支付");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        initView();

//        initPrice(projectInfo.getProjectEarnestMoney(), 0, binding.amountView.getAmount());
        initClick();
        initPay(true, false, false);
//        initView();
        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean ==null){
            EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_RELOGIN,""));
            return;
        }

        map.clear();
        map.put("userId", userInfoBean.getUserId());
        NetModel.getInstance().getDataFromNet("GET_USER_DEFAULT", HttpUrls.GET_USER_DEFAULT, map);
//        netModel.getDataFromNet("GET_USER_INTEGRAL", HttpUrls.GET_USER_INTEGRAL_USE, map);

        binding.cbIsIntegral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (totalPrice <= v) {
                        binding.tvIntegral.setText("可用" + totalPrice + "积分抵扣" + totalPrice + "元");
                        binding.tvPayPrice.setText("¥0");
                    } else {
                        binding.tvIntegral.setText("可用" + v + "积分抵扣" + v + "元");
                        binding.tvPayPrice.setText("¥ " + (totalPrice - v));
                    }
                } else {
                    binding.tvPayPrice.setText("¥" + totalPrice);
                }
            }
        });

    }

    private void updateUI(int recvtype) {
        binding.llSelectAddr.setVisibility(recvtype == 0 ? View.GONE : View.VISIBLE);
        binding.llZtTel.setVisibility(recvtype == 0 ? View.VISIBLE : View.GONE);
        binding.llShipment.setVisibility(recvtype == 0 ? View.GONE : View.VISIBLE);
        binding.tvTgWay.setText(recvtype == 0 ? "自提" : "物流");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取已选择地址
        if (requestCode == 999 && AppConstant.SELECT_ADDR != null) {
            binding.tvNoAddr.setVisibility(View.GONE);
            addressBean = AppConstant.SELECT_ADDR;
            binding.tvAddrRecv.setText(AppConstant.SELECT_ADDR.getRecvName() + "    " + AppConstant.SELECT_ADDR.getRecvTel());
            binding.tvAddrDetail.setText(AppConstant.SELECT_ADDR.getAddressDetail());
        } else {
            binding.tvNoAddr.setVisibility(View.VISIBLE);
        }
    }

    private void initClick() {

        binding.tvWechat.setOnClickListener(v -> {
            position = 0;
            initPay(true, false, false);
        });


//        binding.tvAlipay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position = 1;
//                initPay(false, true, false);
//
//            }
//        });
//
//
//        binding.tvYl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position = 2;
//
//                initPay(false, false, true);
//
//            }
//        });


    }

    private void initPay(boolean isWechat, boolean isAlipay, boolean isYl) {
//        binding.tvWechat.setBackgroundResource(isWechat ? R.mipmap.ic_wechat_check : R.mipmap.ic_wechat_normal);
//        binding.tvAlipay.setBackgroundResource(isAlipay ? R.mipmap.ic_ali_check : R.mipmap.ic_ali_normal);
//        binding.tvYl.setBackgroundResource(isYl ? R.mipmap.ic_yl_check : R.mipmap.ic_yl_normal);

    }

    private void initView() {

        //提交订单 换取支付
        binding.btnPay.setOnClickListener(view -> {
            /**
             * 创建预付款订单
             *
             * @param projectId  项目id
             * @param exceptTime 期望时间 格式 yyyy-MM-dd HH:mm:ss
             * @param couponsId  优惠券id 没有 传''
             * @param userId     用户id
             * @param type       支付方式 0微信 1支付宝 2银联卡 3钱包余额'
             * @return 微信支付字符串or其他
             */
            if (recvtype == 1 && addressBean == null) {
                showTipe(0, "请选择地址");
                return;
            }
            map.clear();
            String productId = "";
            String count = "";
            for (UserCartBean item : selectCartList) {
                productId = productId + item.getProductId();
                productId = productId + "#";
                count = count + item.getProductNum();
                count = count + "#";
            }
            UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
            if (userInfoBean==null){
                return;
            }
            map.put("productId", productId);
            map.put("userId",userInfoBean.getUserId());
            map.put("type", position);
            if (recvtype == 1) {
                map.put("addressId", addressBean.getId());
            }
            if (recvtype == 0) {
                String s = binding.etSelectTel.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    showTipe(0, "请输入提货手机号");
                    return;
                }
                if (!UIUtils.isTelPhoneNumber(s)) {
                    showTipe(0, "请输入正确手机号码");
                    return;
                }
                map.put("recvTel", s);
            }
            boolean checked = binding.cbIsIntegral.isChecked();
            if (checked) {
                //金额计算
                if (totalPrice <= v) {
                    map.put("integralNum", totalPrice);
                } else {
                    map.put("integralNum", v);
                }
            } else {
                map.put("integralNum", 0);
            }
            map.put("recvType", recvtype);
            map.put("count", count);
            NetModel.getInstance().getDataFromNet("CREATE_ORDER", HttpUrls.CREATEORDER, map);
        });
    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "CREATE_ORDER":
                //创建订单成功 吊起微信支付 appid=wx87d3cc6a3943c5a9&partnerid=1507805611&prepayid=wx22230113723921f2e39958473523105817&package=Sign=WXPay&noncestr=2301137008&timestamp=1558537273&sign=38F0EF19CFFB3391CB5BBBAC16DA7056
                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);
                AppConstant.ORDER_RESULT_BEAN=orderResultBean;
                if (orderResultBean.isIsPay()) {
                    //无需支付 直接下单成功
//                    logu.e("创建订单返回数据:::" + data);
//                String[] split = data.split("&");
                    WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                    builder.setAppId(orderResultBean.getAppid())
                            .setPartnerId(orderResultBean.getPartnerid())
                            .setPrepayId(orderResultBean.getPrepayid())
                            .setPackageValue("Sign=WXPay")
                            .setNonceStr(orderResultBean.getNoncestr())
                            .setTimeStamp(String.valueOf(orderResultBean.getTimestamp()))
                            .setSign(orderResultBean.getSign())
                            .build().toWXPayNotSign(ConfirmOrderCartActivity.this, orderResultBean.getAppid());

                }

//                LogUtils.e(String.valueOf(netResponse.getData()));


                break;


            case "GET_USER_DEFAULT":
                AddressBean addressBean = GsonUtil.GsonToBean((String) netResponse.getData(), AddressBean.class);
                if (addressBean != null) {
                    binding.etSelectTel.setText(addressBean.getRecvTel());
                }

                break;
            case "GET_USER_INTEGRAL":
//                UseIntegralBean useIntegralBean = GsonUtil.GsonToBean((String) netResponse.getData(), UseIntegralBean.class);
//                v = Double.parseDouble(useIntegralBean.getUserIntegral());
//
//                if (totalPrice <= v) {
//                    binding.tvIntegral.setText("可用" + totalPrice + "积分抵扣" + totalPrice + "元");
//                } else {
//                    binding.tvIntegral.setText("可用" + v + "积分抵扣" + v + "元");
//                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.SELECT_ADDR != null) {
            binding.tvNoAddr.setVisibility(View.GONE);
        } else {
            binding.tvNoAddr.setVisibility(View.VISIBLE);
        }
    }

//    private void initPrice(double projectOrginPrice, double couponsPrice, int count) {
//        double v = projectOrginPrice * count - couponsPrice;
//        binding.tvPayPrice.setText("¥" + v);
//        binding.tvPay.setText("¥" + v);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
