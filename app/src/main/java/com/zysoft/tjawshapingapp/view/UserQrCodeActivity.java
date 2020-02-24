package com.zysoft.tjawshapingapp.view;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityQrCodeBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.http.NovateUtil;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import me.jessyan.autosize.utils.LogUtils;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/4.
 */

public class UserQrCodeActivity extends CustomBaseActivity {

    private ActivityQrCodeBinding binding;
    private String data;
    private int RC_CAMERA_AND_LOCATION=990;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_qr_code);
        binding = (ActivityQrCodeBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.qmTopBar.setTitle("推广码");
        binding.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        map.clear();
        map.put("lastUserId", AppConstant.USER_INFO_BEAN.getUserId());
        NetModel.getInstance().getDataFromNet("GET_QR_CODE", HttpUrls.GET_QR_CODE, map);
        binding.btnSave.setOnClickListener(v -> {
            String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(UserQrCodeActivity.this, perms)) {


                if (TextUtils.isEmpty(data)){
                    return;
                }
                showTipe(2, "正在保存...");
                //保存到本地
                NovateUtil.getInstance().download(data, new DownLoadCallBack() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeDialog();

                    }

                    @Override
                    public void onSucess(String key, String path, String name, long fileSize) {
                        LogUtils.e("下载完成" + key + ":" + path + ":" + name + ":" + fileSize);
                        //上传
                        File file = new File(path + name);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                        try{
                            saveBitmap(bitmap);
                        }catch (Exception ex){
                            closeDialog();
                        }

                    }
                });


            } else {
                // 没有申请过权限，现在去申请
                EasyPermissions.requestPermissions(UserQrCodeActivity.this, "保存图片到相册",
                        RC_CAMERA_AND_LOCATION, perms);
            }




        });





    }

    public String saveBitmap(Bitmap bitmap) {
        try {
            // 获取内置SD卡路径
            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
            // 图片文件路径
            File file = new File(sdCardPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                String name = file1.getName();
                if (name.endsWith("awqrcode.png")) {
                    boolean flag = file1.delete();
                }
            }
            String filePath = sdCardPath + "/awqrcode.png";
            file = new File(filePath);
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(UIUtils.getContext().getContentResolver(),
                    file.getAbsolutePath(), "awqrcode.png", null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            UIUtils.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            closeDialog();
            showTipe(1, "已保存");

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_QR_CODE":
                data = (String) netResponse.getData();
                if (!data.equals(binding.ivQrCode.getTag())) {
                    binding.ivQrCode.setTag(null);
                    GlideApp.with(binding.ivQrCode.getContext())
                            .load(data)
                            .centerCrop()
                            .transform(new GlideRoundTransform(4))
                            .into(binding.ivQrCode);
                    binding.ivQrCode.setTag(data);
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
