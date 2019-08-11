package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityImDetailBinding;
import com.zysoft.tjawshapingapp.view.im.DefaultUser;
import com.zysoft.tjawshapingapp.view.im.MyMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
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
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import me.jessyan.autosize.utils.LogUtils;

import static cn.jiguang.imui.commons.models.IMessage.MessageType.RECEIVE_TEXT;
import static cn.jiguang.imui.commons.models.IMessage.MessageType.SEND_TEXT;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(IMDetailActivity.this, R.layout.activity_im_detail);
        EventBus.getDefault().register(this);
        //发送者信息
        sendInfo = JMessageClient.getMyInfo();
        recvUserName = getIntent().getExtras().getString("recvUserName");
        String recvUserAppkey = getIntent().getExtras().getString("recvUserAppkey");
        singleConversation = Conversation.createSingleConversation(recvUserName, recvUserAppkey);
        //构建消息列表adapter
        adapter = new MsgListAdapter<>(AppConstant.USER_INFO_BEAN.getUserTel(), new MsgListAdapter.HoldersConfig(), new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                GlideApp.with(IMDetailActivity.this)
                        .load(string)
                        .centerCrop()
                        .into(avatarImageView);
            }

            @Override
            public void loadImage(ImageView imageView, String string) {
                GlideApp.with(IMDetailActivity.this)
                        .load(string)
                        .centerCrop()
                        .into(imageView);
            }
        });
        bind.msgList.setAdapter(adapter);

        initMsg();
        initChatView();
        recvUserName = getIntent().getExtras().getString("recvNickName");
        bind.title.qmTopBar.setTitle(recvUserName);
        bind.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
    }

    private void initChatView() {
        //自定义输入菜单
        MenuManager menuManager = bind.chatInput.getMenuManager();
        menuManager.addCustomMenu("CUSTOM_MENU",R.layout.layout_menu_item,R.layout.layout_menu_feature);
        Menu build = Menu.newBuilder().
                customize(true).
                setRight(Menu.TAG_SEND).
                setLeft(Menu.TAG_VOICE).
                build();
        menuManager.setMenu(build);

        bind.chatInput.setMenuContainerHeight(40);
        bind.chatInput.setMenuClickListener(new OnMenuClickListener() {
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) {
                    return false;
                }
                // 输入框输入文字后，点击发送按钮事件
                UIUtils.showToast(input.toString());
                sendMessage(input.toString());
                return true;
            }

            @Override
            public void onSendFiles(List<FileItem> list) {

            }


            @Override
            public boolean switchToMicrophoneMode() {
//                // 点击语音按钮触发事件，显示录音界面前触发此事件
//                // 返回 true 表示使用默认的界面，若返回 false 应该自己实现界面
//                String[] perms = new String[]{
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                };
//
//                if (!EasyPermissions.hasPermissions(IMDetailActivity.this, perms)) {
//                    EasyPermissions.requestPermissions(IMDetailActivity.this,
//                            getResources().getString(R.string.rationale_record_voice),
//                            RC_RECORD_VOICE, perms);
//                }
                return true;
            }

            @Override
            public boolean switchToGalleryMode() {
               UIUtils.showToast("点击了选择图片");
                return true;
            }

            @Override
            public boolean switchToCameraMode() {
                // 点击拍照按钮触发事件，显示拍照界面前触发此事件
                // 返回 true 表示使用默认的界面
                return true;
            }

            @Override
            public boolean switchToEmojiMode() {
                return false;
            }
        });
        bind.chatInput.setOnClickEditTextListener(new OnClickEditTextListener() {
            @Override
            public void onTouchEditText() {
                adapter.getLayoutManager().scrollToPosition(0);
            }
        });


        mRecordVoiceBtn = bind.chatInput.getRecordVoiceButton();
        mRecordVoiceBtn.setRecordVoiceListener(new RecordVoiceListener() {
            @Override
            public void onStartRecord() {
                // Show record voice interface
                // 设置存放录音文件目录
                File rootDir = UIUtils.getContext().getFilesDir();
                String fileDir = rootDir.getAbsolutePath() + "/voice";
                mRecordVoiceBtn.setVoiceFilePath(fileDir, CommonUtil.ms2date("yyyy_MMdd_hhmmss",new Date().getTime()));
            }

            @Override
            public void onFinishRecord(File voiceFile, int duration) {
                MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE, IMessage.MessageStatus.SEND_SUCCEED);
                message.setMediaFilePath(voiceFile.getPath());
                message.setDuration(duration);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                adapter.addToStart(message, true);
            }

            @Override
            public void onCancelRecord() {

            }

            /**
             * 录音试听界面，点击取消按钮触发
             * 0.7.3 后添加此事件
             */
            @Override
            public void onPreviewCancel() {

            }

            /**
             * 录音试听界面，点击发送按钮触发
             * 0.7.3 后增加此事件
             */
            @Override
            public void onPreviewSend() {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "MSG":
                parseData((Message) netResponse.getData());
                break;
        }
    }

    private void parseData(Message msg) {
        UserInfo targetInfo = (UserInfo) msg.getTargetInfo();
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                textContent.getText();
                //创建一个消息对象
                MyMessage myMessage = new MyMessage(((TextContent) msg.getContent()).getText(), IMessage.MessageType.RECEIVE_TEXT, IMessage.MessageStatus.RECEIVE_SUCCEED);
                addMsg(myMessage, msg, targetInfo);
                messageList.add(msg);
//                adapter.notifyDataSetChanged();
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
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
    }


    private void sendMessage(String msg) {
        //创建文本消息
        TextContent content = new TextContent(msg);
        //获取创建的消息
        Message message1 = singleConversation.createSendMessage(content);
        //创建展示的本地消息
        final MyMessage myMessage = new MyMessage(msg, SEND_TEXT, IMessage.MessageStatus.SEND_SUCCEED);
        myMessage.setMessage(message1);
        myMessage.setTimeString(CommonUtil.ms2date("MM-dd HH:mm", message1.getCreateTime()));
        myMessage.setUser(new DefaultUser(JMessageClient.getMyInfo().getUserName(), "DeadPool", Uri.fromFile(JMessageClient.getMyInfo().getAvatarFile()).toString()));
        //返回的结果
        message1.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    //将消息添加到本地视图
                    adapter.addToStart(myMessage, true);
                }
            }
        });
        //向服务器发送消息
        JMessageClient.sendMessage(message1);
    }


    private void initMsg() {
        messageList = singleConversation.getAllMessage();
        for (Message msg : messageList) {
            MessageStatus status = msg.getStatus();
            LogUtils.e("miao发送还是接收：：" + status.toString());
            MyMessage myMessage = null;
            switch (status) {
                case receive_success:
                    myMessage = new MyMessage(((TextContent) msg.getContent()).getText(), RECEIVE_TEXT, IMessage.MessageStatus.RECEIVE_SUCCEED);
                    UserInfo targetInfo = (UserInfo) msg.getTargetInfo();
                    addMsg(myMessage, msg, targetInfo);
                    break;
                case send_success:
                    myMessage = new MyMessage(((TextContent) msg.getContent()).getText(), SEND_TEXT, IMessage.MessageStatus.SEND_SUCCEED);
                    addMsg(myMessage, msg, JMessageClient.getMyInfo());
                    break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void addMsg(MyMessage myMessage, Message msg, UserInfo userInfo) {
        myMessage.setMessage(msg);
        myMessage.setPosition(msg.getId());
        myMessage.setMsgID(msg.getServerMessageId());
        myMessage.setText(((TextContent) msg.getContent()).getText() + "");
        myMessage.setTimeString(CommonUtil.ms2date("MM-dd HH:mm", msg.getCreateTime()));
        File avatarFile = userInfo.getAvatarFile();
        String avatar = null;
        if (avatarFile != null) {
            avatar = avatarFile.toURI().toString();
        }
        myMessage.setUser(new DefaultUser(userInfo.getUserName(), userInfo.getNickname(), avatar));
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
