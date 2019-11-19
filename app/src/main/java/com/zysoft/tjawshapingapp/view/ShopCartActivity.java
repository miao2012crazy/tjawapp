package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.UserCartAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.UserCartBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityShopCartBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-08.
 */
public class ShopCartActivity extends CustomBaseActivity {
    private ActivityShopCartBinding bind;
    private List<UserCartBean> list = new ArrayList<>();
    private UserCartAdapter userCartAdapter;
    //    private NetModel netModel;
    private int last_position = -1;
    private List<UserCartBean> select_bean = new ArrayList<>();
    private double totalMoney = 0;
    private boolean isDel = false;

    private int index = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_shop_cart);
        EventBus.getDefault().register(this);


        userCartAdapter = new UserCartAdapter(list);
        bind.recyclerList.setLayoutManager(new LinearLayoutManager(this));
        userCartAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        userCartAdapter.openLoadAnimation();
        bind.recyclerList.setAdapter(userCartAdapter);
        userCartAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            switch (view1.getId()) {
                case R.id.iv_item:
                    CheckBox checkBox = (CheckBox) adapter.getViewByPosition(bind.recyclerList, position, R.id.iv_item);
                    UserCartBean e = list.get(position);
//                        boolean contains = select_bean.contains(e);
                    if (checkBox.isChecked()) {
                        select_bean.add(e);
                    } else {
                        select_bean.remove(e);
                    }
                    updateTotalMoney(select_bean);
                    break;
            }
        });
//        userCartAdapter.setOnItemClickListener((adapter, view1, position) -> {
//            CheckBox checkBox = (CheckBox) adapter.getViewByPosition(bind.recyclerList, position, R.id.iv_item);
//            UserCartBean e = list.get(position);
//            boolean contains = select_bean.contains(e);
//            if (checkBox.isChecked()){
//                select_bean.add(e);
//            }else{
//                select_bean.remove(e);
//            }
//            LogUtils.e(select_bean.size() + "123123");
//            updateTotalMoney(select_bean);
//
//        });
        bind.tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.isShowDialog = true;
                if (select_bean.size() == 0) {
                    UIUtils.showToast("未选择商品！");
                    return;
                }
                AppConstant.SELECT_CART_LIST=select_bean;
//                startActivityBase(ConfirmOrderCartActivity.class);
            }
        });

        bind.tvEt.setOnClickListener(v -> {
            if (isDel) {
                bind.tvEt.setText("编辑");
                isDel = false;
                bind.tvDel.setVisibility(View.GONE);
                bind.llJs.setVisibility(View.VISIBLE);
            } else {
                bind.tvEt.setText("完成");
                isDel = true;
                bind.tvDel.setVisibility(View.VISIBLE);
                bind.llJs.setVisibility(View.GONE);
            }


        });


        bind.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_bean.size() == 0) {
                    UIUtils.showToast("请选择商品！");
                    return;
                }
                StringBuilder cartId = new StringBuilder();
                for (UserCartBean item : select_bean) {
                    cartId.append(item.getId());
                    cartId.append("#");
                }


                map.clear();
                map.put("cartId", cartId);
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                NetModel.getInstance().getDataFromNet("DEL_CART", HttpUrls.DEL_CART, map);


            }
        });
        bind.ivItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //全选
                    select_bean.clear();
                    select_bean.addAll(list);
                    for (UserCartBean item : list) {
                        item.setSelect(true);
                    }
                    userCartAdapter.notifyDataSetChanged();
                } else {
                    //取消
                    select_bean.clear();
                    for (UserCartBean item : list) {
                        item.setSelect(false);
                    }
                    userCartAdapter.notifyDataSetChanged();

                }

            }
        });


        bind.smartRefresh.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            index = 0;
            getData(index);
        });
        bind.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            index = index + 1;
            getData(index);
        });
        bind.smartRefresh.autoRefresh();

    }





    private void getData(int index) {
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("index", index);
        NetModel.getInstance().getDataFromNet("GET_CART", HttpUrls.GET_CART, map);
    }

    // ￥0.00
    private void updateTotalMoney(List<UserCartBean> select_bean) {
        totalMoney = 0;
        for (UserCartBean item :
                select_bean) {
            totalMoney += item.getProductOrignPrice() * Double.parseDouble(String.valueOf(item.getProductNum()));
        }
        bind.tvTotalMoney.setText("￥" + totalMoney);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppConstant.isShowDialog = true;
    }

    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_CART":
                String data = (String) netResponse.getData();
                List<UserCartBean> userCartBeans = GsonUtil.GsonToList(data, UserCartBean.class);
                if (isRefresh) {
                    list.clear();
                    bind.smartRefresh.finishRefresh(true);
                    bind.smartRefresh.setNoMoreData(false);

                    isRefresh = false;
                }
                if (isLoadMore) {
                    if (userCartBeans.size() == 0) {
                        bind.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        bind.smartRefresh.finishLoadMore(true);
                    }
                    isLoadMore = false;
                }
                list.addAll(userCartBeans);
                if (userCartAdapter != null) {
                    userCartAdapter.notifyDataSetChanged();
                }
                break;
            case "CART_NUM":
                String data1 = (String) netResponse.getData();

                String[] split = data1.split("/");
                map.clear();
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                map.put("cart_id", split[1]);
                map.put("num", split[0]);
                NetModel.getInstance().getDataFromNet("UPDATE_CART", HttpUrls.UPDATE_CART, map);
                AppConstant.isShowDialog = false;
                updateTotalMoney(select_bean);

                break;
//            case "GETRECOMMENDPRODUCT":
//                List<ShopMallBean.DataBean> dataBeans = GsonUtil.jsonToList((String) netResponse.getData(), ShopMallBean.DataBean.class);
//                initProduct(dataBeans);
//                break;
//            case "ADD_CART":
//                break;
//            case "CREATE_ORDER":
//                UIUtils.showToast("下单成功！");
//
//                break;
            case "DEL_CART":
                select_bean.clear();
                isRefresh = true;
                index = 0;
                getData(index);
                break;
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        last_position = -1;
        select_bean.clear();

        updateTotalMoney(select_bean);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
