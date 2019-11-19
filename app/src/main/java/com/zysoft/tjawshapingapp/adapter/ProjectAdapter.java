package com.zysoft.tjawshapingapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class ProjectAdapter extends BaseQuickAdapter<HomeDataBean.ProjectListBean,BaseViewHolder>{
    public ProjectAdapter(List<HomeDataBean.ProjectListBean> mainList2) {
        super(R.layout.item_project,mainList2);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.ProjectListBean item) {
        helper.setText(R.id.tv_tag_name,item.getProjectTag()+item.getProjectName())
                .setText(R.id.tv_price,String.valueOf(item.getProjectOrginPrice()))
                .setText(R.id.tv_desc,String.valueOf(item.getProjectDesc()))
        .setText(R.id.textView2,String.valueOf(item.getProjectSellNum()));

        ImageView iv = helper.getView(R.id.iv_project);
//        iv.setTag(R.id.indexTag,item.getTag_drawable());
        if (!item.getProductIcon().equals(iv.getTag())){
            iv.setTag(null);
            GlideApp.with(iv.getContext())
                    .load(item.getProductIcon())
                    .error(R.drawable.ic_img_error)
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(iv);
            iv.setTag(item.getProductIcon());
        }


    }
}
