package com.zysoft.tjawshapingapp.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.TextMsgBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.view.im.DefaultUser;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
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
        ImageView view1 = helper.getView(R.id.iv_img);
        LogUtils.e("msgItem:::" + item);
        //会话者信息
        UserInfo targetInfo = (UserInfo) item.getTargetInfo();
        LogUtils.e("msgItem:::targetInfo" + targetInfo);
        JMessageClient.getUserInfo(targetInfo.getUserName(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                helper.setText(R.id.tv_name, userInfo.getNickname());

                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        LogUtils.e("下载用户头像："+bitmap);
                        view1.setTag(null);
                        GlideApp.with(view1)
                                .load(bitmap)
                                .error(R.mipmap.default_head)
                                .centerCrop()
                                .transform(new GlideRoundTransform(4))
                                .into(view1);
                        view1.setTag(view1.getTag() != bitmap);
                    }
                });
            }
        });
        Message latestMessage = item.getLatestMessage();
        if (latestMessage == null) {
            return;
        }
        ContentType contentType = latestMessage.getContentType();
        String s = latestMessage.getContent().toJson();

        switch (contentType) {
            case text:
                TextMsgBean textMsgBean = GsonUtil.GsonToBean(s, TextMsgBean.class);
                helper.setText(R.id.tv_msg, textMsgBean.getText());

                LogUtils.e("文字消息" + s);
                break;
            case unknown:
                break;
            case image:
                helper.setText(R.id.tv_msg, "[图片]");
                break;
            case video:
                helper.setText(R.id.tv_msg, "[视频]");
                break;
            case file:
                break;
            case location:
                break;
            case custom:
                break;
            case voice:
                helper.setText(R.id.tv_msg, "[语音消息]");
                break;
        }

        int unReadMsgCnt = item.getUnReadMsgCnt();
        TextView view = helper.getView(R.id.tv_no_see);

        if (unReadMsgCnt != 0) {
            view.setText("+" + unReadMsgCnt + "");
        } else {
            view.setText("");
        }


    }
}
