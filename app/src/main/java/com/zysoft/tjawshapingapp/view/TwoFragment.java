package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomMsgListAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.BaseRecAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentTwoBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import me.jessyan.autosize.utils.LogUtils;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class TwoFragment extends BaseLazyFragment {

    private FragmentTwoBinding binding;
    private Conversation singleConversation;
    private List<Conversation> conversationList = new ArrayList<>();
    private CustomMsgListAdapter customMsgListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_two, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customMsgListAdapter = new CustomMsgListAdapter(conversationList);
        customMsgListAdapter.openLoadAnimation();
        customMsgListAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));

        EventBus.getDefault().register(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        binding.recyclerMsgList.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerMsgList.recyclerList.setAdapter(customMsgListAdapter);
        customMsgListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转
                Conversation conversation = conversationList.get(position);
                conversation.resetUnreadCount();
                Intent intent = new Intent(getActivity(), IMDetailActivity.class);
                Bundle bundle = new Bundle();
                UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
                bundle.putString("recvUserName", targetInfo.getUserName());
                bundle.putString("recvNickName", targetInfo.getNickname());
                bundle.putString("recvUserAppkey", targetInfo.getAppKey());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        customMsgListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Conversation conversation = conversationList.get(position);
                UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();

                JMessageClient.deleteSingleConversation(targetInfo.getUserName());
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        conversationList.clear();
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (conversationList==null||conversationList.size()==0){
            return;
        }

        this.conversationList.addAll(conversationList);
        LogUtils.e("miao消息列表" + this.conversationList);
        customMsgListAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "MSG":
                initData();
                break;

        }
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
