package com.zysoft.tjawshapingapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;

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
        helper.setText(R.id.tv_project_name,item.getIsProduct()==1?item.getProjectName():item.getProjectName()+"--"+item.getProjectDesc())
                .setText(R.id.tv_count, item.getProjectNum() +"件")
                .setText(R.id.tv_time,item.getExpectTime())
                .setVisible(R.id.ll_time,item.getIsProduct()==1)
                .setText(R.id.tv_conpoun,String.valueOf(item.getOrderPayPrice()))
                .setText(R.id.tv_state_name,item.getOrderStateName())
                .setText(R.id.tv_yfk,item.getIsProduct()==1?"预付款(余款到店付)":"金额")
        .addOnClickListener(R.id.btn_cancel);

        ImageView view = helper.getView(R.id.iv_icon);
        if (!item.getProjectIcon().equals(view.getTag())){
            view.setTag(null);
            GlideApp.with(view.getContext())
                    .load(item.getProjectIcon())
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(view);
            view.setTag(item.getProjectIcon());
        }
        Button btnCancel = helper.getView(R.id.btn_cancel);
        Button btn_pay = helper.getView(R.id.btn_pay);
        Button btn_pj = helper.getView(R.id.btn_pj);
        Button btn_shipment = helper.getView(R.id.btn_shipment);
        Button btnConfirm = helper.getView(R.id.btnConfirm);

        btnCancel.setVisibility(item.getOrderState()==0?View.VISIBLE:View.GONE);
        btn_pay.setVisibility(item.getOrderState()==0?View.VISIBLE:View.GONE);
        btn_pj.setVisibility(item.getOrderState()==3?View.VISIBLE:View.GONE);
        btn_shipment.setVisibility((item.getOrderState()==6||item.getOrderState()==7)?View.VISIBLE:View.GONE);
        btnConfirm.setVisibility(item.getOrderState()==7?View.VISIBLE:View.GONE);

    }
}
