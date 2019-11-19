package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ProjectAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/25.
 */

public class OptionFragment extends BaseLazyFragment {


    private String type="-1";
    private FragmentOrderBinding bind;

    private List<HomeDataBean.ProjectListBean> mainList=new ArrayList<>();
    private ProjectAdapter projectAdapter;
    private boolean isRefresh;

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
        QMUIStatusBarHelper.translucent(getActivity());
        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());

    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();

        initList();
        type = getArguments().getString("type");
        map.put("option", type);
        map.put("pageIndex","0");
        NetModel.getInstance().getDataFromNet("OPTION_DATA"+type, HttpUrls.GET_OPTION_DATA,map);
    }

    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        if (netResponse.getTag().equals("OPTION_DATA"+type)){
            List<HomeDataBean.ProjectListBean> projectListBeans = GsonUtil.GsonToList((String) netResponse.getData(), HomeDataBean.ProjectListBean.class);
            initProject(projectListBeans);
        }
        if (netResponse.getTag().equals("REFRESH")){
            isRefresh =true;
//            mainList.clear();
//            map.put("option", type);
//            map.put("pageIndex","0");
//            NetModel.getInstance().getDataFromNet("OPTION_DATA"+type, HttpUrls.GET_OPTION_DATA,map);
        }

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        projectAdapter = new ProjectAdapter(mainList);
        projectAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        projectAdapter.openLoadAnimation();
        bind.recyclerListOrder.recyclerList.setLayoutManager(linearLayoutManager);
        bind.recyclerListOrder.recyclerList.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeDataBean.ProjectListBean bindingAdapterItem1 = mainList.get(position);
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PROJECT_ID", String.valueOf(bindingAdapterItem1.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void initProject(List<HomeDataBean.ProjectListBean> projectList) {
        if (isRefresh){
            mainList.clear();
            isRefresh=false;
        }
        mainList.addAll(projectList);
        projectAdapter.notifyDataSetChanged();

//        bind.recyclerListOrder.smartRefresh.setEnableAutoLoadMore(false);
//        bind.recyclerListOrder.smartRefresh.setEnableRefresh(false);
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
