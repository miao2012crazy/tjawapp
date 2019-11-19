package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zysoft.tjawshapingapp.MainActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.SoftKeyBoardListener;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityImDetailBinding;
import com.zysoft.tjawshapingapp.view.im.ChatEmotionFragment;
import com.zysoft.tjawshapingapp.view.im.ChatFunctionFragment;
import com.zysoft.tjawshapingapp.view.im.DefaultUser;
import com.zysoft.tjawshapingapp.view.im.FullImageActivity;
import com.zysoft.tjawshapingapp.view.im.IMActivity;
import com.zysoft.tjawshapingapp.view.im.MyMessage;
import com.zysoft.tjawshapingapp.view.im.adapter.ChatAdapter;
import com.zysoft.tjawshapingapp.view.im.adapter.CommonFragmentPagerAdapter;
import com.zysoft.tjawshapingapp.view.im.enity.FullImageInfo;
import com.zysoft.tjawshapingapp.view.im.util.GlobalOnItemClickManagerUtils;
import com.zysoft.tjawshapingapp.view.im.widget.EmotionInputDetector;
import com.zysoft.tjawshapingapp.view.imuisample.messages.BrowserImageActivity;
import com.zysoft.tjawshapingapp.view.imuisample.messages.MessageListActivity;
import com.zysoft.tjawshapingapp.view.imuisample.messages.VideoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.jiguang.imui.chatinput.listener.OnClickEditTextListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.menu.Menu;
import cn.jiguang.imui.chatinput.menu.MenuManager;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.record.RecordVoiceButton;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import me.jessyan.autosize.utils.LogUtils;
import pub.devrel.easypermissions.EasyPermissions;

