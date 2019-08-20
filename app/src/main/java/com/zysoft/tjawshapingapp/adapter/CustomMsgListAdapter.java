package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.TextMsgBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import me.jessyan.autosize.utils.LogUtils;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class CustomMsgListAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {
    public CustomMsgListAdapter(@Nullable List<Conversation> data) {
        super(R.layout.item_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Conversation item) {
        //最新消息

        LogUtils.e("msgItem:::" + item);

//        String text;
//        try {
//             text = ((TextContent) latestMessage.getContent()).getText();
//        }catch (Exception ex){
//            text=item.getLatestText();
//        }

        //会话者信息
        UserInfo targetInfo = (UserInfo) item.getTargetInfo();
        String nickname = targetInfo.getNickname();
        Message latestMessage = item.getLatestMessage();
        ContentType contentType = latestMessage.getContentType();
        String s = latestMessage.getContent().toJson();

        LogUtils.e("文字消息" + s);

        switch (contentType) {
            case text:
                TextMsgBean textMsgBean = GsonUtil.GsonToBean(s, TextMsgBean.class);
                helper.setText(R.id.tv_msg, textMsgBean.getText());

                LogUtils.e("文字消息" + s);
                break;
            case unknown:

                break;
            case image:
                break;
            case video:
                break;
            case file:
                break;
            case location:
                break;
            case custom:
                break;
            case voice:
                break;
        }
        helper.setText(R.id.tv_name, nickname);
        RequestOptions requestOptions = new RequestOptions().centerCrop();

        ImageView view1 = helper.getView(R.id.iv_img);
//        if (!targetInfo.getAvatarFile().equals(view1.getTag())){
//            view1.setTag(null);
//            Glide.with(view1.getContext())
//                    .load(targetInfo.getAvatarFile())
//                    .apply(requestOptions)
//                    .into(view1);
//            view1.setTag(targetInfo.getAvatarFile());
//        }


        int unReadMsgCnt = item.getUnReadMsgCnt();
        TextView view = helper.getView(R.id.tv_no_see);

        if (unReadMsgCnt != 0) {
            view.setText("+" + unReadMsgCnt + "");
        } else {
            view.setText("");
        }


    }
}
