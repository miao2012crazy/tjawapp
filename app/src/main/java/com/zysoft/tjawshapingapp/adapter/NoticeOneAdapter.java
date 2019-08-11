package com.zysoft.tjawshapingapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.DataBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class NoticeOneAdapter extends BaseQuickAdapter<DataBean,BaseViewHolder>{
    public NoticeOneAdapter(List<DataBean> mainList) {
        super(R.layout.item_gg,mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_reg_date,item.getRegDate())
                .setText(R.id.tv_desc,item.getDesc());

        ImageView view = helper.getView(R.id.iv_gg);
        if (!item.getPushIcon().equals(view.getTag())){
            view.setTag(null);
            GlideApp.with(view.getContext())
                    .load(item.getPushIcon())
                    .centerCrop()
                    .error(R.drawable.ic_img_error)
                    .transform(new GlideRoundTransform(4))
                    .into(view);
            view.setTag(item.getPushIcon());
        }

    }
}
