package com.zysoft.tjawshapingapp.view.order;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.bean.OrderDetailBean;
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
    private OrderDetailBean.OrderInfoBean orderBean;
    private String order_id;
    private OrderDetailBean orderDetailBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        binding = (ActivityOrderDetailBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        order_id = getIntent().getExtras().getString("ORDER_ID");
        //获取订单详情
        map.clear();
        map.put("orderId", order_id);
        NetModel.getInstance().getAllData("PROJECT_DETAIL", HttpUrls.GET_PROJECTOR_DERDETAIL, map);
        binding.tvSeeShip.setOnClickListener(v -> {
            new QMUIDialog.MessageDialogBuilder(this)
                    .setTitle("物流信息")
                    .setMessage(orderBean.getShipmentName() + " " + orderBean.getShipmentId())
                    .addAction("关闭", (dialog, index) -> dialog.dismiss())
                    .addAction("复制单号", (dialog, index) -> {
                        boolean copy = copy(orderBean.getShipmentId());
                        if (copy) {
                            showTipe(1, "已复制单号到剪切板");
                        } else {
                            showTipe(0, "复制失败");
                        }
                        dialog.dismiss();
                    })
                    .show();
        });
        binding.btnConfirm.setOnClickListener(v -> {
            map.clear();
            map.put("orderId", order_id);
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            NetModel.getInstance().getAllData("CONFIRM_ORDER_USER", HttpUrls.CONFIRM_ORDER_USER, map);
        });
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "PROJECT_DETAIL":
                String data = (String) netResponse.getData();
                LogUtils.e(data);
                orderDetailBean = GsonUtil.GsonToBean(data, OrderDetailBean.class);
                orderBean=orderDetailBean.getOrderInfo();
                binding.setItem(orderBean);
                binding.setItem2(orderDetailBean.getExceptor());
                break;
            case "CONFIRM_ORDER_USER":
                showTipe(1, "已确认收货");
                map.clear();
                map.put("orderId", order_id);
                NetModel.getInstance().getAllData("PROJECT_DETAIL", HttpUrls.GET_PROJECTOR_DERDETAIL, map);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
