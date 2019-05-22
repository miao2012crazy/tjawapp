package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zysoft.baseapp.base.BaseActivity;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityConfirmOrderBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 * Created by mr.miao on 2019/5/19.
 */
public class ConfirmOrderActivity extends BaseActivity {


    private ProjectDetailBean.ProjectInfoBean projectInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConfirmOrderBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order);
        EventBus.getDefault().register(this);
        projectInfo = AppConstant.PROJECT_INFO;
        String product_num = getIntent().getExtras().getString("PRODUCT_NUM");
        binding.amountView.setAmount(product_num);
        binding.tvProjectName.setText(projectInfo.getProjectName());
        Glide.with(ConfirmOrderActivity.this).load(projectInfo.getProductIcon()).error(R.mipmap.sample_add_dl).into(binding.ivIcon);
        binding.tvProjectOption.setText(projectInfo.getProjectName());
        binding.tvPreparePrice.setText("¥"+projectInfo.getProjectEarnestMoney()+"");
        binding.tvPrice.setText("¥"+projectInfo.getProjectOrginPrice()+"");
        binding.tvPayPrice.setText("¥"+projectInfo.getProjectEarnestMoney());
        binding.amountView.setGoods_storage(999);


    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GETPROJECTDETAIL":

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
