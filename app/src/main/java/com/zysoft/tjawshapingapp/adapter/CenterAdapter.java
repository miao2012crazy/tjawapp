package com.zysoft.tjawshapingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.CenterToolBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class CenterAdapter extends BaseQuickAdapter<CenterToolBean,BaseViewHolder>{
    public CenterAdapter(List<CenterToolBean> mainList) {
        super(R.layout.item_center_1);
    }

    @Override
    protected void convert(BaseViewHolder helper, CenterToolBean item) {

    }
}
