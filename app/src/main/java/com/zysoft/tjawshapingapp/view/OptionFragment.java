package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.FragmentOrderBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.order.OrderOneFragment;

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

    private List<BindingAdapterItem> mainList=new ArrayList<>();

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
    }


    private void initProject(List<HomeDataBean.ProjectListBean> projectList) {
        mainList.clear();
        mainList.addAll(projectList);
        setList_V(bind.recyclerListOrder.recyclerList, mainList,handlerEvent, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                HomeDataBean.ProjectListBean bindingAdapterItem1 = (HomeDataBean.ProjectListBean) bindingAdapterItem;
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PROJECT_ID", String.valueOf(bindingAdapterItem1.getId()));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        bind.recyclerListOrder.smartRefresh.setEnableAutoLoadMore(false);
        bind.recyclerListOrder.smartRefresh.setEnableRefresh(false);
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
