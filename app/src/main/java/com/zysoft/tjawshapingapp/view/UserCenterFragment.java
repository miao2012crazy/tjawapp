package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;
import com.zysoft.tjawshapingapp.databinding.FragmentCenterBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class UserCenterFragment extends CustomBaseFragment {
    private List<BindingAdapterItem> mainList = new ArrayList<>();
    private List<BindingAdapterItem> mainList2 = new ArrayList<>();
    private List<BindingAdapterItem> mainList3 = new ArrayList<>();
    private FragmentCenterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_center, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStatusBar("#00000000");
        mainList.clear();
        mainList2.clear();
        mainList3.clear();
        CenterToolBean centerToolBean1 = new CenterToolBean(1, "消息", R.mipmap.ic_xx, 1);
        CenterToolBean centerToolBean2 = new CenterToolBean(1, "订单", R.mipmap.ic_dd, 2);
        CenterToolBean centerToolBean3 = new CenterToolBean(1, "钱包", R.mipmap.ic_qb, 3);
        CenterToolBean centerToolBean4 = new CenterToolBean(1, "代理", R.mipmap.ic_dl, 4);
        mainList.add(centerToolBean1);
        mainList.add(centerToolBean2);
        mainList.add(centerToolBean3);
        mainList.add(centerToolBean4);
        setList_H(binding.recyclerListCenter1, mainList, 4, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });

        mainList2.add(new CenterToolBean(2, "已预约", R.mipmap.ic_yyy, 5));
        mainList2.add(new CenterToolBean(2, "待付款", R.mipmap.ic_dfk, 6));
        mainList2.add(new CenterToolBean(2, "待收货", R.mipmap.ic_dsh, 7));
        mainList2.add(new CenterToolBean(2, "待评价", R.mipmap.ic_dpj, 8));
        setList_H(binding.recyclerListCenterOrder, mainList2, 4, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });
        CenterToolBean centerToolBean9 = new CenterToolBean(2, "我的钱包", R.mipmap.ic_wdqb, 9);
        CenterToolBean centerToolBean10 = new CenterToolBean(2, "实名认证", R.mipmap.ic_smrz, 10);
        CenterToolBean centerToolBean11 = new CenterToolBean(2, "收货地址", R.mipmap.ic_shdz, 11);
        CenterToolBean centerToolBean12 = new CenterToolBean(2, "我是代理", R.mipmap.ic_wsdl, 12);
        CenterToolBean centerToolBean13 = new CenterToolBean(2, "优惠券", R.mipmap.ic_yhq, 13);
        CenterToolBean centerToolBean14 = new CenterToolBean(2, "客服中心", R.mipmap.ic_kfzx, 14);
        CenterToolBean centerToolBean15 = new CenterToolBean(2, "关于爱薇", R.mipmap.ic_gyaw, 15);
        CenterToolBean centerToolBean16 = new CenterToolBean(2, "设置", R.mipmap.ic_sz, 16);
        mainList3.add(centerToolBean9);
        mainList3.add(centerToolBean10);
        mainList3.add(centerToolBean11);
        mainList3.add(centerToolBean12);
        mainList3.add(centerToolBean13);
        mainList3.add(centerToolBean14);
        mainList3.add(centerToolBean15);
        mainList3.add(centerToolBean16);
        setList_H(binding.recyclerListCenterTool, mainList3, 4, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });
//        initRecyclerView1(binding.recyclerListCenter1);
//        initRecyclerView2(binding.recyclerListCenterTool);
//        initRecyclerView1(binding.recyclerProject);


    }

}
