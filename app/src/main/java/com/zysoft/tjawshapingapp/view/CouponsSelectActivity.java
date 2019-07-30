package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CouponsAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
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

    private List<CouponsBean> mainList = new ArrayList<>();
    private ActivityCouponsBinding binding;
    private CouponsAdapter couponsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupons);
        EventBus.getDefault().register(this);
        String coupons = getIntent().getExtras().getString("coupons");
        initList();
        if (TextUtils.isEmpty(coupons)) {

        } else {
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            map.put("projectId", coupons);
            NetModel.getInstance().getAllData("getCoupons", HttpUrls.GETUSERCOUPONS, map);
        }
        CustomTitleBean customTitleBean = new CustomTitleBean("确认支付", "", true, -1);
        binding.title.setItem(customTitleBean);
        binding.title.toolbar.setBackgroundColor(Color.WHITE);
//        initTitle(binding.title.tvReturn,null);

    }

    private void initList() {
        couponsAdapter = new CouponsAdapter(mainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerList.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.recyclerList.setAdapter(couponsAdapter);
        couponsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppConstant.Coupons = mainList.get(position);
                finish();
            }
        });
    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "getCoupons":
                List<CouponsBean> couponsBeans = GsonUtil.GsonToList((String) netResponse.getData(), CouponsBean.class);
                mainList.clear();
                mainList.addAll(couponsBeans);
                couponsAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
