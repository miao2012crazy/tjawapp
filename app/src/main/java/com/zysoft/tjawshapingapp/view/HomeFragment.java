package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomLazyPagerAdapter;
import com.zysoft.tjawshapingapp.adapter.OptionTabAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentHomeBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.search.PlatformSearchActivity;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

/**
 * Created by mr.miao on 2019/3/31.
 */

public class HomeFragment extends CustomBaseFragment {
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    //    protected List<BindingAdapterItem> mainList2 = new ArrayList<>();
    private NetModel netModel;
    private List<String> info = new ArrayList<>();

    private List<HomeDataBean.OptionBean> optionList = new ArrayList<>();

    List<BaseLazyFragment> fragmentList = new ArrayList<>();
    List<String> list_Title = new ArrayList<>();
    private FragmentHomeBinding bind;
    private HomeDataBean homeDataBean;
    private OptionTabAdapter optionTabAdapter;
    private CustomLazyPagerAdapter adapter;
    private boolean isInit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        bind = (FragmentHomeBinding) binding;
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initOptionTab();

        bind.ivRecomment1.setOnClickListener(v -> {
            if (homeDataBean != null) {
                List<HomeDataBean.RecommendBean> recommend = homeDataBean.getRecommend();
                if (recommend.size() != 0) {
                    Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PROJECT_ID", String.valueOf(recommend.get(0).getId()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bind.ivRecomment2.setOnClickListener(v -> {
            if (homeDataBean != null) {
                List<HomeDataBean.RecommendBean> recommend = homeDataBean.getRecommend();
                if (recommend.size() != 0) {
                    Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PROJECT_ID", String.valueOf(recommend.get(1).getId()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        NetModel.getInstance().getAllData("HOME_DATA", HttpUrls.GET_HOME_DATA, map);

        bind.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                EventBus.getDefault().post(new NetResponse("REFRESH", ""));
                NetModel.getInstance().getAllData("HOME_DATA", HttpUrls.GET_HOME_DATA, map);
            }
        });
        bind.smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                EventBus.getDefault().post(new NetResponse("LOAD_MORE", ""));

            }
        });

