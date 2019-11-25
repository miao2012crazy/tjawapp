package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityRegeditBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.VerificationCodeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * Created by mr.miao on 2019/5/16.
 */
public class RegeditActivity extends CustomBaseActivity {
    private HashMap<String, Object> map = new HashMap<>();

    private String mCode = "";
    private String openId="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegeditBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_regedit);
        EventBus.getDefault().register(this);
        binding.tvTel.setText(AppConstant.USER_PHONE);
        binding.btnRegedit.setEnabled(false);
        binding.btnRegedit.setBackgroundResource(R.mipmap.btn_next_true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            openId = extras.getString("openId");
        }

        binding.verificationcodeview.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                if (TextUtils.isEmpty(content)) {
                    //TODO: 例如底部【下一步】按钮可点击
                    binding.btnRegedit.setEnabled(false);
                    binding.btnRegedit.setBackgroundResource(R.mipmap.btn_next_true);
                    return;
                }

                mCode = content;
                //TODO: 例如底部【下一步】按钮可点击
                binding.btnRegedit.setEnabled(true);
                binding.btnRegedit.setBackgroundResource(R.mipmap.btn_ok);
            }

        });

        binding.btnRegedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                map.clear();
                map.put("msgId", AppConstant.MsgId);
                map.put("code", mCode);
                NetModel.getInstance().getDataFromNet("CHECK_CODE", HttpUrls.CHECK_CODE,map);

            }
        });
    }

    @Subscribe
    public void receiveData(NetResponse netResponse){
        switch (netResponse.getTag()){
            case "CHECK_CODE":
                //成功！
                bundle.clear();
                bundle.putString("openId", openId);
                //用户不存在跳转到注册
                startActivityBase(SettingPsdActivity.class,bundle);
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


