package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.RadioGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.bean.CommonBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.bean.WXBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityUserInfoBinding;
import com.zysoft.tjawshapingapp.http.Api;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.http.NovateUtil;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/5.
 */

public class UserInfoActivity extends CustomBaseActivity {


    private ActivityUserInfoBinding bind;
    private UserInfoBean userBean;
    private int RC_CAMERA_AND_LOCATION = 9990;
    private String cutPath;
    private int userSex;
    private String userName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        bind = (ActivityUserInfoBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        map.clear();
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        NetModel.getInstance().getDataFromNet("GET_USER", HttpUrls.GET_USER, map);
        bind.title.qmTopBar.setTitle("个人信息");
        bind.title.qmTopBar.addRightTextButton("保存", R.id.btn_add).setOnClickListener(v -> {
            UIUtils.showToast("点击了保存");
            updateUserInfo();
        });

        bind.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        bind.ivHeader.setOnClickListener(v -> chooseImg());


//        bind.tvUserTelState.setOnClickListener(v -> {
//            //更换手机
//            startActivityBase(UpdateUserTelActivity.class);
//        });
//        bind.tvWechatState.setOnClickListener(v -> {
//            //绑定微信
//            //调用微信登录
//            SendAuth.Req req = new SendAuth.Req();
//            req.scope = "snsapi_userinfo";//
////                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
//            req.state = "wechat_sdk_微信登录";
//            CustomApplaction.getWXApi().sendReq(req);
//        });
        bind.rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_nan:
                        userSex=1;
                        break;

                    case R.id.rb_nv:
                        userSex=0;
                        break;

                }
            }
        });


    }

    private void updateUserInfo() {
        Api api = NovateUtil.initApi();
        String sign = bind.etSign.getText().toString();
        userName = bind.etRecvName.getText().toString();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody.Builder builder1 = builder.addFormDataPart("userNickName", userName)
                .addFormDataPart("userSex", String.valueOf(userSex))
                .addFormDataPart("userSign", sign)
                .addFormDataPart("userId", String.valueOf(AppConstant.USER_INFO_BEAN.getUserId()));
        if (!TextUtils.isEmpty(cutPath)) {
            File file = new File(cutPath);
            String[] split = file.getName().split("\\.");
            builder1.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/" + split[split.length - 1]), file));
        }
        RequestBody build = builder1.build();
        api.uploadPic(HttpUrls.getBaseUrl() + "updateUserInfo", build).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    LogUtils.e(response.toString());
                    String string = response.body().string();
                    CommonBean commonBean = GsonUtil.GsonToBean(string, CommonBean.class);
                    if (commonBean.getResult().equals("S")) {
                        showTipWhisBtn(null, commonBean.getMsg()).show().setOnDismissListener(dialog -> finish());
                    } else {
                        showTipe(0, "服务器开小差了，请稍后尝试！");
                    }
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showTipe(0, "服务器开小差了，请稍后尝试！");

            }
        });

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_USER":
                String data = (String) netResponse.getData();
                userBean = GsonUtil.GsonToBean(data, UserInfoBean.class);
                initView(userBean);
                break;
//            case "WXLOGINCODE":
//                // code
//                WXBean data1 = (WXBean) netResponse.getData();
//                //上传code 换取用户信息
//                //上传code 换取用户信息
//                map.put("code", data1.getCode());
//                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
//                NetModel.getInstance().getDataFromNet("UPDATE_WX_INFO", HttpUrls.UPDATE_WX_INFO, map);
//                break;
            case "UPDATE_WX_INFO":
                showTipe(1,"绑定成功");
                map.clear();
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                NetModel.getInstance().getDataFromNet("GET_USER", HttpUrls.GET_USER, map);
                break;
        }
    }


    private void initView(UserInfoBean userBean) {
        bind.etRecvName.setText(userBean.getUserNickName());
        bind.etRecvTel.setText(userBean.getUserTel());
        bind.etSign.setText(userBean.getUserSign());
        bind.rbNan.setChecked(userBean.getUserSex()==1);
        bind.rbNv.setChecked(userBean.getUserSex()==0);


        String productIcon = userBean.getUserHeadImg();
        if (!productIcon.equals(bind.ivHeader.getTag())) {
            bind.ivHeader.setTag(null);
            GlideApp.with(bind.ivHeader.getContext())
                    .load(productIcon)
                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .transform(new GlideCircleTransform())
                    .into(bind.ivHeader);
            bind.ivHeader.setTag(productIcon);
        }


    }


    private String getSex(int userSex) {
        switch (userSex) {
            case 0:
                return "女";
            case 1:
                return "男";
            case 2:
                return "未知";
            default:
                return "";
        }
    }


    private void chooseImg() {
        //检测是否有相机权限
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(UserInfoActivity.this, perms)) {
            // 已经申请过权限，做想做的事
            // 进入相册 以下是例子：用不到的api可以不写
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(9)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                    .isGif(false)// 是否显示gif图片 true or false
                    .compressSavePath(UIUtils.getPath())//压缩图片保存地址
                    .openClickSound(true)// 是否开启点击声音 true or false
//                    .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(UserInfoActivity.this, "上传头像需要使用手机存储和相机权限",
                    RC_CAMERA_AND_LOCATION, perms);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
                    LogUtils.e(String.valueOf(images.get(0)));
                    LocalMedia localMedia = images.get(0);
                    cutPath = localMedia.getCompressPath();
                    GlideApp.with(UserInfoActivity.this).load(cutPath).into(bind.ivHeader);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
