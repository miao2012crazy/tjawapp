package com.zysoft.tjawshapingapp.view.order;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.OrderAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/23.
 */

public class OrderOneFragment extends BaseLazyFragment {


    private String type="-1";
    private FragmentOrderBinding bind;

    private List<OrderBean> mainList=new ArrayList<>();
    private OrderAdapter orderAdapter;

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
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("state", type);
        map.put("index", "0");
        NetModel.getInstance().getAllData("ORDER_DATA"+type, HttpUrls.GET_ORDER_DATA, map);
    }


    private void initList() {
        orderAdapter = new OrderAdapter(mainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        bind.recyclerListOrder.recyclerList.setLayoutManager(linearLayoutManager);
        bind.recyclerListOrder.recyclerList.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UIUtils.showToast(mainList.get(position).getOrderId());
            }
        });

    }

    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        if (netResponse.getTag().equals("ORDER_DATA"+type)){
            List<OrderBean> orderBeans = GsonUtil.GsonToList((String) netResponse.getData(), OrderBean.class);
            mainList.clear();
            mainList.addAll(orderBeans);
            orderAdapter.notifyDataSetChanged();
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
