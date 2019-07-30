package com.zysoft.tjawshapingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/29.
 */

public class ProjectAdapter extends BaseQuickAdapter<HomeDataBean.ProjectListBean,BaseViewHolder>{
    public ProjectAdapter(List<HomeDataBean.ProjectListBean> mainList2) {
        super(R.layout.item_project,mainList2);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.ProjectListBean item) {

    }
}
