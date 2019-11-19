package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.Center2Adapter;
import com.zysoft.tjawshapingapp.adapter.Center3Adapter;
import com.zysoft.tjawshapingapp.adapter.CenterAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.SPUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentCenterBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.order.OrderActivity;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class UserCenterFragment extends CustomBaseFragment {
    private List<CenterToolBean> mainList = new ArrayList<>();
    private List<CenterToolBean> mainList2 = new ArrayList<>();
    private List<CenterToolBean> mainList3 = new ArrayList<>();
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
        CenterToolBean centerToolBean1 = new CenterToolBean(CenterToolBean.center_1, "消息", R.mipmap.ic_xx, 1);
        CenterToolBean centerToolBean2 = new CenterToolBean(CenterToolBean.center_1, "订单", R.mipmap.ic_dd, 2);
        CenterToolBean centerToolBean3 = new CenterToolBean(CenterToolBean.center_1, "钱包", R.mipmap.ic_qb, 3);
        CenterToolBean centerToolBean4 = new CenterToolBean(CenterToolBean.center_1, "代理", R.mipmap.ic_dl, 4);
        mainList.add(centerToolBean1);
        mainList.add(centerToolBean2);
        mainList.add(centerToolBean3);
        mainList.add(centerToolBean4);

        CenterAdapter centerAdapter = new CenterAdapter(mainList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        binding.recyclerListCenter1.setLayoutManager(gridLayoutManager);
        binding.recyclerListCenter1.setAdapter(centerAdapter);

        centerAdapter.setOnItemClickListener((adapter, view13, position) -> {
            CenterToolBean centerToolBean = mainList.get(position);

            switch (centerToolBean.getId()){
                case 1:
                    Intent intent = new Intent(getActivity(), NoticeActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    Intent intent1 = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent1);
                    break;
                case 3:
                    Intent intent2 = new Intent(getActivity(), UserWalletActivity.class);
                    startActivity(intent2);
                    break;
            }
        });


        mainList2.clear();
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "已预约", R.mipmap.ic_yyy, 5));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待付款", R.mipmap.ic_dfk, 6));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待收货", R.mipmap.ic_dsh, 7));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待评价", R.mipmap.ic_dpj, 8));
        Center2Adapter centerAdapter2 = new Center2Adapter(mainList2);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),4);
        binding.recyclerListCenterOrder.setLayoutManager(gridLayoutManager2);
        binding.recyclerListCenterOrder.setAdapter(centerAdapter2);

        centerAdapter2.setOnItemClickListener((adapter, view12, position) -> {
            CenterToolBean centerToolBean = mainList2.get(position);
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            bundle.clear();
            bundle.putString("TABID", String.valueOf((centerToolBean.getId())));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        mainList3.clear();
        CenterToolBean centerToolBean9 = new CenterToolBean(CenterToolBean.center_1, "我的钱包", R.mipmap.ic_wdqb, 9);
        CenterToolBean centerToolBean10 = new CenterToolBean(CenterToolBean.center_1, "实名认证", R.mipmap.ic_smrz, 10);
        CenterToolBean centerToolBean11 = new CenterToolBean(CenterToolBean.center_1, "收货地址", R.mipmap.ic_shdz, 11);
        CenterToolBean centerToolBean12 = new CenterToolBean(CenterToolBean.center_1, "我是代理", R.mipmap.ic_wsdl, 12);
        CenterToolBean centerToolBean13 = new CenterToolBean(CenterToolBean.center_1, "优惠券", R.mipmap.ic_yhq, 13);
        CenterToolBean centerToolBean14 = new CenterToolBean(CenterToolBean.center_1, "客服中心", R.mipmap.ic_kfzx, 14);
        CenterToolBean centerToolBean15 = new CenterToolBean(CenterToolBean.center_1, "关于爱薇", R.mipmap.ic_gyaw, 15);
        CenterToolBean centerToolBean16 = new CenterToolBean(CenterToolBean.center_1, "设置", R.mipmap.ic_sz, 16);
        mainList3.add(centerToolBean9);
        mainList3.add(centerToolBean10);
        mainList3.add(centerToolBean11);
        mainList3.add(centerToolBean12);
        mainList3.add(centerToolBean13);
        mainList3.add(centerToolBean14);
        mainList3.add(centerToolBean15);
        mainList3.add(centerToolBean16);
        Center3Adapter centerAdapter3 = new Center3Adapter(mainList3);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(),4);

        binding.recyclerListCenterTool.setLayoutManager(gridLayoutManager3);
        binding.recyclerListCenterTool.setAdapter(centerAdapter3);
        centerAdapter3.setOnItemClickListener((adapter, view1, position) -> {
            CenterToolBean centerToolBean = mainList3.get(position);
            switch (centerToolBean.getId()){
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
                case 14:
                    Conversation conversation =  Conversation.createSingleConversation("miao2012crazy@163.com");
                    conversation.resetUnreadCount();
                    Intent intent6 = new Intent(getActivity(), IMDetailActivity.class);
                    bundle.clear();
                    UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
                    bundle.putString("recvUserName", targetInfo.getUserName());
                    bundle.putString("recvNickName", targetInfo.getNickname());
                    bundle.putString("recvUserAppkey", targetInfo.getAppKey());
                    intent6.putExtras(bundle);
                    startActivity(intent6);

                    break;
                case 12:

                    break;
                case 15:
                    Intent intent15 = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(intent15);
                    break;
            }
        });
        binding.rlApplyDl.setOnClickListener(v -> {
            Intent intent1 = new Intent(getActivity(), WebViewActivity.class);
            bundle.putString("title", "代理");
            bundle.putString("url", "http://www.jd.com");
            intent1.putExtras(bundle);
            startActivity(intent1);
        });
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
        binding.ivDesc.setText(userInfoBean.getUserSign());
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
