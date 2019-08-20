package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.NoticeOneAdapter;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.DataBean;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.databinding.FragmentOneBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mr.miao on 2019/5/21.
 */

public class OneFragment extends BaseLazyFragment {

    private FragmentOneBinding bind;
    private int pageIndex=0;
    private List<DataBean> mainList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false);
        bind= (FragmentOneBinding) binding;
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<DataBean> list = CustomApplaction.getSession().queryBuilder(DataBean.class).offset(pageIndex * 10).limit(10).list();
        mainList.clear();
        mainList.addAll(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        NoticeOneAdapter noticeOneAdapter = new NoticeOneAdapter(mainList);
        noticeOneAdapter.openLoadAnimation();
        bind.recyclerListGg.recyclerList.setLayoutManager(linearLayoutManager);
        bind.recyclerListGg.recyclerList.setAdapter(noticeOneAdapter);
        noticeOneAdapter.setOnItemClickListener((adapter, view1, position) -> {

        });
    }

}
