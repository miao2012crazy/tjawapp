package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.CouponsBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class CouponsAdapter extends BaseQuickAdapter<CouponsBean,BaseViewHolder> {
    public CouponsAdapter(@Nullable List<CouponsBean> data) {
        super(R.layout.item_coupons,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsBean item) {
        helper.setText(R.id.textView6,item.getCouponsDesc())
        .setText(R.id.textView4,item.getCouponsName())
        .setBackgroundRes(R.id.iv_bg,item.getBg0())
        .setBackgroundRes(R.id.iv_bg_2,item.getBg1())
        .setText(R.id.tv_out_time,item.getOutTime())
        .setText(R.id.tv_btn_name,item.getBtnName());

    }
}
