package com.zysoft.tjawshapingapp.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;


public abstract class VideoBaseFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();
    protected Context context;
    private View rootView;
    protected CustomHandlerEvent handlerEvent =new CustomHandlerEvent(UIUtils.getContext());


    protected abstract int getLayoutId();

    protected abstract void initView(View rootView);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    public boolean isUIVisible;
    protected boolean isLoadCompleted;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser && !isLoadCompleted) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && !isLoadCompleted) {
            // 此处不需要判断isViewCreated，因为这个方法在onCreateView方法之后执行
            lazyLoad();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        isViewCreated = true;
        initView(rootView);
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isUIVisible = !hidden;
    }

    protected void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
//            isViewCreated = false;
//            isUIVisible = false;
            isLoadCompleted = true;
        }
    }


    protected abstract void loadData();

    public boolean onBackPressed() {
        assert getFragmentManager() != null;
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