        bind.search.setOnClickListener(v -> startActivityCom(PlatformSearchActivity.class));
        bind.rlMsg.setOnClickListener(v -> startActivityCom(NoticeActivity.class));
    }

    private void initOptionTab() {
        optionTabAdapter = new OptionTabAdapter(optionList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UIUtils.getContext(), 5);
        bind.recyclerTabs.setLayoutManager(gridLayoutManager);
        bind.recyclerTabs.setAdapter(optionTabAdapter);
        optionTabAdapter.openLoadAnimation();
        optionTabAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (optionList.get(position).getId() == -1) {
                bundle.putString("type", "1");
                bundle.putString("title", "全部分类");
                startActivityBase(ShopoptionActivity.class, bundle);
                return;
            }
            Intent intent = new Intent(getActivity(), OptionActvity.class);
            bundle.clear();
            bundle.putSerializable("OPTION_ID", optionList.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "HOME_DATA":
                String data = (String) netResponse.getData();
                homeDataBean = GsonUtil.GsonToBean(data, HomeDataBean.class);
                initLoop(homeDataBean.getLoop());
                initRecyclerTabs(homeDataBean.getOption());
                initRecommendImage(homeDataBean.getRecommend(), homeDataBean.getAppDL());
//                initProject(homeDataBean.getProjectList());
                if (bind.smartRefresh.isRefreshing()) {
                    LogUtils.e("正在刷新");
                    bind.smartRefresh.finishRefresh();
                }
                initTablayout(homeDataBean.getOption());

                break;
            case "check":
//                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
            case "NO_MORE_DATA":
                bind.smartRefresh.finishLoadMoreWithNoMoreData();
                break;
            case "LOAD_MORE_SUCCESS":
                bind.smartRefresh.finishLoadMore();
                break;
        }
    }

    private void initTablayout(List<HomeDataBean.OptionBean> option) {
        if (isInit) {
            return;
        }
        LogUtils.e("取得数据" + option);
        bind.tablayout.removeAllTabs();
        fragmentList.clear();
        list_Title.clear();
        OptionFragment optionFragment1 = new OptionFragment();
        Bundle bundle1 = new Bundle();
        setArgments(optionFragment1, bundle1, "-1");
        fragmentList.add(optionFragment1);
        list_Title.add("推荐");

        for (HomeDataBean.OptionBean item : option) {
            bind.tablayout.addTab(bind.tablayout.newTab().setText(item.getOptionName()));
            OptionFragment optionFragment = new OptionFragment();
            Bundle bundle = new Bundle();
            setArgments(optionFragment, bundle, String.valueOf(item.getId()));
            fragmentList.add(optionFragment);
            list_Title.add(item.getOptionName());
        }
        if (adapter == null) {
            adapter = new CustomLazyPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList, list_Title);
        }
        bind.viewpager.setOffscreenPageLimit(option.size());
        bind.viewpager.setAdapter(adapter);
        bind.tablayout.setupWithViewPager(bind.viewpager);
        bind.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bind.smartRefresh.setNoMoreData(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        isInit = true;
    }

    private void setArgments(OptionFragment optionFragment, Bundle bundle, String type) {
        bundle.putString("type", type);
        optionFragment.setArguments(bundle);

    }

    private void initRecommendImage(List<HomeDataBean.RecommendBean> recommend, HomeDataBean.AppDLBean appDL) {
        if (recommend.size() == 1) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).centerCrop().transform(new GlideRoundTransform(6)).into(bind.ivRecomment1);
        } else if (recommend.size() >= 2) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).transform(new GlideRoundTransform(6)).into(bind.ivRecomment1);
            GlideApp.with(this).load(recommend.get(1).getProductIcon()).transform(new GlideRoundTransform(6)).into(bind.ivRecomment2);
        }
        GlideApp.with(this).load(appDL.getProjectImg()).transform(new GlideRoundTransform(4)).into(bind.ivRecomment3);
    }

    private void initRecyclerTabs(List<HomeDataBean.OptionBean> option) {
            optionList.clear();
            HomeDataBean.OptionBean optionBean = new HomeDataBean.OptionBean();
            optionBean.setIsProject(1);
            optionBean.setId(-1);
            optionBean.setOptionImg(String.valueOf(R.drawable.icon_more));
            optionBean.setOptionName("全部");
            optionBean.setRegDate("");
            optionBean.setStateUsable(0);
            optionList.addAll(option);
            optionList.add(optionBean);
            optionTabAdapter.notifyDataSetChanged();

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
        if (bind.banner == null) {
            return;
        }
        titles.add("");
        bind.banner.removeAllViews();
        bind.banner.setData(images, titles);
        bind.banner.setOnItemClickListener((banner, position) -> {
            HomeDataBean.LoopBean loopBean = loop.get(position);
            switch (loopBean.getIsProduct()) {
                case 0:
                    if (TextUtils.isEmpty(loopBean.getLoopLink())){
                        break;
                    }

                    //0活动
                    bundle.clear();
                    bundle.putString("title", "官方活动");
                    bundle.putString("url", loopBean.getLoopLink());
                    startActivityBase(WebViewActivity.class, bundle);
                    break;
                case 1:
                    //1 商品
                    if (TextUtils.isEmpty(loopBean.getProductId())||loopBean.getProductId().equals("0")){
                        break;
                    }
                    bundle.clear();
                    bundle.putString("PRODUCT_ID", loopBean.getProductId());
                    startActivityBase(ProductDetailActivity.class, bundle);
                    break;
                case 2:
                    if (TextUtils.isEmpty(loopBean.getProductId())||loopBean.getProductId().equals("0")){
                        break;
                    }
                    // 2 项目
                    bundle.clear();
                    bundle.putString("PROJECT_ID", loopBean.getProductId());
                    startActivityBase(ProjectDetailActivity.class, bundle);

                    break;
            }
        });

        // XBanner适配数据
        bind.banner.setmAdapter((banner, view, position) -> GlideApp.with(HomeFragment.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
