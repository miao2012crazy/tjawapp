package com.zysoft.tjawshapingapp.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danikula.videocache.HttpProxyCacheServer;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.List;


/**
 * Created by mr.miao on 2019/8/11.
 */

public class VideoAdapter extends BaseQuickAdapter<ProjectVideoBean,BaseViewHolder> {



    public VideoAdapter(@Nullable List<ProjectVideoBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectVideoBean item) {
        VideoView view = helper.getView(R.id.video_view);

        HttpProxyCacheServer proxy = CustomApplaction.getProxy(UIUtils.getContext());
        String proxyUrl = proxy.getProxyUrl(item.getVideoPath());
        view.setVideoPath(proxyUrl);
        ImageView img_thumb= helper.getView(R.id.img_thumb);
        //加载视频第一帧
        Bitmap netVideoBitmap = CommonUtil.getNetVideoBitmap(item.getVideoPath());
        img_thumb.setImageBitmap(netVideoBitmap);

    }

}





