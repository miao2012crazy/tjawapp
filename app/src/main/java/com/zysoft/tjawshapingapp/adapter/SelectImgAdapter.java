package com.zysoft.tjawshapingapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.SelectImg;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-04.
 */
public class SelectImgAdapter extends BaseQuickAdapter<SelectImg, BaseViewHolder> {
    public SelectImgAdapter(List<SelectImg> mainList) {
        super(R.layout.item_select_img, mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectImg item) {

        helper.addOnClickListener(R.id.rl_del_img);

        ImageView iv = helper.getView(R.id.iv_image);
        RelativeLayout rl_del_img = helper.getView(R.id.rl_del_img);
//        iv.setTag(R.id.indexTag,item.getTag_drawable());
        if (item.isAdd()){
            iv.setImageResource(Integer.parseInt(item.getCompressPath()));
            rl_del_img.setVisibility(View.GONE);
        }else {
            if (!item.getCompressPath().equals(iv.getTag())) {
                iv.setTag(null);
                GlideApp.with(iv.getContext())
                        .load(item.getCompressPath())
                        .error(R.drawable.ic_img_error)
                        .centerCrop()
                        .transform(new GlideRoundTransform(5))
                        .into(iv);
                iv.setTag(item.getCompressPath());
            }
        }



    }
}
