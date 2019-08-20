package com.zysoft.tjawshapingapp.view.order;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityOrderDetailBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.jessyan.autosize.utils.LogUtils;

/**
 * Created by mr.miao on 2019/8/13.
 */

public class OrderDetailActivity extends CustomBaseActivity {


    private ActivityOrderDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        binding = (ActivityOrderDetailBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        String order_id = getIntent().getExtras().getString("ORDER_ID");
        UIUtils.showToast(order_id);
        //获取订单详情
        map.clear();
        map.put("orderId", order_id);
        NetModel.getInstance().getAllData("PROJECT_DETAIL", HttpUrls.GET_PROJECTOR_DERDETAIL, map);

    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "PROJECT_DETAIL":
                String data = (String) netResponse.getData();
                LogUtils.e(data);
                OrderBean orderBean = GsonUtil.GsonToBean(data, OrderBean.class);
                binding.setItem(orderBean);

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
