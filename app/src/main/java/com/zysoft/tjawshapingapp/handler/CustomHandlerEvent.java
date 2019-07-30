package com.zysoft.tjawshapingapp.handler;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.constants.NetResponse;

import org.greenrobot.eventbus.EventBus;

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



    public void updateAddr(AddressBean addressBean){

        EventBus.getDefault().post(new NetResponse("UPDATE_ADDR",addressBean));

    }
   public void deleteAddr(AddressBean addressBean){

        EventBus.getDefault().post(new NetResponse("DELETE_ADDR",addressBean));

    }

  public void setDefault(AddressBean addressBean){

        EventBus.getDefault().post(new NetResponse("SET_DEFAULT",addressBean));

    }


}
