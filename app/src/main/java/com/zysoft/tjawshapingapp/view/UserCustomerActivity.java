package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.UserCustomerAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.UserCustomerBean;
import com.zysoft.tjawshapingapp.bean.UserTeamBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityUserCustomerBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.im.DefaultUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import me.jessyan.autosize.utils.LogUtils;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-29.
 */
public class UserCustomerActivity extends CustomBaseActivity {

    private ActivityUserCustomerBinding binding;
    private int index = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private List<UserCustomerBean.UserCustomerIncomBeansBean> mainList = new ArrayList<>();
    private UserCustomerAdapter userCustomerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_customer);
        binding = (ActivityUserCustomerBinding) dataBinding;
        binding.title.qmTopBar.setTitle("我的客户");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        EventBus.getDefault().register(this);

        getData(index);
        binding.smartRefresh.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            index = 0;
            getData(index);
        });
        binding.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            index = index + 1;
            getData(index);
        });

        userCustomerAdapter = new UserCustomerAdapter(mainList);
        userCustomerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_im:
                        UserCustomerBean.UserCustomerIncomBeansBean userCustomerIncomBeansBean = mainList.get(position);
                        JMessageClient.getUserInfo(userCustomerIncomBeansBean.getUserTel(), new GetUserInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, UserInfo userInfo) {
                               if (i==898002){
                                   showTipe(0,"此用户未登录APP");
                               }else {
                                   Conversation conversation = Conversation.createSingleConversation(userCustomerIncomBeansBean.getUserTel());
                                   conversation.resetUnreadCount();
                                   Intent intent6 = new Intent(UserCustomerActivity.this, IMDetailActivity.class);
                                   bundle.clear();
                                   UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
                                   bundle.putString("recvUserName", targetInfo.getUserName());
                                   bundle.putString("recvNickName", targetInfo.getNickname());
                                   bundle.putString("recvUserAppkey", targetInfo.getAppKey());
                                   intent6.putExtras(bundle);
                                   startActivity(intent6);
                               }
                            }
                        });

                        break;
                }
            }
        });
        userCustomerAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        userCustomerAdapter.openLoadAnimation();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        binding.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.setAdapter(userCustomerAdapter);

    }

    private void getData(int index) {
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("index", index);
        NetModel.getInstance().getDataFromNet("GET_USER_CUSTOMER", HttpUrls.GET_USER_CUSTOMER, map);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_USER_CUSTOMER":
                String data = (String) netResponse.getData();
                UserCustomerBean userCartBeans = GsonUtil.GsonToBean(data, UserCustomerBean.class);
                if (isRefresh) {
                    mainList.clear();
                    binding.smartRefresh.finishRefresh(true);
                    binding.smartRefresh.setNoMoreData(false);

                    isRefresh = false;
                }
                if (isLoadMore) {
                    if (userCartBeans.getUserCustomerIncomBeans().size() == 0) {
                        binding.smartRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        binding.smartRefresh.finishLoadMore(true);
                    }
                    isLoadMore = false;
                }
                binding.tvCustomerCount.setText(String.valueOf(userCartBeans.getCount()));
                binding.tvCustomerIncome.setText(String.valueOf(userCartBeans.getIncome()));
                mainList.addAll(userCartBeans.getUserCustomerIncomBeans());
                if (userCustomerAdapter != null) {
                    userCustomerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
