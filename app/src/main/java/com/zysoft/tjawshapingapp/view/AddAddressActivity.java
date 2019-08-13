package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityAddAddrBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by mr.miao on 2019/5/27.
 */

public class AddAddressActivity extends CustomBaseActivity {

    private QMUITipDialog.Builder builder = new QMUITipDialog.Builder(AddAddressActivity.this);

    private ActivityAddAddrBinding binding;
    private AddressBean address_bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_addr);
        binding = (ActivityAddAddrBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        binding.btnAdd.setOnClickListener(v -> {
            String etRecvName = binding.etRecvName.getText().toString();
            String etRecvTel = binding.etRecvTel.getText().toString();
            String etAddrA = binding.etAddrA.getText().toString();
            String etAddrB = binding.etAddrB.getText().toString();
            if (TextUtils.isEmpty(etRecvName)) {
                builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord("请输入收货人姓名").create().show();
            }
            if (TextUtils.isEmpty(etRecvTel)) {
                builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord("请输入收货人电话").create().show();
            }
            if (TextUtils.isEmpty(etAddrA)) {
                builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord("请输入所在区域").create().show();
            }
            if (TextUtils.isEmpty(etAddrB)) {
                builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord("请输入详细地址").create().show();
            }
            map.clear();

            map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
            map.put("recvName", etRecvName);
            map.put("recvTel", etRecvTel);
            map.put("addressClassA", etAddrA);
            map.put("addressClassB", etAddrB);
            map.put("isDefault", "0");
            map.put("detailAddr", etAddrA + etAddrB);
            map.put("type", "0");
            if (address_bean!=null){
                map.put("addressId", address_bean.getId());
                map.put("type", "1");
            }
            NetModel.getInstance().getDataFromNet("ADD_ADDR", HttpUrls.ADD_ADDR, map);

        });

        binding.title.qmTopBar.setTitle("新增地址");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());



        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable("ADDRESS_BEAN") != null) {
            address_bean = (AddressBean) extras.getSerializable("ADDRESS_BEAN");
        }
        if (address_bean != null) {
            binding.etRecvName.setText(address_bean.getRecvName());
            binding.etRecvTel.setText(address_bean.getRecvTel());
            binding.etAddrA.setText(address_bean.getAddressA());
            binding.etAddrB.setText(address_bean.getAddressB());
        }


    }


    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "ADD_ADDR":
//                QMUITipDialog qmuiTipDialog = builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("添加成功").create();
//                qmuiTipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        finish();
//                    }
//                });
//
//                qmuiTipDialog.show();
                if (address_bean!=null){
                    UIUtils.showToast("修改成功！");
                }else {
                    UIUtils.showToast("添加成功！");
                }
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
