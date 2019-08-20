package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.stx.xhb.xbanner.XBanner;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomLazyPagerAdapter;
import com.zysoft.tjawshapingapp.adapter.OptionTabAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GlideUtil;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
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
    private ViewDataBinding binding;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        bind = (FragmentHomeBinding) binding;
        QMUIStatusBarHelper.translucent(getActivity());
        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
//        QMUIStatusBarHelper.translucent(getActivity());
//        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
        initOptionTab();

        bind.ivRecomment1.setOnClickListener(v -> {
            if (homeDataBean!=null){
                List<HomeDataBean.RecommendBean> recommend = homeDataBean.getRecommend();
                if (recommend.size()!=0){
                    Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PROJECT_ID", String.valueOf(recommend.get(0).getId()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bind.ivRecomment2.setOnClickListener(v -> {
            if (homeDataBean!=null){
                List<HomeDataBean.RecommendBean> recommend = homeDataBean.getRecommend();
                if (recommend.size()!=0){
                    Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PROJECT_ID", String.valueOf(recommend.get(1).getId()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        NetModel.getInstance().getAllData("HOME_DATA", HttpUrls.GET_HOME_DATA, map);

    }

    private void initOptionTab() {
        optionTabAdapter = new OptionTabAdapter(optionList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UIUtils.getContext(), 5);
        bind.recyclerTabs.setLayoutManager(gridLayoutManager);
        bind.recyclerTabs.setAdapter(optionTabAdapter);
        optionTabAdapter.openLoadAnimation();
        optionTabAdapter.setOnItemClickListener((adapter, view, position) -> {
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
                initTablayout(homeDataBean.getOption());
                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }

    private void initTablayout(List<HomeDataBean.OptionBean> option) {
        bind.tablayout.removeAllTabs();
        for (HomeDataBean.OptionBean item : option) {
            bind.tablayout.addTab(bind.tablayout.newTab().setText(item.getOptionName()));
            OptionFragment optionFragment = new OptionFragment();
            Bundle bundle = new Bundle();
            setArgments(optionFragment, bundle, String.valueOf(item.getId()));
            fragmentList.add(optionFragment);
            list_Title.add(item.getOptionName());
        }

        bind.viewpager.setAdapter(new CustomLazyPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList, list_Title));
        bind.tablayout.setupWithViewPager(bind.viewpager);
        bind.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void setArgments(OptionFragment optionFragment, Bundle bundle, String type) {
        bundle.putString("type",type);
        optionFragment.setArguments(bundle);

    }

    private void initRecommendImage(List<HomeDataBean.RecommendBean> recommend, HomeDataBean.AppDLBean appDL) {
        if (recommend.size() == 1) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).centerCrop().transform(new GlideRoundTransform( 4)).into(bind.ivRecomment1);
        } else if (recommend.size() >= 2) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).transform(new GlideRoundTransform( 4)).into(bind.ivRecomment1);
            GlideApp.with(this).load(recommend.get(1).getProductIcon()).into(bind.ivRecomment2);
        }
        GlideApp.with(this).load(appDL.getProjectImg()).transform(new GlideRoundTransform( 4)).into(bind.ivRecomment3);






    }

    private void initRecyclerTabs(List<HomeDataBean.OptionBean> option) {
        optionList.clear();
        optionList.addAll(option);
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
        // XBanner适配数据
        bind.banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                GlideApp.with(HomeFragment.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view);
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

    }
}
