package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityAddressBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/27.
 */

public class AddressManageActivity extends CustomBaseActivity {

    private ActivityAddressBinding binding;
    private List<BindingAdapterItem> mainList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        binding = (ActivityAddressBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(AddAddressActivity.class);
            }
        });
        CustomTitleBean customTitleBean = new CustomTitleBean("地址管理", "", true, -1);
        binding.title.setItem(customTitleBean);
        binding.title.toolbar.setBackgroundColor(Color.WHITE);
        initTitle(binding.title.tvReturn, null);

    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "ADDRESS":
                String data = (String) netResponse.getData();
                List<AddressBean> addressBeans = GsonUtil.GsonToList(data, AddressBean.class);
                mainList.clear();
                mainList.addAll(addressBeans);
                setList_V(binding.recyclerList.recyclerList, mainList, handlerEvent, new BindingAdapter.CustomOnClickListener() {
                    @Override
                    public void onItemClick(BindingAdapterItem bindingAdapterItem) {

                    }
                });
                break;
            case "UPDATE_ADDR":
                AddressBean addressBean = (AddressBean) netResponse.getData();
                bundle.clear();
                bundle.putSerializable("ADDRESS_BEAN", addressBean);
                startActivituCom(AddressManageActivity.this, AddAddressActivity.class, bundle);
                break;

            case "DELETE_ADDR":
                AddressBean addressBean1 = (AddressBean) netResponse.getData();
                new QMUIDialog.MessageDialogBuilder(this)
                        .setMessage("确定删除地址？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                map.clear();
                                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                                map.put("addressId", addressBean1.getId());
                                map.put("type", "2");
                                NetModel.getInstance().getDataFromNet("DELETE_ADDR_SUCCESS", HttpUrls.ADD_ADDR, map);
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case "SET_DEFAULT":
                AddressBean addressBean2 = (AddressBean) netResponse.getData();
                map.clear();
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                map.put("isDefault", addressBean2.getIsDefault() == 0 ? "1" : "0");
                map.put("type", "1");
                map.put("addressId", addressBean2.getId());
                NetModel.getInstance().getDataFromNet("SET_DEFAULT_SUCCESS", HttpUrls.ADD_ADDR, map);
                break;
            case "DELETE_ADDR_SUCCESS":
                NetModel.getInstance().getDataFromNet("ADDRESS", HttpUrls.GET_ADDRESS, map);

                UIUtils.showToast("删除成功！");
                break;
            case "SET_DEFAULT_SUCCESS":
                NetModel.getInstance().getDataFromNet("ADDRESS", HttpUrls.GET_ADDRESS, map);

                UIUtils.showToast("修改成功！");
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        NetModel.getInstance().getDataFromNet("ADDRESS", HttpUrls.GET_ADDRESS, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
