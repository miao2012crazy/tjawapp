package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.view.IMDetailActivity;

import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class CustomMsgListAdapter extends BaseQuickAdapter<Conversation,BaseViewHolder>{
    public CustomMsgListAdapter(@Nullable List<Conversation> data) {
        super(R.layout.item_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Conversation item) {
        //最新消息
        Message latestMessage = item.getLatestMessage();
        String text;
        try {
             text = ((TextContent) latestMessage.getContent()).getText();
        }catch (Exception ex){
            text=item.getLatestText();
        }

        //会话者信息
        UserInfo targetInfo = (UserInfo) item.getTargetInfo();
        String nickname = targetInfo.getNickname();

        helper.setText(R.id.tv_name,nickname);
        helper.setText(R.id.tv_msg,text);

        Glide.with(UIUtils.getContext())
                .load(targetInfo.getAvatarFile())
                .centerCrop()
                .into((ImageView) helper.getView(R.id.iv_img));

        int unReadMsgCnt = item.getUnReadMsgCnt();
        TextView view = helper.getView(R.id.tv_no_see);

        if (unReadMsgCnt!=0){
            view.setText("+"+unReadMsgCnt+"");
        }else {
            view.setText("");
        }


    }
}
