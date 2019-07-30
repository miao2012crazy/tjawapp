package com.zysoft.tjawshapingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.OrderBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderBean,BaseViewHolder>{
    public OrderAdapter(List<OrderBean> mainList) {
        super(R.layout.item_order,mainList);

    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {

    }
}
