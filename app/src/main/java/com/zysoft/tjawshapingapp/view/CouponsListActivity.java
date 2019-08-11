package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomPagerAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.databinding.ActivityCouponsListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/26.
 */

public class CouponsListActivity extends CustomBaseActivity {

    private ActivityCouponsListBinding binding;
    List<BaseLazyFragment> fragmentList = new ArrayList<>();
    List<String> list_Title = new ArrayList<>();
    private CustomPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_coupons_list);
        binding = (ActivityCouponsListBinding) viewDataBinding;
        initTab();
        binding.title.qmTopBar.setTitle("我的优惠券");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
//        initTitle(binding.title.tvReturn, null);

    }

    private void initTab() {
        fragmentList.clear();
        list_Title.clear();
        CouponsListFragment couponsListFragment0 = new CouponsListFragment();
        CouponsListFragment couponsListFragment1 = new CouponsListFragment();
        CouponsListFragment couponsListFragment2 = new CouponsListFragment();
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        setArgments(couponsListFragment0, bundle, "0");
        fragmentList.add(couponsListFragment0);
        setArgments(couponsListFragment1, bundle1, "1");
        fragmentList.add(couponsListFragment1);
        setArgments(couponsListFragment2, bundle2, "2");
        fragmentList.add(couponsListFragment2);

        list_Title.add("可使用");
        list_Title.add("已使用");
        list_Title.add("已过期");

        adapter = new CustomPagerAdapter(getSupportFragmentManager(), this, fragmentList, list_Title);
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }


    private void setArgments(CouponsListFragment couponsListFragment, Bundle bundle, String type) {
        bundle.putString("type", type);
        couponsListFragment.setArguments(bundle);

    }

}
