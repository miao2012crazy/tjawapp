package com.zysoft.tjawshapingapp.view.pl;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.SelectImgAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CommonBean;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.SelectImg;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.LogUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityFbBinding;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr.miao on 2019/7/3.
 */

public class FBInputActivity extends CustomBaseActivity {

    private List<SelectImg> mainList = new ArrayList<>();
    private ActivityFbBinding binding;
    private List<LocalMedia> selectList = new ArrayList<>();
    private SelectImgAdapter adapter;
    private String orderId;
    private String projectId;
    private boolean isAdd = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_fb);
        binding = (ActivityFbBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.qmTopBar.setTitle("评论");
        binding.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        orderId = getIntent().getExtras().getString("orderId");
        projectId = getIntent().getExtras().getString("projectId");
        map.clear();
        map.put("projectId", projectId);
        NetModel.getInstance().getDataFromNet("GET_PROJECT_BASIC", HttpUrls.GET_PROJECT_BASIC, map);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UIUtils.getContext(), 3);
        initAdd();
        adapter = new SelectImgAdapter(mainList);
        binding.recyclerImg.setLayoutManager(gridLayoutManager);
        binding.recyclerImg.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectImg selectImg1 = mainList.get(position);
                if (selectImg1.isAdd()) {
                    openPicSelect();
                }
            }
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_del_img:
                    mainList.remove(position);
                    selectList.remove(position);
                    if (selectList.size() == 5) {
                        initAdd();
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        });

        binding.btnFb.setOnClickListener(v -> {
            checkData();
        });


    }

    private void initAdd() {
        if (mainList.size() < 6) {
            SelectImg selectImg = new SelectImg("", String.valueOf(R.mipmap.ic_add_img),
                    "", 0, false, false,
                    99, 0, -1, "",
                    true, 0, 0, true);
            mainList.add(mainList.size(), selectImg);
        }

    }

    private void checkData() {
        if (selectList.size() == 0) {
            showTipe(3, "至少选择一张图片～");

            return;
        }
        showTipe(2, "正在提交评论");
        String content = binding.etComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showTipe(0, "请输入内容！");
            return;
        }
        Api api = NovateUtil.initApi();
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean == null) {
            return;
        }
        MultipartBody.Builder builder1 = builder
                .addFormDataPart("content", content)
                .addFormDataPart("orderId", orderId)
                .addFormDataPart("userId", String.valueOf(userInfoBean.getUserId()));
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            SelectImg selectImg = (SelectImg) mainList.get(i);
            File file = new File(selectImg.getCompressPath());
            files.add(file);
            LogUtils.e(String.valueOf(file.length()));
            String[] split = file.getName().split("\\.");
            builder1.addFormDataPart("file" + i, file.getName(), RequestBody.create(MediaType.parse("image/" + split[split.length - 1]), file));
        }

        RequestBody build = builder1.build();


        api.uploadPic(HttpUrls.getBaseUrl() + "plOrder", build).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    CommonBean commonBean = GsonUtil.GsonToBean(string, CommonBean.class);
                    switch (commonBean.getResult()) {
                        case "S":
                            showTipWhisBtn(null, commonBean.getData()).show().setOnDismissListener(dialog -> finish());
                            break;
                        case "F":
                            showTipe(0, "服务器开小差了，请稍后尝试！");
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                closeDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, java.lang.Throwable t) {
                t.printStackTrace();
                closeDialog();
                showTipe(0, "服务器开小差了，请稍后尝试！");
            }
        });

    }

    private void openPicSelect() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(6)// 最大图片选择数量 int
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
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PictureConfig.CHOOSE_REQUEST:
                    mainList.clear();
                    // 图片、视频、音频选择结果回调
                    selectList.clear();
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia item : selectList) {
                        //转换
                        SelectImg selectImg = new SelectImg(item.getPath(), item.getCompressPath(),
                                item.getCutPath(), item.getDuration(), item.isChecked(), item.isCut(),
                                item.position, item.getNum(), item.getMimeType(), item.getPictureType(),
                                item.isCompressed(), item.getWidth(), item.getHeight(), false);
                        mainList.add(0, selectImg);
                        LogUtils.e(" 图片压缩后：：" + item.getCompressPath());
                    }
                    initAdd();
                    adapter.notifyDataSetChanged();

                    break;
            }
        }
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_PROJECT_BASIC":
                String data = (String) netResponse.getData();
                HomeDataBean.ProjectInfoBean projectInfoBean = GsonUtil.GsonToBean(data, HomeDataBean.ProjectInfoBean.class);
                binding.setItem(projectInfoBean);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
