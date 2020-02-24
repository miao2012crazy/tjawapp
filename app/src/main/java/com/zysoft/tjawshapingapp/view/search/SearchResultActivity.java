package com.zysoft.tjawshapingapp.view.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ProjectAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivitySearchResultBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.ProjectDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/21.
 */

public class SearchResultActivity extends CustomBaseActivity {
    private int index = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private List<HomeDataBean.ProjectListBean> proudctBeans = new ArrayList<>();
    private ActivitySearchResultBinding binding;
    private ProjectAdapter searchAdapter;
    private String searchKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        binding = (ActivitySearchResultBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        searchAdapter = new ProjectAdapter(proudctBeans);
        searchAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomeDataBean.ProjectListBean projectListBean = proudctBeans.get(position);
            Intent intent = new Intent(SearchResultActivity.this, ProjectDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("PROJECT_ID", String.valueOf(projectListBean.getId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.recyclerList.recyclerList.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));

        binding.recyclerList.recyclerList.setAdapter(searchAdapter);
        searchKey = AppConstant.SEARCH_VALUE;
        binding.title.qmTopBar.setTitle("搜索'" + searchKey + "'");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        getData(index);
        binding.smartRefresh.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            index = 0;
            getData(index);
        });
        binding.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            index = index + 1;
            getData(index);
        });


    }

    private void getData(int i) {
        map.clear();
        map.put("searchValue", searchKey);
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("index", i);
        NetModel.getInstance().getDataFromNet("SEARCH_PROJECT", HttpUrls.SEARCH_PROJECT, map);
    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "SEARCH_PROJECT":
                String data = (String) netResponse.getData();
                List<HomeDataBean.ProjectListBean> userCartBeans = GsonUtil.GsonToList(data, HomeDataBean.ProjectListBean.class);
                int startPosition=proudctBeans.size();
                if (isRefresh) {
                    proudctBeans.clear();
                    binding.smartRefresh.finishRefresh(2);
                    binding.smartRefresh.setNoMoreData(false);

                    isRefresh = false;
                }
                if (isLoadMore) {
                    if (userCartBeans.size() == 0) {
                        binding.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        binding.smartRefresh.finishLoadMore(true);
                    }
                    isLoadMore = false;
                }
                proudctBeans.addAll(userCartBeans);
                if (searchAdapter != null) {
                    searchAdapter.notifyItemRangeChanged(startPosition,userCartBeans.size());
                }
                break;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
