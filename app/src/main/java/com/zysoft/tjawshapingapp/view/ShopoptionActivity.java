package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.OptionDetailAdapter;
import com.zysoft.tjawshapingapp.adapter.OptionProductAdapter;
import com.zysoft.tjawshapingapp.adapter.ProductAdapter;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.ProductHomeBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.AcrtivityShopOptionBinding;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/9/30.
 */

public class ShopoptionActivity extends CustomBaseActivity {

    private AcrtivityShopOptionBinding binding;
    private List<HomeDataBean.OptionBean> optionList = new ArrayList<>();
    private List<HomeDataBean.ProjectInfoBean> proudctBeans = new ArrayList<>();
    private OptionDetailAdapter optionDetailAdapter;
    private int lastPosition = 0;
    private int index = 0;
    private OptionProductAdapter productAdapter;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.acrtivity_shop_option);
        binding = (AcrtivityShopOptionBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String type = extras.getString("type");
        String option_name = extras.getString("title");
        binding.title.qmTopBar.setTitle(option_name);
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        LinearLayoutManager layout = new LinearLayoutManager(UIUtils.getContext());
        optionDetailAdapter = new OptionDetailAdapter(optionList);
        optionDetailAdapter.openLoadAnimation();
        binding.recyclerListOption.setLayoutManager(layout);
        binding.recyclerListOption.setAdapter(optionDetailAdapter);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        productAdapter = new OptionProductAdapter(proudctBeans);
        productAdapter.openLoadAnimation();
        productAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        binding.recyclerList.setLayoutManager(layoutManager);
        binding.recyclerList.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(ShopoptionActivity.this, ProjectDetailActivity.class);
            bundle.putString("PROJECT_ID", String.valueOf(proudctBeans.get(position).getId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });


        optionDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_option_name:
                    if (position != lastPosition) {
                        optionList.get(lastPosition).setCheck(false);
                        adapter.notifyItemChanged(lastPosition);
                        lastPosition = position;
                    }
                    optionList.get(position).setCheck(true);
                    adapter.notifyItemChanged(position);
                    index = 0;
                    proudctBeans.clear();
                    getData(index);
                    break;
            }
        });




        binding.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                proudctBeans.clear();
                refreshLayout.setNoMoreData(false);
                index = 0;
                getData(index);
            }
        });
        binding.smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index = index+1;
                getData(index);
            }
        });


        map.put("option", type);
        NetModel.getInstance().getDataFromNet("GET_DATA", HttpUrls.GET_OPTION, map);



    }

    private void getData(int index) {
        id = optionList.get(lastPosition).getId();
        map.put("option", id);
        map.put("pageIndex", index);
        NetModel.getInstance().getDataFromNet("GET_PRODUCT_DATA", HttpUrls.GET_OPTION_DATA, map);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_DATA":
                String data = (String) netResponse.getData();
                List<HomeDataBean.OptionBean> optionDetailBeans = GsonUtil.GsonToList(data, HomeDataBean.OptionBean.class);
                optionList.clear();
                optionDetailBeans.get(0).setCheck(true);
                optionList.addAll(optionDetailBeans);
                optionDetailAdapter.notifyDataSetChanged();
                index = 0;
                getData(0);
                break;
            case "GET_PRODUCT_DATA":
                int startPosition=proudctBeans.size();
                String data1 = (String) netResponse.getData();
                List<HomeDataBean.ProjectInfoBean> proudctBeans1 = GsonUtil.GsonToList(data1, HomeDataBean.ProjectInfoBean.class);

                if (binding.smartRefresh.isRefreshing()) {
                    binding.smartRefresh.finishRefresh(true);
                    binding.smartRefresh.setNoMoreData(false);
                }
                if (binding.smartRefresh.isLoading()) {
                    if (proudctBeans1.size() == 0) {
                        binding.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        binding.smartRefresh.finishLoadMore(true);
                    }
                }
                proudctBeans.addAll(proudctBeans1);
                productAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
