package com.zysoft.tjawshapingapp.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.ProductHomeBean;
import com.zysoft.tjawshapingapp.common.DisplayUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.RoundedCornersTransformation;
import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class OptionProductAdapter extends BaseQuickAdapter<HomeDataBean.ProjectInfoBean,BaseViewHolder>{
    public OptionProductAdapter(List<HomeDataBean.ProjectInfoBean> mainList2) {
        super(R.layout.item_option_project,mainList2);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.ProjectInfoBean item) {
        helper.setText(R.id.tv_tag_name,item.getProjectName())
                .setText(R.id.tv_price,"¥"+ item.getProjectOrginPrice())
        .setText(R.id.textView2,String.valueOf(item.getProjectSellNum()));

        ImageView iv = helper.getView(R.id.iv_project);
//        iv.setTag(R.id.indexTag,item.getTag_drawable());
        if (!item.getProductIcon().equals(iv.getTag())){
            //顶部左边圆角
            RoundedCornersTransformation transformation = new RoundedCornersTransformation
                    (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
            //顶部右边圆角
            RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                    (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

            //组合各种Transformation,
            MultiTransformation<Bitmap> mation = new MultiTransformation<>
                    //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                    (new CenterCrop(), transformation, transformation1);


            iv.setTag(null);
            GlideApp.with(iv.getContext())
                    .load(item.getProductIcon())
                    .error(R.drawable.ic_img_error)
                    .centerCrop()
                    .transform(mation)
                    .into(iv);
            iv.setTag(item.getProductIcon());
        }
    }
}
