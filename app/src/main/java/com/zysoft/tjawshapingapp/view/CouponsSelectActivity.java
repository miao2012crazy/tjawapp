package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityCouponsBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/22.
 */

public class CouponsSelectActivity extends CustomBaseActivity {

    private List<CouponsBean> couponsBeans;
    private ActivityCouponsBinding binding;
    private List<BindingAdapterItem> mainList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupons);
        EventBus.getDefault().register(this);
        String coupons = getIntent().getExtras().getString("coupons");
        if (TextUtils.isEmpty(coupons)){

        }else {
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            map.put("projectId",coupons);
            NetModel.getInstance().getAllData("getCoupons", HttpUrls.GETUSERCOUPONS,map);
        }
        CustomTitleBean customTitleBean = new CustomTitleBean("确认支付","",true,-1);
        binding.title.setItem(customTitleBean);
        binding.title.toolbar.setBackgroundColor(Color.WHITE);
        initTitle(binding.title.tvReturn,null);

    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "getCoupons":
                couponsBeans = GsonUtil.GsonToList((String) netResponse.getData(), CouponsBean.class);
                mainList.clear();
                mainList.addAll(couponsBeans);
                setList_V(binding.recyclerList.recyclerList, mainList, handlerEvent, new BindingAdapter.CustomOnClickListener() {
                    @Override
                    public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                        AppConstant.Coupons=(CouponsBean)bindingAdapterItem;
                        finish();
                    }
                });


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
