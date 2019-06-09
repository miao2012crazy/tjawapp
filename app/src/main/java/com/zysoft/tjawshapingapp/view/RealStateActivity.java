package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zysoft.baseapp.commonUtil.GlideApp;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.ActivityRealBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by mr.miao on 2019/5/27.
 */

public class RealStateActivity extends CustomBaseActivity{

    private final int REQUEST_CODE_CHOOSE = 100;
    private int RC_CAMERA_AND_LOCATION = 99;

    private ActivityRealBinding binding;
    private int position=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_real);
        binding = (ActivityRealBinding) viewDataBinding;
        binding.ivFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=0;
                chooseImg();
            }
        });
        binding.ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=1;
                chooseImg();
            }
        });
    }

    private void chooseImg() {
        //检测是否有相机权限
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(RealStateActivity.this, perms)) {
            // 已经申请过权限，做想做的事
            PictureSelector.create(RealStateActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(1)// 最大图片选择数量
//                    .minSelectNum(1)// 最小选择数量
//                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
//                    .previewImage(true)// 是否可预览图片
//                    .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                    .isCamera(true)// 是否显示拍照按钮
//                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(true)// 是否裁剪
//                    .compress(true)// 是否压缩
//                    .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
//                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(16,9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                    //.selectionMedia(selectList)// 是否传入已选图片
//                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                    .cropCompressQuality(90)// 裁剪压缩质量 默认100
//                    //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
//                    //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
//                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                    .rotateEnabled(false) // 裁剪是否可旋转图片
//                    //.scaleEnabled()// 裁剪是否可放大缩小图片
//                    //.recordVideoSecond()//录制视频秒数 默认60s
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
                    LocalMedia localMedia = images.get(0);
                    String cutPath = localMedia.getCutPath();
                    GlideApp.with(RealStateActivity.this).load(cutPath).into(position==0?binding.ivFront:binding.ivBg);
//                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }



}
