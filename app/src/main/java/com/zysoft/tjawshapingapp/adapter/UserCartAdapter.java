package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserCartBean;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.ui.AmountView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2019/3/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UserCartAdapter extends BaseQuickAdapter<UserCartBean, BaseViewHolder> {


    private final List<UserCartBean> selectData;

    public UserCartAdapter(@Nullable List<UserCartBean> data) {
        super(R.layout.item_user_cart, data);
        this.selectData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserCartBean item) {
        helper.setText(R.id.tv_product_title, item.getProductTitle()).setText(R.id.tv_product_desc, item.getProductDesc())
                .setText(R.id.tv_price, "¥ " + item.getProductOrignPrice()).addOnClickListener(R.id.iv_item)
                .setChecked(R.id.iv_item, item.isSelect());

        Glide.with(mContext)
                .load(item.getProductImg())
                .into((ImageView) helper.getView(R.id.tv_prouct_img));
        AmountView view = (AmountView) helper.getView(R.id.amount_view);
        view.setGoods_storage(100000);
        view.setAmount(item.getProductNum() + "");
        view.setOnAmountChangeListener((view1, amount) -> {
            if (item.getProductNum()==amount){
                return;
            }
            item.setProductNum(amount);
            EventBus.getDefault().post(new NetResponse("CART_NUM", amount + "/" + item.getId()));
        });
    }
}
