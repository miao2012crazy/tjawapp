package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import com.zysoft.tjawshapingapp.adapter.NoticeOneAdapter;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.DataBean;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.databinding.FragmentOneBinding;
import com.zysoft.tjawshapingapp.gen.DataBeanDao;
import com.zysoft.tjawshapingapp.view.order.OrderDetailActivity;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mr.miao on 2019/5/21.
 */

public class OneFragment extends BaseLazyFragment {

    private FragmentOneBinding bind;
    private int pageIndex = 0;
    private List<DataBean> mainList = new ArrayList<>();
    private NoticeOneAdapter noticeOneAdapter;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false);
        bind = (FragmentOneBinding) binding;
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        noticeOneAdapter = new NoticeOneAdapter(mainList);
        noticeOneAdapter.openLoadAnimation();
        noticeOneAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        bind.recyclerListGg.recyclerList.setLayoutManager(linearLayoutManager);
        bind.recyclerListGg.recyclerList.setAdapter(noticeOneAdapter);
        noticeOneAdapter.setOnItemClickListener((adapter, view1, position) -> {
            //type 类型： 0下单成功 1付款成功 2订单状态变化 3官方通知 4 新活动 5新项目 6新产品
            DataBean dataBean = mainList.get(position);
            switch (Integer.parseInt(dataBean.getType())) {
                case 0:
                case 1:
                case 2:
                    bundle.clear();
                    bundle.putString("ORDER_ID", String.valueOf(dataBean.getJpushId()));
                    startActivityBase(OrderDetailActivity.class, bundle);
                    break;

                case 5:
                    bundle.clear();
                    bundle.putString("PROJECT_ID", dataBean.getJpushId());
                    startActivityBase(ProjectDetailActivity.class, bundle);
                    break;
                case 6:
                    bundle.clear();
                    bundle.putString("PRODUCT_ID", dataBean.getJpushId());
                    startActivityBase(ProductDetailActivity.class, bundle);
                    break;
                case 3:
                case 4:
                default:
                    bundle.clear();
                    bundle.putString("title", dataBean.getTitle());
                    bundle.putString("url", dataBean.getLinkurl());
                    startActivityBase(WebViewActivity.class, bundle);
                break;
            }


        });
        bind.smartRefresh.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            pageIndex = 0;
            getOrderData(pageIndex);
        });
        bind.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            pageIndex = pageIndex + 1;
            getOrderData(pageIndex);
        });
        getOrderData(pageIndex);
    }

    private void getOrderData(int pageIndex) {
        List<DataBean> list = CustomApplaction.getSession().queryBuilder(DataBean.class).orderDesc(DataBeanDao.Properties.RegDate).offset(pageIndex * 10).limit(10).list();
        if (isRefresh) {
            mainList.clear();
            bind.smartRefresh.finishRefresh(true);
            bind.smartRefresh.setNoMoreData(false);
            isRefresh = false;
        }
        if (isLoadMore) {
            if (list.size() == 0) {
                bind.smartRefresh.finishLoadMoreWithNoMoreData();
            } else {
                bind.smartRefresh.finishLoadMore(true);
            }
            isLoadMore = false;
        }

        mainList.addAll(list);
        noticeOneAdapter.notifyDataSetChanged();


    }
}
