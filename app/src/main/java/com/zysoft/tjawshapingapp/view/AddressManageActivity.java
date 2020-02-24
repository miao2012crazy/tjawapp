package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.AddressAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
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
    private List<AddressBean> mainList = new ArrayList<>();
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        binding = (ActivityAddressBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
        if (userInfoBean==null){
            return;
        }
        map.put("userId", userInfoBean.getUserId());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(AddAddressActivity.class);
            }
        });
        binding.title.qmTopBar.setTitle("地址管理");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        
        initList();

    }

    private void initList() {
        addressAdapter = new AddressAdapter(mainList);
        addressAdapter.openLoadAnimation();
        addressAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerList.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.recyclerList.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    Intent intent = new Intent(AddressManageActivity.this, AddAddressActivity.class);
                    bundle.clear();
                    bundle.putSerializable("ADDRESS_BEAN",mainList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return;
                }
                String isSelect = extras.getString("isSelect");
                if (isSelect != null && isSelect.equalsIgnoreCase("1")) {
                    AppConstant.SELECT_ADDR=mainList.get(position);
                    setResult(999,getIntent());
                    finish();
                }


            }
        });
        addressAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AddressBean addressBean = mainList.get(position);
            UserInfoBean userInfoBean = AppConstant.USER_INFO_BEAN;
            if (userInfoBean==null){
                return;
            }
            switch (view.getId()){
                case R.id.tv_update:

                    bundle.clear();
                    bundle.putSerializable("ADDRESS_BEAN", addressBean);
                    startActivituCom(AddressManageActivity.this, AddAddressActivity.class, bundle);
                    break;
                case R.id.tv_delete:
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
                                    map.put("userId", userInfoBean.getUserId());
                                    map.put("addressId", addressBean.getId());
                                    map.put("type", "2");
                                    NetModel.getInstance().getDataFromNet("DELETE_ADDR_SUCCESS", HttpUrls.ADD_ADDR, map);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    break;
                case R.id.cb_check:
                    map.clear();
                    map.put("userId", userInfoBean.getUserId());
                    map.put("isDefault", addressBean.getIsDefault() == 0 ? "1" : "0");
                    map.put("type", "1");
                    map.put("addressId", addressBean.getId());
                    NetModel.getInstance().getDataFromNet("SET_DEFAULT_SUCCESS", HttpUrls.ADD_ADDR, map);
                    break;
            }
        });
    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "ADDRESS":
                String data = (String) netResponse.getData();
                List<AddressBean> addressBeans = GsonUtil.GsonToList(data, AddressBean.class);
                mainList.clear();
                mainList.addAll(addressBeans);
                addressAdapter.notifyDataSetChanged();
                break;
//            case "UPDATE_ADDR":
//                AddressBean addressBean = (AddressBean) netResponse.getData();
//                bundle.clear();
//                bundle.putSerializable("ADDRESS_BEAN", addressBean);
//                startActivituCom(AddressManageActivity.this, AddAddressActivity.class, bundle);
//                break;
//
//            case "DELETE_ADDR":
//                AddressBean addressBean1 = (AddressBean) netResponse.getData();
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setMessage("确定删除地址？")
//                        .addAction("取消", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .addAction("确定", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                map.clear();
//                                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
//                                map.put("addressId", addressBean1.getId());
//                                map.put("type", "2");
//                                NetModel.getInstance().getDataFromNet("DELETE_ADDR_SUCCESS", HttpUrls.ADD_ADDR, map);
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//                break;
//            case "SET_DEFAULT":
//                AddressBean addressBean2 = (AddressBean) netResponse.getData();
//                map.clear();
//                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
//                map.put("isDefault", addressBean2.getIsDefault() == 0 ? "1" : "0");
//                map.put("type", "1");
//                map.put("addressId", addressBean2.getId());
//                NetModel.getInstance().getDataFromNet("SET_DEFAULT_SUCCESS", HttpUrls.ADD_ADDR, map);
//                break;
            case "DELETE_ADDR_SUCCESS":
                NetModel.getInstance().getDataFromNet("ADDRESS", HttpUrls.GET_ADDRESS, map);
                showTipe(3,"删除成功！");
                break;
            case "SET_DEFAULT_SUCCESS":
                NetModel.getInstance().getDataFromNet("ADDRESS", HttpUrls.GET_ADDRESS, map);
                showTipe(3,"修改成功！");

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
