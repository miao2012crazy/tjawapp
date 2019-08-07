package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.VerticalViewPagerAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentVideoBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.ViewPagerScroller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;


/**
 * Created by mr.miao on 2019/5/20.
 */
public class CustomVideoFragment extends BaseLazyFragment {

    public static final String URL = "URL";
    private FragmentVideoBinding bind;
    private List<String> urlList = new ArrayList<>();
    private VerticalViewPagerAdapter pagerAdapter;
    private List<ProjectVideoBean> projectVideoBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        NetModel.getInstance().getDataFromNet("GET_VIDEO", HttpUrls.GET_VIDEO, map);
        setAndroidNativeLightStatusBar(getActivity(), false);
//        initView();

    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();

    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        NetModel.getInstance().getDataFromNet("GET_VIDEO", HttpUrls.GET_VIDEO, map);

    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_VIDEO":
                String data = (String) netResponse.getData();
                projectVideoBeans = GsonUtil.GsonToList(data, ProjectVideoBean.class);
                initView();
//                pagerAdapter.setUrlList(projectVideoBeans);
//                pagerAdapter.notifyDataSetChanged();
                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }


    private void initView() {
        pagerAdapter = new VerticalViewPagerAdapter(getChildFragmentManager());
        bind.vvpBackPlay.setVertical(true);
        bind.vvpBackPlay.setOffscreenPageLimit(10);
        pagerAdapter.setUrlList(projectVideoBeans);
        bind.vvpBackPlay.setAdapter(pagerAdapter);
        bind.vvpBackPlay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.e("滑动" + positionOffset);
                if (positionOffset > 0.15) {
                    bind.vvpBackPlay.setCurrentItem(position + 1, true);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                if (position == urlList.size() - 1) {
//                    bind.srlPage.setEnableAutoLoadMore(true);
//                    bind.srlPage.setEnableLoadMore(true);
//                } else {
//                    bind.srlPage.setEnableAutoLoadMore(false);
//                    bind.srlPage.setEnableLoadMore(false);
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.initViewPagerScroll(bind.vvpBackPlay);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }
}
