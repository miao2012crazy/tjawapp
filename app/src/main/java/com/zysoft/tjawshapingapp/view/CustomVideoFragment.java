package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.VerticalViewPagerAdapter;
import com.zysoft.tjawshapingapp.base.BaseRecAdapter;
import com.zysoft.tjawshapingapp.base.BaseRecViewHolder;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.databinding.FragmentVideoBinding;
import com.zysoft.tjawshapingapp.ui.widget.MyVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 *
 * Created by mr.miao on 2019/5/20.
 */
public class CustomVideoFragment extends CustomBaseFragment {

    public static final String URL = "URL";
    private FragmentVideoBinding bind;
    private List<String> urlList = new ArrayList<>();
    private VerticalViewPagerAdapter pagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStatusBar("#00000000");
        initView();
        addListener();

    }

    private void addListener() {
//        bind.srlPage.setEnableAutoLoadMore(false);
//        bind.srlPage.setEnableLoadMore(false);
//        bind.srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                bind.srlPage.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        urlList.addAll(urlList);
//                        pagerAdapter.setUrlList(urlList);
//                        pagerAdapter.notifyDataSetChanged();
//
//                        bind.srlPage.finishLoadMore();
//                    }
//                }, 2000);
//
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//
//            }
//        });
    }

    private void initView() {
        makeData();
        pagerAdapter = new VerticalViewPagerAdapter(getActivity().getSupportFragmentManager());
        bind.vvpBackPlay.setVertical(true);
        bind.vvpBackPlay.setOffscreenPageLimit(10);
        pagerAdapter.setUrlList(urlList);
        bind.vvpBackPlay.setAdapter(pagerAdapter);
        bind.vvpBackPlay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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


    }

    private void makeData() {
        urlList.clear();
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201805/100651/201805181532123423.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803151735198462.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150923220770.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150922255785.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803150920130302.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803141625005241.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803141624378522.mp4");
        urlList.add("http://chuangfen.oss-cn-hangzhou.aliyuncs.com/public/attachment/201803/100651/201803131546119319.mp4");
    }
}
