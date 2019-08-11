package com.zysoft.tjawshapingapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GlideUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */
public class Center2Adapter extends BaseMultiItemQuickAdapter<CenterToolBean, BaseViewHolder> {
    public Center2Adapter(List<CenterToolBean> mainList) {
        super(mainList);
        addItemType(CenterToolBean.center_0, R.layout.item_center_1);
        addItemType(CenterToolBean.center_1, R.layout.item_center_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, CenterToolBean item) {
        ImageView iv = helper.getView(R.id.iv_drawable);
        helper.setText(R.id.tv_tag_name, item.getTag_name());

        if (!UIUtils.getDrawable(item.getTag_drawable()).equals(iv.getTag())){
            iv.setTag(null);
            GlideApp.with(iv.getContext())
                    .load(UIUtils.getDrawable(item.getTag_drawable()))
                    .error(R.drawable.ic_img_error)
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(iv);
            iv.setTag(item.getTag_drawable());
        }




//        switch (helper.getItemViewType()) {
//            case CenterToolBean.center_0:
//
//                ImageView iv = helper.getView(R.id.iv_drawable);
//
//                break;
//            case CenterToolBean.center_1:
//                helper.setText(R.id.tv_tag_name, item.getTag_name());
//                GlideApp.with(UIUtils.getContext()).load(item.getTag_drawable()).into(iv);
//                break;
//        }


    }
}
