package com.zysoft.tjawshapingapp.wxapi;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zysoft.tjawshapingapp.MainActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.PayResultBinding;
import com.zysoft.tjawshapingapp.view.order.OrderActivity;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends CustomBaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;
    private PayResultBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.pay_result);
        binding = (PayResultBinding) viewDataBinding;

        binding.title.qmTopBar.setTitle("支付结果");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        api = WXAPIFactory.createWXAPI(this, WXIDConstants.APP_ID);
        api.handleIntent(getIntent(), this);
        binding.btnReturnHome.setOnClickListener(v -> {
            finish();
            finish();
            startActivityBase(OrderActivity.class);
        });
        binding.btnFinish.setOnClickListener(v -> finish());

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case 0:
                showTipe(1,"支付成功");
                binding.btnFinish.setVisibility(View.GONE);
                binding.ivSuccess.setVisibility(View.VISIBLE);

                if (AppConstant.PAY_TYPE==0){
                    binding.btnReturnHome.setVisibility(View.VISIBLE);
                }else {
                    binding.btnReturnHome.setVisibility(View.GONE);
                }

                binding.ivFail.setVisibility(View.GONE);
                EventBus.getDefault().post(new NetResponse("SUCCESSED",""));

                break;
            case -1:
                showTipWhisBtn(null,"内部错误,请联系管理员!");
                break;
            case -2:
                binding.btnFinish.setVisibility(View.VISIBLE);
                binding.btnReturnHome.setVisibility(View.GONE);
                binding.ivFail.setVisibility(View.VISIBLE);
                binding.ivSuccess.setVisibility(View.GONE);
                showTipe(0,"取消支付");
                break;
        }
    }
}