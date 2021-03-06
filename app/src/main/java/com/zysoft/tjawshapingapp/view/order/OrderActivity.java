package com.zysoft.tjawshapingapp.view.order;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomLazyPagerAdapter;
import com.zysoft.tjawshapingapp.adapter.CustomPagerAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityOrderBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/23.
 */

public class OrderActivity extends CustomBaseActivity {


    private ActivityOrderBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        binding.title.qmTopBar.setTitle("我的订单");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        initTab();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tabid = extras.getString("TABID");
            if (!TextUtils.isEmpty(tabid)) {
                binding.tablayout.getTabAt(Integer.parseInt(tabid) - 4).select();
            }

        }


    }


    private void initTab() {
        List<BaseLazyFragment> fragmentList = new ArrayList<>();
        List<String> list_Title = new ArrayList<>();
        OrderOneFragment orderOneFragment0 = new OrderOneFragment();
        OrderTwoFragment orderOneFragment1 = new OrderTwoFragment();
        OrderThreeFragment orderOneFragment2 = new OrderThreeFragment();
        OrderFourFragment orderOneFragment3 = new OrderFourFragment();
        OrderFiveFragment orderOneFragment4 = new OrderFiveFragment();
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        setArgments(orderOneFragment0, bundle, "-1");
        fragmentList.add(orderOneFragment0);
        setArgments(orderOneFragment1, bundle1, "1");
        fragmentList.add(orderOneFragment1);
        setArgments(orderOneFragment2, bundle2, "0");
        fragmentList.add(orderOneFragment2);
        setArgments(orderOneFragment3, bundle3, "2");
        fragmentList.add(orderOneFragment3);
        setArgments(orderOneFragment4, bundle4, "3");
        fragmentList.add(orderOneFragment4);
        list_Title.add("全部");
        list_Title.add("已预约");
        list_Title.add("待付款");
        list_Title.add("待收货");
        list_Title.add("待评价");
        binding.viewpager.setOffscreenPageLimit(5);
        binding.viewpager.setAdapter(new CustomLazyPagerAdapter(getSupportFragmentManager(), this, fragmentList, list_Title));
        binding.tablayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void setArgments(BaseLazyFragment orderOneFragment, Bundle bundle, String type) {
        bundle.putString("type", type);
        orderOneFragment.setArguments(bundle);

    }

}
