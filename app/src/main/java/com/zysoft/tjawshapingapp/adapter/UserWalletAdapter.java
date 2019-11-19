package com.zysoft.tjawshapingapp.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserWalletBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/8/15.
 */

public class UserWalletAdapter extends BaseQuickAdapter<UserWalletBean.HistoryBean,BaseViewHolder>{
    public UserWalletAdapter(List<UserWalletBean.HistoryBean> mainList) {
        super(R.layout.item_wallet_history,mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserWalletBean.HistoryBean item) {
            helper.setText(R.id.tv_desc,item.getTradingDesc()).setText(R.id.tv_reg_date,item.getRegDate())
                    .setText(R.id.tv_price,(item.getIsAdd()==0?"+":"-")+item.getTradingPrice()).setTextColor(R.id.tv_price,getTextColor(item));


    }

    private int getTextColor(UserWalletBean.HistoryBean item) {
        switch (item.getType()){
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.YELLOW;
        }
        return 0;
    }
}
