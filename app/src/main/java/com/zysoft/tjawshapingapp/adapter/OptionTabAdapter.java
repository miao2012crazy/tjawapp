package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

import java.util.List;

/**
 *
 * Created by mr.miao on 2019/7/29.
 */
public class OptionTabAdapter extends BaseQuickAdapter<HomeDataBean.OptionBean, BaseViewHolder> {
    public OptionTabAdapter(@Nullable List<HomeDataBean.OptionBean> data) {
        super(R.layout.item_option_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.OptionBean item) {
        helper.setText(R.id.tv_option_name, item.getOptionName());

        ImageView iv = helper.getView(R.id.iv_option);
//        iv.setTag(R.id.indexTag,item.getTag_drawable());
        if (!item.getOptionImg().equals(iv.getTag())){
            iv.setTag(null);
            GlideApp.with(iv.getContext())
                    .load(item.getOptionImg())
                    .error(R.drawable.ic_img_error)
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(iv);
            iv.setTag(item.getOptionImg());
        }
    }
}
