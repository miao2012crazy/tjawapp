package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.Center2Adapter;
import com.zysoft.tjawshapingapp.adapter.Center3Adapter;
import com.zysoft.tjawshapingapp.adapter.CenterAdapter;
import com.zysoft.tjawshapingapp.adapter.ProjectAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.LogUtils;
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
    private List<HomeDataBean.ProjectListBean> projectList = new ArrayList<>();
    private FragmentCenterBinding binding;
    private ProjectAdapter projectAdapter;
    private UserInfoBean userInfoBean;
    private QMUIDialog.MessageDialogBuilder messageDialogBuilder;

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
        initList();
        CenterToolBean centerToolBean1 = new CenterToolBean(CenterToolBean.center_2, "消息", R.mipmap.ic_xx, 1);
        CenterToolBean centerToolBean2 = new CenterToolBean(CenterToolBean.center_2, "订单", R.mipmap.ic_dd, 2);
        CenterToolBean centerToolBean3 = new CenterToolBean(CenterToolBean.center_2, "钱包", R.mipmap.ic_qb, 3);
        CenterToolBean centerToolBean4 = new CenterToolBean(CenterToolBean.center_2, "代理", R.mipmap.ic_dl, 4);
        mainList.add(centerToolBean1);
        mainList.add(centerToolBean2);
        mainList.add(centerToolBean3);
        mainList.add(centerToolBean4);

        CenterAdapter centerAdapter = new CenterAdapter(mainList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        binding.recyclerListCenter1.setLayoutManager(gridLayoutManager);
        binding.recyclerListCenter1.setAdapter(centerAdapter);

        centerAdapter.setOnItemClickListener((adapter, view13, position) -> {
            UserInfoBean userInfoBean = AppConstant.getUserInfoBean();
            if (userInfoBean == null) {
                return;
            }
            CenterToolBean centerToolBean = mainList.get(position);

            switch (centerToolBean.getId()) {
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
                case 4:
                    if (userInfoBean.getUserLevel() == 0) {
                        showAddDL();
                    } else {
                        startActivityCom(UserTeamBasicActivity.class);
                    }
                    break;
            }
        });


        mainList2.clear();
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "已预约", R.mipmap.ic_yyy, 5));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待付款", R.mipmap.ic_dfk, 6));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待收货", R.mipmap.ic_dsh, 7));
        mainList2.add(new CenterToolBean(CenterToolBean.center_1, "待评价", R.mipmap.ic_dpj, 8));
        Center2Adapter centerAdapter2 = new Center2Adapter(mainList2);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 4);
        binding.recyclerListCenterOrder.setLayoutManager(gridLayoutManager2);
        binding.recyclerListCenterOrder.setAdapter(centerAdapter2);

        centerAdapter2.setOnItemClickListener((adapter, view12, position) -> {
            UserInfoBean userInfoBean = AppConstant.getUserInfoBean();
            if (userInfoBean == null) {
                return;
            }
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
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 4);

        binding.recyclerListCenterTool.setLayoutManager(gridLayoutManager3);
        binding.recyclerListCenterTool.setAdapter(centerAdapter3);
        centerAdapter3.setOnItemClickListener((adapter, view1, position) -> {
            UserInfoBean userInfoBean = AppConstant.getUserInfoBean();
            if (userInfoBean == null) {
                return;
            }
            CenterToolBean centerToolBean = mainList3.get(position);
            switch (centerToolBean.getId()) {

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
                case 10:
                    Intent intent4 = new Intent(getActivity(), RealStateActivity.class);
                    startActivity(intent4);
                    break;
                case 9:
                    Intent intent5 = new Intent(getActivity(), UserWalletActivity.class);
                    startActivity(intent5);
                    break;
                case 14:
                    connetKF();

                    break;
                case 12:
                    if (userInfoBean.getUserLevel() == 0) {
                        showTipWhisBtn("提示", "加盟爱薇会员后即可开通!").show();
                    } else {
                        startActivityCom(UserTeamBasicActivity.class);
                    }
                    break;
                case 15:
                    Intent intent15 = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(intent15);
                    break;
            }
        });
//        binding.rlApplyDl.setOnClickListener(v -> {
//            Intent intent1 = new Intent(getActivity(), WebViewActivity.class);
//            bundle.putString("title", "代理");
//            bundle.putString("url", "http://www.jd.com");
//            intent1.putExtras(bundle);
//            startActivity(intent1);
//        });

        binding.icQrCode.setOnClickListener(v -> {
            startActivityCom(UserQrCodeActivity.class);
        });
        if (AppConstant.USER_INFO_BEAN != null) {
            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        }
        NetModel.getInstance().getDataFromNet("GET_RECOMMOND_PROJECT", HttpUrls.GET_RECOMMOND_PROJECT, map);
        binding.llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfoBean == null) {
                    startActivityCom(LoginActivity.class);
                } else {
                    startActivityCom(UserInfoActivity.class);
                }
            }
        });

        if (AppConstant.APP_CONFIG_BEAN != null) {
            GlideApp.with(this).load(AppConstant.APP_CONFIG_BEAN.getCenter_ad().getProjectImg()).transform(new GlideRoundTransform(4)).into(binding.ivGg);

        }
    }

    private void connetKF() {
        if (AppConstant.APP_CONFIG_BEAN == null) {
            return;
        }
        Conversation conversation = Conversation.createSingleConversation(AppConstant.APP_CONFIG_BEAN.getKf());
        Intent intent6 = new Intent(getActivity(), IMDetailActivity.class);
        bundle.clear();
        UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
        bundle.putString("recvUserName", targetInfo.getUserName());
        bundle.putString("recvNickName", targetInfo.getNickname());
        bundle.putString("recvUserAppkey", targetInfo.getAppKey());
        intent6.putExtras(bundle);
        startActivity(intent6);
    }

    private void initUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            binding.ivLevels.setVisibility(View.VISIBLE);
            binding.icQrCode.setVisibility(View.VISIBLE);
            binding.tvNickName.setText(userInfoBean.getUserNickName());
            GlideApp.with(binding.ivHead.getContext()).load(userInfoBean.getUserHeadImg()).centerCrop().transform(new GlideCircleTransform()).into(binding.ivHead);
        } else {
            binding.tvNickName.setText("点击登录>");
            binding.ivLevels.setVisibility(View.GONE);
            binding.icQrCode.setVisibility(View.GONE);
            binding.ivDesc.setText("未认证");
            GlideApp.with(binding.ivHead.getContext()).load(UIUtils.getDrawable(R.mipmap.default_head)).centerCrop().transform(new GlideCircleTransform()).into(binding.ivHead);

            return;
        }
        int realState = userInfoBean.getRealState();
        String realStateDesc = "";
        switch (realState) {
            case 0:
                realStateDesc = "未认证";
                break;
            case 1:
                realStateDesc = "已实名认证";
                break;
            case 2:
                realStateDesc = "审核中";
                break;
        }
        binding.ivDesc.setText(realStateDesc);
        int userLevel = userInfoBean.getUserLevel();
        int userlevelDrawable = 0;
        switch (userLevel) {
            case 0:
                userlevelDrawable = R.mipmap.ic_level_0;
                binding.icQrCode.setVisibility(View.GONE);
                break;
            case 1:
                userlevelDrawable = R.mipmap.ic_level_1;
                binding.icQrCode.setVisibility(View.VISIBLE);
                break;
            case 2:
                userlevelDrawable = R.mipmap.ic_level_2;
                binding.icQrCode.setVisibility(View.VISIBLE);
                break;
            case 3:
                userlevelDrawable = R.mipmap.ic_level_3;
                binding.icQrCode.setVisibility(View.VISIBLE);
                break;
        }
        binding.ivLevels.setImageResource(userlevelDrawable);
    }

    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "USER_INFO":
                String data = (String) netResponse.getData();
                userInfoBean = GsonUtil.GsonToBean(data, UserInfoBean.class);
                SPUtils.setParam(UIUtils.getContext(), "USER_INFO", data);
                AppConstant.USER_INFO_BEAN = userInfoBean;
                initUserInfo(userInfoBean);
                EventBus.getDefault().post(new NetResponse("LOGIN_SUCCESS", ""));
                break;
            case "GET_RECOMMOND_PROJECT":
                String data1 = String.valueOf(netResponse.getData());
                List<HomeDataBean.ProjectListBean> projectListBeans = GsonUtil.GsonToList(data1, HomeDataBean.ProjectListBean.class);
                projectList.addAll(projectListBeans);
                projectAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        projectAdapter = new ProjectAdapter(projectList);
        projectAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        projectAdapter.openLoadAnimation();
        binding.recyclerProject.setLayoutManager(linearLayoutManager);
        binding.recyclerProject.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomeDataBean.ProjectListBean bindingAdapterItem1 = projectList.get(position);
            Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("PROJECT_ID", String.valueOf(bindingAdapterItem1.getId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });
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


    public void showAddDL() {
        if (messageDialogBuilder!=null){
            messageDialogBuilder.show();
        }else {
            messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(getActivity())
                    .setTitle("提示")
                    .setMessage("加盟爱薇会员后即可开通!")
                    .addAction("联系客服", (dialog, index) -> {
                        dialog.dismiss();
                        connetKF();
                    })
                    .addAction("容我想想", (dialog, index) -> {
                        dialog.dismiss();
                    });
            messageDialogBuilder.show();
        }


    }


}
