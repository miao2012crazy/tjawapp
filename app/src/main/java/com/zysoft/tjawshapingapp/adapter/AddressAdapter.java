package com.zysoft.tjawshapingapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.AddressBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/7/26.
 */

public class AddressAdapter extends BaseQuickAdapter<AddressBean,BaseViewHolder>{

    public AddressAdapter(@Nullable List<AddressBean> data) {
        super(R.layout.item_address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.tv_recvName,item.getRecvName())
        .setText(R.id.tv_detail_addr,item.getDetailAddr())
        .setText(R.id.tv_recv_tel,item.getRecvTel())
        .setChecked(R.id.cb_check,item.getIsDefault()==1);
    }
}
