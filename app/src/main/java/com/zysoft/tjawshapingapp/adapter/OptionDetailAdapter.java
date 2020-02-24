package com.zysoft.tjawshapingapp.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/3.
 */

public class OptionDetailAdapter extends BaseQuickAdapter<HomeDataBean.OptionBean, BaseViewHolder> {
    public OptionDetailAdapter(@Nullable List<HomeDataBean.OptionBean> data) {
        super(R.layout.item_option_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.OptionBean item) {
        helper.setText(R.id.tv_option_name, item.getOptionName()).addOnClickListener(R.id.tv_option_name);
        TextView view = helper.getView(R.id.tv_option_name);
        view.setBackgroundColor((item.isCheck() ? Color.parseColor("#f5f5f5") : Color.parseColor("#ffffff")));
        view.setTextColor((item.isCheck() ? Color.parseColor("#0094de") : Color.parseColor("#333333")));
    }
}
