package com.zysoft.tjawshapingapp.handler;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.ui.FriendsCircleImageLayout;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by mr.miao on 2019/5/18.
 */

public class CustomHandlerEvent extends HandlerEvent {
    public CustomHandlerEvent(Context context) {
        super(context);
    }

//    @BindingAdapter({"getOrderState"})
//    public void getOrderState(TextView textView,OrderBean orderBean){
//        textView.setText(orderBean.getOrderState());
//    }


    public void updateAddr(AddressBean addressBean) {

        EventBus.getDefault().post(new NetResponse("UPDATE_ADDR", addressBean));

    }

    public void deleteAddr(AddressBean addressBean) {

        EventBus.getDefault().post(new NetResponse("DELETE_ADDR", addressBean));

    }

    public void setDefault(AddressBean addressBean) {

        EventBus.getDefault().post(new NetResponse("SET_DEFAULT", addressBean));

    }

    @BindingAdapter("setTitleName")
    public static void setTitleName(TextView textView, int state) {

        textView.setText(state == 0 ? "商品金额" : "项目金额");

    }


    @BindingAdapter("setPayWay")
    public static void setPayWay(TextView textView, int state) {
        switch (state) {
            case 0:
                textView.setText("微信支付");
                break;
            case 1:
                textView.setText("支付宝支付");
                break;
            case 2:
                textView.setText("银联支付");
                break;
            default:
                textView.setText("线下支付");
                break;
        }
    }


    @BindingAdapter("setFriendLayoutImg")
    public static void setFriendLayoutImg(FriendsCircleImageLayout friendsCircleImageLayout, List<ProjectDetailBean.UserPLBean.PlImgListBean> plImgListBeans) {
        if (plImgListBeans!=null&&plImgListBeans.size()!=0){
            friendsCircleImageLayout.setVisibility(View.VISIBLE);

            friendsCircleImageLayout.setImageUrls(plImgListBeans);
        }else {
            friendsCircleImageLayout.setVisibility(View.GONE);
        }


    }

}
