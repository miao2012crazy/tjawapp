package com.zysoft.tjawshapingapp.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.view.video.VideoFragment;

import java.util.List;

public class VerticalViewPagerAdapter extends PagerAdapter {
    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    private List<ProjectVideoBean> urlList;

    public void setUrlList(List<ProjectVideoBean> urlList) {
        this.urlList = urlList;
    }


    public VerticalViewPagerAdapter(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }

        VideoFragment fragment = new VideoFragment();
        if (urlList != null && urlList.size() > 0) {
            Bundle bundle = new Bundle();
            if (position >= urlList.size()) {
                bundle.putSerializable(VideoFragment.URL, urlList.get(position % urlList.size()));
            } else {
                bundle.putSerializable(VideoFragment.URL, urlList.get(position));
            }
            fragment.setArguments(bundle);
        }


        mCurTransaction.add(container.getId(), fragment,
                makeFragmentName(container.getId(), position));
        fragment.setUserVisibleHint(false);

        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment) object);
        mCurTransaction.remove((Fragment) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    private String makeFragmentName(int viewId, int position) {
        return "android:switcher:" + viewId + position;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }
}