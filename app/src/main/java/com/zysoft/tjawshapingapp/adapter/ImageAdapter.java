package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/3.
 */

public class ImageAdapter extends BaseQuickAdapter<ProjectDetailBean.ImgDetailBean,BaseViewHolder> {
    public ImageAdapter(@Nullable List<ProjectDetailBean.ImgDetailBean> data) {
        super(R.layout.item_img_2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetailBean.ImgDetailBean item) {
        ImageView view = helper.getView(R.id.image_msg);
        view.setTag(null);
        GlideApp.with(view.getContext())
                .load(item.getImgPath())
                .centerCrop()
                .transform(new GlideRoundTransform(4))
                .into(view);
    }
}
