package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ProjectAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityOptionBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by mr.miao on 2019/5/25.
 */

public class OptionActvity extends CustomBaseActivity {

    private ActivityOptionBinding binding;
    private List<HomeDataBean.ProjectListBean> mainList2 = new ArrayList<>();
    private ProjectAdapter projectAdapter;
    private int index = 0;
    private String option_id1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_option);
        EventBus.getDefault().register(this);
        binding.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        map.clear();
        initList();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        option_id1 = extras.getString("OPTION_ID");
        if (TextUtils.isEmpty(option_id1)) {
            HomeDataBean.OptionBean option_id = (HomeDataBean.OptionBean) getIntent().getExtras().getSerializable("OPTION_ID");
            binding.qmTopBar.setTitle(option_id.getOptionName());
            option_id1 = String.valueOf(option_id.getId());
        } else {
            String option_name = extras.getString("OPTION_NAME");
            binding.qmTopBar.setTitle(option_name);
        }



        getData(index);
    }

    private void getData(int index) {
        map.put("option", option_id1);
        map.put("pageIndex", index);
        NetModel.getInstance().getDataFromNet("OPTION_DATA", HttpUrls.GET_OPTION_DATA, map);

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        projectAdapter = new ProjectAdapter(mainList2);
        projectAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        projectAdapter.openLoadAnimation();
        binding.recyclerList.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.recyclerList.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeDataBean.ProjectListBean bindingAdapterItem1 = mainList2.get(position);
                Intent intent = new Intent(OptionActvity.this, ProjectDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PROJECT_ID", String.valueOf(bindingAdapterItem1.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mainList2.clear();
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
    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "OPTION_DATA":
                String data = (String) netResponse.getData();
                List<HomeDataBean.ProjectListBean> projectListBean = GsonUtil.GsonToList(data, HomeDataBean.ProjectListBean.class);
                initProject(projectListBean);
                break;

        }
    }


    private void initProject(List<HomeDataBean.ProjectListBean> projectList) {
        mainList2.addAll(projectList);
        projectAdapter.notifyItemRangeChanged(mainList2.size(),projectList.size());
        if (binding.smartRefresh.isRefreshing()){
            binding.smartRefresh.finishRefresh(2);
        }
         if (binding.smartRefresh.isLoading()){
             if (projectList.size()==0){
                 binding.smartRefresh.finishLoadMoreWithNoMoreData();
             }else {
                 binding.smartRefresh.finishLoadMore();
             }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
