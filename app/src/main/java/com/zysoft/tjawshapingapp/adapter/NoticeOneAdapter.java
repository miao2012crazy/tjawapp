package com.zysoft.tjawshapingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.DataBean;

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

    }
}