import static cn.jiguang.imui.commons.models.IMessage.MessageType.RECEIVE_FILE;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.RECEIVE_IMAGE;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.RECEIVE_TEXT;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.RECEIVE_VOICE;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.SEND_IMAGE;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.SEND_TEXT;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.SEND_VOICE;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class IMDetailActivity extends CustomBaseActivity {

    private Conversation singleConversation;
    private MsgListAdapter<MyMessage> adapter;
    private ActivityImDetailBinding bind;
    private UserInfo sendInfo;
    private List<Message> messageList;
    private String recvUserName;
    private final int RC_RECORD_VOICE = 0x0001;
    private final int RC_CAMERA = 0x0002;
    private final int RC_PHOTO = 0x0003;
    private RecordVoiceButton mRecordVoiceBtn;
    //消息发送者
    private DefaultUser sendUser;
    private DefaultUser targetUser;
    private UserInfo targetInfo;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter bottomAdapter;
    private EmotionInputDetector mDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(IMDetailActivity.this, R.layout.activity_im_detail);
        EventBus.getDefault().register(this);
        recvUserName = getIntent().getExtras().getString("recvUserName");
        String recvUserAppkey = getIntent().getExtras().getString("recvUserAppkey");
        singleConversation = Conversation.createSingleConversation(recvUserName, recvUserAppkey);

        //自身
        sendInfo = JMessageClient.getMyInfo();
        File avatarFile = sendInfo.getAvatarFile();
        if (avatarFile == null) {
            sendInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    sendInfo = JMessageClient.getMyInfo();
                    sendUser = new DefaultUser(String.valueOf(sendInfo.getUserID()), sendInfo.getDisplayName(), sendInfo.getAvatarFile().getPath());

                }
            });
        }
        sendUser = new DefaultUser(String.valueOf(sendInfo.getUserID()), sendInfo.getDisplayName(), avatarFile.getPath());

        //目标用户
        targetInfo = (UserInfo) singleConversation.getTargetInfo();
        File avatarFile1 = targetInfo.getAvatarFile();
        if (avatarFile1 == null) {
            sendInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    targetInfo = JMessageClient.getMyInfo();
                    targetUser = new DefaultUser(String.valueOf(targetInfo.getUserID()), targetInfo.getDisplayName(), targetInfo.getAvatarFile().getPath());

                }
            });
        }
        targetUser = new DefaultUser(String.valueOf(targetInfo.getUserID()), targetInfo.getDisplayName(), avatarFile1.getPath());

        //构建消息列表adapter
        adapter = new MsgListAdapter<>(AppConstant.USER_INFO_BEAN.getUserTel(), new MsgListAdapter.HoldersConfig(), new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {

                GlideApp.with(UIUtils.getContext())
                        .load(string)
                        .centerCrop()
                        .into(avatarImageView);
            }

            @Override
            public void loadImage(ImageView imageView, String string) {

                GlideApp.with(UIUtils.getContext())
                        .load(string)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void loadVideo(ImageView imageCover, String uri) {

            }
        });
        adapter.setOnMsgClickListener(message -> {
           if (message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()
                    || message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {
                UIUtils.showToast("点击了图片");
            } else {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.message_click_hint),
                        Toast.LENGTH_SHORT).show();
            }
        });

        bind.msgList.setAdapter(adapter);
        bind.msgList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:

                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        initMsg();
        initChatView();

        bind.title.qmTopBar.setTitle(recvUserName);
        bind.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        SoftKeyBoardListener.setListener(this,new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Toast.makeText(IMDetailActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
                Toast.makeText(IMDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initChatView() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        bottomAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        bind.chatInput.viewpager.setAdapter(bottomAdapter);
        bind.chatInput.viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(bind.chatInput.emotionLayout)
                .setViewPager(bind.chatInput.viewpager)
                .bindToContent(bind.msgList)
                .bindToEditText(bind.chatInput.editText)
                .bindToEmotionButton(bind.chatInput.emotionButton)
                .bindToAddButton(bind.chatInput.emotionAdd)
                .bindToSendButton(bind.chatInput.emotionSend)
                .bindToVoiceButton(bind.chatInput.emotionVoice)
                .bindToVoiceText(bind.chatInput.voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(bind.chatInput.editText);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "MSG":
                parseData((Message) netResponse.getData());
                break;
            case "VOICE":
                //录音结束
                MyMessage data = (MyMessage) netResponse.getData();
                LogUtils.e("录音：：" + data.toString());
                //发送消息
//                sendVoiceMessage(data);
                try {
                    VoiceContent voiceContent = new VoiceContent(new File(data.getMediaFilePath()), Integer.parseInt(String.valueOf(data.getDuration())));
                    sendMessage(data, voiceContent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "SEND_TEXT":
                MyMessage data1 = (MyMessage) netResponse.getData();
                LogUtils.e("文字：：" + data1.toString());
                sendMessage(data1, new TextContent(data1.getText()));
                break;

            case "SEND_IMAGE":
                try {
                    MyMessage data2 = (MyMessage) netResponse.getData();
                    LogUtils.e("拍照：：" + data2.toString());
                    sendMessage(data2, new ImageContent(new File(data2.getMediaFilePath())));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    private void parseData(Message msg) {
        messageList.add(msg);
        MyMessage myMessage = null;
        switch (msg.getContentType()) {
            case text:
                //创建一个消息对象
                String text = ((TextContent) msg.getContent()).getText();
                myMessage = new MyMessage(text, RECEIVE_TEXT.ordinal());
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                String localPath = imageContent.getLocalPath();//图片本地地址
                String localThumbnailPath = imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                myMessage = new MyMessage(localThumbnailPath, RECEIVE_IMAGE.ordinal());
                myMessage.setMediaFilePath(localThumbnailPath);

                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                String localPath1 = voiceContent.getLocalPath();//语音文件本地地址
                int duration = voiceContent.getDuration();//语音文件时长
                myMessage = new MyMessage(localPath1, RECEIVE_VOICE.ordinal());
                myMessage.setMediaFilePath(localPath1);
                myMessage.setDuration(duration);

                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()) {
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                }
                break;
        }
        myMessage.setUserInfo(targetUser);
        addMsg(myMessage);

    }


    private void sendMessage(MyMessage myMessage, MessageContent content) {
//        //创建文本消息
//        TextContent content = new TextContent(msg);
        //获取创建的消息
        Message message1 = singleConversation.createSendMessage(content);
        //创建展示的本地消息
        myMessage.setUserInfo(sendUser);
        myMessage.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        myMessage.setMessageStatus(IMessage.MessageStatus.SEND_GOING);
        adapter.addToStart(myMessage, true);

        //返回的结果
        message1.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                //将消息添加到本地视图
                if (i == 0) {
                    myMessage.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
                    adapter.notifyItemChanged(0);
                } else {
                    myMessage.setMessageStatus(IMessage.MessageStatus.SEND_FAILED);
                    adapter.notifyItemChanged(0);
                }
            }
        });
        //向服务器发送消息
        JMessageClient.sendMessage(message1);
    }


    private void initMsg() {
        messageList = singleConversation.getAllMessage();
        MyMessage myMessage = null;
        for (Message msg : messageList) {
            MessageStatus status = msg.getStatus();

            switch (msg.getContentType()) {
                case text:
                    //            MyMessage  myMessage = new MyMessage(((TextContent)msg.getContent()).getText(), msg.getContentType().ordinal());
                    //创建一个消息对象
                    String text = ((TextContent) msg.getContent()).getText();
                    myMessage = new MyMessage(text, status == MessageStatus.receive_success ? RECEIVE_TEXT.ordinal() : SEND_TEXT.ordinal());
                    break;
                case image:
                    //处理图片消息
                    ImageContent imageContent = (ImageContent) msg.getContent();
                    String localPath = imageContent.getLocalPath();//图片本地地址
                    String localThumbnailPath = imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                    myMessage = new MyMessage(localThumbnailPath, status == MessageStatus.receive_success ? RECEIVE_IMAGE.ordinal() : SEND_IMAGE.ordinal());
                    myMessage.setMediaFilePath(localThumbnailPath);
                    LogUtils.e(myMessage.toString());
                    break;
                case voice:
                    //处理语音消息
                    VoiceContent voiceContent = (VoiceContent) msg.getContent();
                    String localPath1 = voiceContent.getLocalPath();//语音文件本地地址
                    int duration = voiceContent.getDuration();//语音文件时长
                    myMessage = new MyMessage(localPath1, status == MessageStatus.receive_success ? RECEIVE_VOICE.ordinal() : SEND_VOICE.ordinal());
                    myMessage.setMediaFilePath(localPath1);
                    myMessage.setDuration(duration);
                    LogUtils.e("语音" + myMessage.toString());

                    break;
                case custom:
                    //处理自定义消息
                    CustomContent customContent = (CustomContent) msg.getContent();
                    customContent.getNumberValue("custom_num"); //获取自定义的值
                    customContent.getBooleanValue("custom_boolean");
                    customContent.getStringValue("custom_string");
                    break;
                case eventNotification:
                    //处理事件提醒消息
                    EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                    switch (eventNotificationContent.getEventNotificationType()) {
                        case group_member_added:
                            //群成员加群事件
                            break;
                        case group_member_removed:
                            //群成员被踢事件
                            break;
                        case group_member_exit:
                            //群成员退群事件
                            break;
                    }
                    break;
            }


            long createTime = msg.getCreateTime();
            myMessage.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(createTime)));


            LogUtils.e("miao发送还是接收：：" + status.toString());
            switch (status) {
                case receive_success:
                    LogUtils.e("miao接收：：" + msg);
                    myMessage.setUserInfo(targetUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.RECEIVE_SUCCEED);
                    break;
                case send_success:
                    LogUtils.e("miao发送：：" + msg);
                    myMessage.setUserInfo(sendUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
                    break;
                case send_fail:
                    myMessage.setUserInfo(sendUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.SEND_FAILED);
                    break;
                case receive_fail:
                    myMessage.setUserInfo(targetUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.RECEIVE_FAILED);
                    break;
                case send_going:
                    myMessage.setUserInfo(sendUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.SEND_GOING);
                    break;
                case receive_going:
                    myMessage.setUserInfo(targetUser);
                    myMessage.setMessageStatus(IMessage.MessageStatus.RECEIVE_GOING);
                    break;
            }
            addMsg(myMessage);

        }
        adapter.notifyDataSetChanged();
    }


    private void addMsg(MyMessage myMessage) {
        adapter.addToStart(myMessage, true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JMessageClient.enterSingleConversation(recvUserName);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JMessageClient.enterSingleConversation(recvUserName);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
