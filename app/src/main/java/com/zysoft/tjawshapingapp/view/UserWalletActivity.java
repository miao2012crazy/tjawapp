package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.ActivityWalletBinding;

/**
 * Created by mr.miao on 2019/5/28.
 */

public class UserWalletActivity extends CustomBaseActivity{


    private ActivityWalletBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        binding = (ActivityWalletBinding) viewDataBinding;





    }


    @Override
    protected void onResume() {
        super.onResume();
//        setStatusBar("#00000000");
    }
}
