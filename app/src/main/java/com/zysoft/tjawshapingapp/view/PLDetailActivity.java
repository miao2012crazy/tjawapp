package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.UserPLAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityPlListBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-04.
 */
public class PLDetailActivity extends CustomBaseActivity {

    private ActivityPlListBinding binding;
    private int index = 0;
    //0 刷新 1加载
    private String project_id;

    private List<ProjectDetailBean.UserPLBean> mainList = new ArrayList<>();
    private UserPLAdapter userPLAdapter;
    private int startPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_pl_list);
        binding = (ActivityPlListBinding) dataBinding;
        EventBus.getDefault().register(this);
        binding.title.qmTopBar.setTitle("全部评论");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        project_id = getIntent().getExtras().getString("project_id");
        binding.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
//                projectListBeans.clear();
                index = 0;

                getData(index, true);
            }
        });
        binding.smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index = index + 1;
                getData(index, false);
            }
        });
        userPLAdapter = new UserPLAdapter(mainList);
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerList.setAdapter(userPLAdapter);
        userPLAdapter.bindToRecyclerView(binding.recyclerList);
        userPLAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExpandableLayout expandableLayout0 = (ExpandableLayout) adapter.getViewByPosition(binding.recyclerList,position, R.id.expandable_layout_0);
                TextView tvOpen = (TextView) adapter.getViewByPosition(position, R.id.tv_open);
                switch (view.getId()) {
                    case R.id.tv_open:
                        if (expandableLayout0.isExpanded()) {
                            tvOpen.setText("查看全文");
                            expandableLayout0.collapse();
                        } else {
                            tvOpen.setText("收起");
                            expandableLayout0.expand();
                        }
                }
            }
        });

        getData(0, true);

    }

    private void getData(int index, boolean isRefresh) {
        if (isRefresh) {
            startPosition =0;
            mainList.clear();
        }else {
            startPosition =mainList.size()-1;
        }
        map.clear();
        map.put("project_id", project_id);
        map.put("index", index);
        NetModel.getInstance().getDataFromNet("GET_PROJECT_PL", HttpUrls.GET_PROJECT_PL, map);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_PROJECT_PL":

                String data = (String) netResponse.getData();
                List<ProjectDetailBean.UserPLBean> userPLBeans = GsonUtil.GsonToList(data, ProjectDetailBean.UserPLBean.class);
                mainList.addAll(userPLBeans);
                if (binding.smartRefresh.isRefreshing()) {
                    binding.smartRefresh.finishRefresh(2);
                }
                if (binding.smartRefresh.isLoading()) {
                    if (userPLBeans.size() == 0) {
                        binding.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        binding.smartRefresh.finishLoadMore(2);
                    }
                }
                userPLAdapter.notifyItemRangeChanged(startPosition,userPLBeans.size());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
