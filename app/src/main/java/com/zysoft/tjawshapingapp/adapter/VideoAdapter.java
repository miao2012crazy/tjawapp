package com.zysoft.tjawshapingapp.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danikula.videocache.HttpProxyCacheServer;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.view.ProjectDetailActivity;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import java.security.MessageDigest;
import java.util.List;


/**
 * Created by mr.miao on 2019/8/11.
 */

public class VideoAdapter extends BaseQuickAdapter<ProjectVideoBean, BaseViewHolder> {


    public VideoAdapter(@Nullable List<ProjectVideoBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectVideoBean item) {

        helper.setText(R.id.tv_project_name, item.getVideoName())
                .setText(R.id.tv_desc, item.getVideoDesc()).addOnClickListener(R.id.btn_project_detail);

        VideoView view = helper.getView(R.id.video_view);
        Button btnProjectDetail = helper.getView(R.id.btn_project_detail);

        HttpProxyCacheServer proxy = CustomApplaction.getProxy(UIUtils.getContext());
        String proxyUrl = proxy.getProxyUrl(item.getVideoPath());
        view.setVideoPath(proxyUrl);
        ImageView img_thumb = helper.getView(R.id.img_thumb);
        GlideApp.with(img_thumb.getContext()).load(item.getVideoImg()).into(img_thumb);
        if (item.getType() == 0) {
            btnProjectDetail.setText("查看详情");
            //活动
            if (TextUtils.isEmpty(item.getLink())) {
                btnProjectDetail.setVisibility(View.GONE);
                return;
            }
        } else if (item.getType() == 1) {
            //商品
            btnProjectDetail.setVisibility(View.GONE);
        } else if (item.getType() == 2) {
            //项目
            if (item.getProjectId() == 0) {
                btnProjectDetail.setVisibility(View.GONE);
                return;
            }
        }
    }

}





