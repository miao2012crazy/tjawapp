package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GlideApp;
import com.zysoft.baseapp.commonUtil.GlideCircleTransform;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.FragmentCenterBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.order.OrderActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

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
        EventBus.getDefault().register(this);
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
        setList_H(binding.recyclerListCenter1, mainList, 4,handlerEvent, bindingAdapterItem -> {
            CenterToolBean bindingAdapterItem1 = (CenterToolBean) bindingAdapterItem;
            switch (bindingAdapterItem1.getId()) {
                case 1:
                    startActivity(new Intent(getActivity(), NoticeActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                    break;
            }
        });

        mainList2.add(new CenterToolBean(2, "已预约", R.mipmap.ic_yyy, 5));
        mainList2.add(new CenterToolBean(2, "待付款", R.mipmap.ic_dfk, 6));
        mainList2.add(new CenterToolBean(2, "待收货", R.mipmap.ic_dsh, 7));
        mainList2.add(new CenterToolBean(2, "待评价", R.mipmap.ic_dpj, 8));
        setList_H(binding.recyclerListCenterOrder, mainList2, 4, handlerEvent, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                bundle.clear();
                bundle.putString("TABID", String.valueOf(((CenterToolBean) bindingAdapterItem).getId()));
                intent.putExtras(bundle);
                startActivity(intent);
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
        setList_H(binding.recyclerListCenterTool, mainList3, 4, handlerEvent, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                CenterToolBean bindingAdapterItem1 = (CenterToolBean) bindingAdapterItem;
                switch (bindingAdapterItem1.getId()) {
                    case 13:
                        Intent intent = new Intent(getActivity(), CouponsListActivity.class);
                        startActivity(intent);
                        break;
                    case 16:
                        Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent2);

                        break;
                    case 11:
                        Intent intent3 = new Intent(getActivity(), AddressManageActivity.class);
                        startActivity(intent3);
                        break;
                    case 10 :
                        Intent intent4 = new Intent(getActivity(), RealStateActivity.class);
                        startActivity(intent4);


                        break;
                    case 9:
                        Intent intent5 = new Intent(getActivity(), UserWalletActivity.class);
                        startActivity(intent5);
                        break;


                }
            }
        });
//        initRecyclerView1(binding.recyclerListCenter1);
//        initRecyclerView2(binding.recyclerListCenterTool);
//        initRecyclerView1(binding.recyclerProject);

    }

    private void initUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            binding.tvNickName.setText(userInfoBean.getUserNickName());
            GlideApp.with(getActivity()).load(userInfoBean.getUserHeadImg()).centerCrop().transform(new GlideCircleTransform()).into(binding.ivHead);
        } else {
            binding.tvLogin.setVisibility(View.VISIBLE);
            binding.llUser.setVisibility(View.GONE);
        }
        binding.tvLogin.setOnClickListener(view -> startActivity(new Intent(getActivity(), LoginActivity.class)));

    }

    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "USER_INFO":
                String data = (String) netResponse.getData();
                UserInfoBean userInfoBean = GsonUtil.GsonToBean(data, UserInfoBean.class);
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data);
                AppConstant.USER_INFO_BEAN = userInfoBean;
                initUserInfo(userInfoBean);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS", ""));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setStatusBar("#00000000");
        if (AppConstant.USER_INFO_BEAN != null) {
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            NetModel.getInstance().getDataFromNet("USER_INFO", HttpUrls.USER, map);
        } else {
            initUserInfo(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
