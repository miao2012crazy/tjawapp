package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.DataBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.FragmentOneBinding;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


/**
 * Created by mr.miao on 2019/5/21.
 */

public class OneFragment extends BaseLazyFragment {

    private FragmentOneBinding bind;
    private int pageIndex=0;
    private List<BindingAdapterItem> mainList=new ArrayList<>();
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
        mainList.addAll(list);
        setList_V(bind.recyclerListGg.recyclerList, mainList, handlerEvent, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });
    }

}
