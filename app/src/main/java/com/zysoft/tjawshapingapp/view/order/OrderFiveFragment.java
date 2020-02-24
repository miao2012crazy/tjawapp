package com.zysoft.tjawshapingapp.view.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.OrderAdapter;
import com.zysoft.tjawshapingapp.alipay.AliPay;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.pl.FBInputActivity;
import com.zysoft.tjawshapingapp.wxapi.WXPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/23.
 */

public class OrderFiveFragment extends BaseLazyFragment {



    private String type = "-1";
    private FragmentOrderBinding bind;

    private List<OrderBean> mainList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private OrderBean orderBean;
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        initList();
        type = getArguments().getString("type");
        getData(0);
    }
    private void initList() {
        orderAdapter = new OrderAdapter(mainList);
        orderAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        orderAdapter.openLoadAnimation();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        bind.recyclerListOrder.recyclerList.setLayoutManager(linearLayoutManager);
        bind.recyclerListOrder.recyclerList.setAdapter(orderAdapter);

        orderAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ORDER_ID", String.valueOf(mainList.get(position).getOrderId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        orderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_cancel:
                    showTipe(2, "正在加载中...");

                    //取消订单
                    map.clear();
                    map.put("orderId", mainList.get(position).getOrderId());
                    NetModel.getInstance().getDataFromNet("CANCEL_ORDER5", HttpUrls.CANCEL_ORDER, map);
                    break;
                case R.id.btn_pay:
                    showTipe(2, "正在加载中...");

                    map.clear();
                    orderBean = mainList.get(position);
                    map.put("orderId", orderBean.getOrderId());
                    map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());

                    NetModel.getInstance().getDataFromNet("REPAY5", HttpUrls.REPAY, map);


                    break;
                case R.id.btn_pj:
                    bundle.clear();
                    bundle.putString("orderId", mainList.get(position).getOrderId());
                    bundle.putString("projectId", String.valueOf(mainList.get(position).getProjectId()));
                    startActivityBase(FBInputActivity.class, bundle);
                    break;
            }
        });

        bind.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mainList.clear();
                index = 0;
                getData(index);
            }
        });

        bind.smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index = index + 1;
                getData(index);
            }
        });


    }


    private void getData(int index) {
        if (index==0){
            mainList.clear();
        }
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("state", type);
        map.put("index", index);
        NetModel.getInstance().getAllData("ORDER_DATA5", HttpUrls.GET_ORDER_DATA, map);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "ORDER_DATA5":
                List<OrderBean> orderBeans = GsonUtil.GsonToList((String) netResponse.getData(), OrderBean.class);
                int startPosition = mainList.size();
                mainList.addAll(orderBeans);
                orderAdapter.notifyDataSetChanged();
                if (bind.smartRefresh.isRefreshing()) {
                    bind.smartRefresh.finishRefresh(2);
                }

                if (bind.smartRefresh.isLoading()) {
                    if (orderBeans.size() == 0) {
                        bind.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        bind.smartRefresh.finishLoadMore(2);
                    }
                }
                break;
            case "CANCEL_ORDER5":
                closeDialog();
                showTipe(1, String.valueOf(netResponse.getData()));
                break;
            case "REPAY5":
                closeDialog();

                String data = (String) netResponse.getData();
                OrderResultBean orderResultBean = GsonUtil.GsonToBean(data, OrderResultBean.class);

                switch (orderBean.getOrderPayWay()) {
                    case 0:
                        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                        builder.setAppId(orderResultBean.getAppid())
                                .setPartnerId(orderResultBean.getPartnerid())
                                .setPrepayId(orderResultBean.getPrepayid())
                                .setPackageValue("Sign=WXPay")
                                .setNonceStr(orderResultBean.getNoncestr())
                                .setTimeStamp(String.valueOf(orderResultBean.getTimestamp()))
                                .setSign(orderResultBean.getSign())
                                .build().toWXPayNotSign(getActivity(), orderResultBean.getAppid());
                        break;
                    case 1:
                        AliPay.Builder builder1 = new AliPay.Builder(getActivity());
                        //支付宝
                        builder1.setPayCallBackListener((status, resultStatus, progress) -> {
                            if (status != 9000) {
                                showTipe(0, "取消支付");
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
                break;
        }
    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
