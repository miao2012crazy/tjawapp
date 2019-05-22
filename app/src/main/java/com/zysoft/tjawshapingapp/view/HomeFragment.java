package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.zysoft.baseapp.base.BaseFragment;
import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GlideRoundTransform;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.databinding.FragmentHomeBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/3/31.
 */

public class HomeFragment extends CustomBaseFragment {
    private FragmentHomeBinding bind;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    protected List<BindingAdapterItem> mainList2 = new ArrayList<>();
    private NetModel netModel;
    private List<String> info = new ArrayList<>();

    private List<BindingAdapterItem> optionList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);

        NetModel.getInstance().getAllData("HOME_DATA", HttpUrls.GET_HOME_DATA,map);


    }



    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "HOME_DATA":
                String data = (String) netResponse.getData();
                HomeDataBean homeDataBean = GsonUtil.GsonToBean(data, HomeDataBean.class);
                initLoop(homeDataBean.getLoop());
                initRecyclerTabs(homeDataBean.getOption());
                initRecommendImage(homeDataBean.getRecommend(),homeDataBean.getAppDL());
                initProject(homeDataBean.getProjectList());
                initTablayout(homeDataBean.getOption());
                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }

    private void initTablayout(List<HomeDataBean.OptionBean> option) {


    }


    private void initProject(List<HomeDataBean.ProjectListBean> projectList) {
        mainList2.clear();
        mainList2.addAll(projectList);
        setList_V(bind.recyclerList2.recyclerList, mainList2, new BindingAdapter.CustomOnClickListener() {
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
        bind.recyclerList2.smartRefresh.setEnableAutoLoadMore(false);
        bind.recyclerList2.smartRefresh.setEnableRefresh(false);
    }

    private void initRecommendImage(List<HomeDataBean.RecommendBean> recommend, HomeDataBean.AppDLBean appDL) {
        if (recommend.size()==1){
            Glide.with(this).load(recommend.get(0).getProductIcon()).centerCrop().transform(new GlideRoundTransform(UIUtils.getContext(),4)).into(bind.ivRecomment1);
        }else if (recommend.size()>=2){
            Glide.with(this).load(recommend.get(0).getProductIcon()).transform(new GlideRoundTransform(UIUtils.getContext(),4)).into(bind.ivRecomment1);
            Glide.with(this).load(recommend.get(1).getProductIcon()).into(bind.ivRecomment2);
        }
        Glide.with(this).load(appDL.getProjectImg()).transform(new GlideRoundTransform(UIUtils.getContext(),4)).into(bind.ivRecomment3);
    }

    private void initRecyclerTabs(List<HomeDataBean.OptionBean> option) {
        optionList.clear();
        optionList.addAll(option);
        setList_H(bind.recyclerTabs, optionList, 5, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });
    }


//
//
    /**
     * List<HomeDataBean.LoopBean> loopBeans
     *
     * @param loop
     */
    private void initLoop(List<HomeDataBean.LoopBean> loop) {

        images.clear();
        titles.clear();
        for (HomeDataBean.LoopBean loopbean : loop) {
            images.add(loopbean.getLoopImgPath());
            titles.add("1");
        }
        if ( bind.banner == null) {
            return;
        }
        titles.add("");
        bind.banner.removeAllViews();
        bind.banner.setData(images, titles);
        // XBanner适配数据
        bind.banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(HomeFragment.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        CustomApplaction.getmLocationClient().startLocation();
    }
}
