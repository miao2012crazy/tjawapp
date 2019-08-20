package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CommonBean;
import com.zysoft.tjawshapingapp.bean.RealBean;
import com.zysoft.tjawshapingapp.bean.SelectImg;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityRealBinding;
import com.zysoft.tjawshapingapp.handler.CustomHandlerEvent;
import com.zysoft.tjawshapingapp.http.Api;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.http.NovateUtil;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * Created by mr.miao on 2019/5/27.
 */

public class RealStateActivity extends CustomBaseActivity {

    private final int REQUEST_CODE_CHOOSE = 100;
    private int RC_CAMERA_AND_LOCATION = 99;

    private ActivityRealBinding binding;
    private int position = -1;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_real);
        binding = (ActivityRealBinding) viewDataBinding;

        EventBus.getDefault().register(this);
        binding.setHandler(new CustomHandlerEvent(this));

        binding.ivFront.setOnClickListener(v -> {
            position = 0;
            chooseImg();
        });
        binding.ivBg.setOnClickListener(v -> {
            position = 1;
            chooseImg();
        });
        binding.title.qmTopBar.setTitle("实名认证");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());

        binding.btnCommit.setOnClickListener(v -> {
            Api api = NovateUtil.initApi();
            String s = binding.etRecvName.getText().toString();
            if (TextUtils.isEmpty(s)) {
                showTipe(0, "请输入真实姓名！");
                return;
            }
            String s1 = binding.etRecvNum.getText().toString();

            if (TextUtils.isEmpty(s1)) {
                showTipe(0, "请输入身份证号码！");
                return;
            }


            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            MultipartBody.Builder builder1 = builder.addFormDataPart("userIdenName", s)
                    .addFormDataPart("userIdenNum", s1);
            String tag = String.valueOf(binding.ivFront.getTag());
            File file = new File(tag);
            String[] split = file.getName().split("\\.");

            String tagbg = String.valueOf(binding.ivFront.getTag());
            File filebg = new File(tagbg);
            String[] splitbg = filebg.getName().split("\\.");

            builder1.addFormDataPart("userfront", file.getName(), RequestBody.create(MediaType.parse("image/" + split[split.length - 1]), file));
            builder1.addFormDataPart("userbg", filebg.getName(), RequestBody.create(MediaType.parse("image/" + splitbg[splitbg.length - 1]), file));
//            }

            RequestBody build = builder1.build();
            api.uploadPic(HttpUrls.getBaseUrl() + "setUserRealInfo", build).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String string = response.body().string();
                        CommonBean commonBean = GsonUtil.GsonToBean(string, CommonBean.class);
                        switch (commonBean.getResult()) {
                            case "S":
                                showTipWhisBtn(null, commonBean.getMsg()).show().setOnDismissListener(dialog -> finish());

                                break;
                            case "F":
                                showTipe(0, "服务器开小差了，请稍后尝试！");
                                break;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    UIUtils.showToast("请求失败");
                }
            });
        });

        map.clear();
        NetModel.getInstance().getDataFromNet("GET_REAL_STATE", HttpUrls.GET_REAL_STATE, map);


    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_REAL_STATE":
                String data = (String) netResponse.getData();
                LogUtils.e(data);
                RealBean realBean = GsonUtil.GsonToBean(data, RealBean.class);
                binding.setItem(realBean);
                break;
        }
    }


    private void chooseImg() {
        //检测是否有相机权限
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(RealStateActivity.this, perms)) {
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
                    .enableCrop(false)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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
            EasyPermissions.requestPermissions(RealStateActivity.this, "上传照片需要使用手机存储和相机权限",
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
                    String cutPath = localMedia.getCompressPath();
                    switch (position) {
                        case 0:
                            binding.ivFront.setTag(cutPath);
                            break;
                        case 1:
                            binding.ivBg.setTag(cutPath);
                            break;
                    }

                    GlideApp.with(RealStateActivity.this).load(cutPath).into(position == 0 ? binding.ivFront : binding.ivBg);

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
