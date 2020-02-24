package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ImageAdapter;
import com.zysoft.tjawshapingapp.adapter.ImageDLAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.RealBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityDlShowBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-06.
 */
public class ApplyDLShowActivity extends CustomBaseActivity {
    private List<Integer> imgList = new ArrayList<>();
    private RealBean realBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_dl_show);
        ActivityDlShowBinding binding = (ActivityDlShowBinding) dataBinding;
        EventBus.getDefault().register(this);
        binding.title.qmTopBar.setTitle("加入爱薇");
        binding.title.qmTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        imgList.add(R.mipmap.a);
        imgList.add(R.mipmap.b);
        imgList.add(R.mipmap.c);
        imgList.add(R.mipmap.d);
        imgList.add(R.mipmap.e);
        imgList.add(R.mipmap.f);
        binding.recyclerImg.recyclerList.setLayoutManager(new LinearLayoutManager(this));
        ImageDLAdapter adapter = new ImageDLAdapter(imgList);
        binding.recyclerImg.recyclerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                switch (position) {
                    case 0:
                        break;



                    default:
                        if (realBean!=null){
                            if (realBean.getState()==0){
                                //未提交
                                QMUIDialog qmuiDialog = showTipWhisBtn("实名认证", "为了保证您的权益，请您提交实名认证信息").show();
                                qmuiDialog.setOnDismissListener(dialog -> {
                                    dialog.dismiss();
                                    startActivityBase(RealStateActivity.class);
                                });
                            }else if (realBean.getState()==3){
                                //审核失败
                                QMUIDialog qmuiDialog1 = showTipWhisBtn("实名认证", "您的实名认证信息未通过审核，为了保证您的权益，请您重新提交实名认证信息").show();
                                qmuiDialog1.setOnDismissListener(dialog -> {
                                    dialog.dismiss();
                                    startActivityBase(RealStateActivity.class);
                                });
                            }else {
                                startActivityBase(ApplyDLActivity.class);

                            }
                        }else {
                            startActivityBase(LoginActivity.class);
                        }

                        break;
                }
                finish();
            }
        });

    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_REAL_STATE":
                String data = (String) netResponse.getData();
                LogUtils.e(data);
                realBean = GsonUtil.GsonToBean(data, RealBean.class);

                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取用户实名认证状态
        map.clear();
        NetModel.getInstance().getDataFromNet("GET_REAL_STATE", HttpUrls.GET_REAL_STATE, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
