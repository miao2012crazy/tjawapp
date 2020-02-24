package com.zysoft.tjawshapingapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserCustomerBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-29.
 */
public class UserCustomerAdapter extends BaseQuickAdapter<UserCustomerBean.UserCustomerIncomBeansBean, BaseViewHolder> {
    public UserCustomerAdapter(List<UserCustomerBean.UserCustomerIncomBeansBean> mainList) {
        super(R.layout.item_user_customer,mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCustomerBean.UserCustomerIncomBeansBean item) {
        helper.setText(R.id.tv_nick_name,item.getUserNickName())
                .setText(R.id.tv_desc,item.getHistoryDesc())
                .setText(R.id.tv_date,"时间 "+item.getRegDate())
                .setText(R.id.tv_income," ¥ "+item.getReturnPrice())
                .setText(R.id.tv_price,"消费¥ "+item.getConPrice())
        .addOnClickListener(R.id.iv_im);

        ImageView view = helper.getView(R.id.iv_user_head);
        if (!item.getUserHeadImg().equals(view.getTag())){
            view.setTag(null);
            GlideApp.with(view.getContext())
                    .load(item.getUserHeadImg())
                    .centerCrop()
                    .transform(new GlideCircleTransform())
                    .into(view);
            view.setTag(item.getUserHeadImg());
        }
    }
}
