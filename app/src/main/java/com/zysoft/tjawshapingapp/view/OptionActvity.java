package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ProjectAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CustomTitleBean;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityOptionBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by mr.miao on 2019/5/25.
 */

public class OptionActvity extends CustomBaseActivity {

    private ActivityOptionBinding binding;
    private List<HomeDataBean.ProjectListBean> mainList2 = new ArrayList<>();
    private ProjectAdapter projectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_option);
        EventBus.getDefault().register(this);
        map.clear();
        initList();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String option_id1 = extras.getString("OPTION_ID");
        if (TextUtils.isEmpty(option_id1)) {
            HomeDataBean.OptionBean option_id = (HomeDataBean.OptionBean) getIntent().getExtras().getSerializable("OPTION_ID");
            CustomTitleBean customTitleBean = new CustomTitleBean(option_id.getOptionName(), "", true, -1);
            binding.title.setItem(customTitleBean);
            binding.title.toolbar.setBackgroundColor(Color.WHITE);
//            initTitle(binding.title.tvReturn, null);
            map.put("option", option_id.getId());
            map.put("pageIndex", "0");
            NetModel.getInstance().getDataFromNet("OPTION_DATA", HttpUrls.GET_OPTION_DATA, map);
        }else {
            String option_name = extras.getString("OPTION_NAME");
            CustomTitleBean customTitleBean = new CustomTitleBean(option_name, "", true, -1);
            binding.title.setItem(customTitleBean);
            binding.title.toolbar.setBackgroundColor(Color.WHITE);
//            initTitle(binding.title.tvReturn, null);
            map.put("option", option_id1);
            map.put("pageIndex", "0");
            NetModel.getInstance().getDataFromNet("OPTION_DATA", HttpUrls.GET_OPTION_DATA, map);
        }


    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        projectAdapter = new ProjectAdapter(mainList2);
        binding.recyclerList.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.recyclerList.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeDataBean.ProjectListBean bindingAdapterItem1 = mainList2.get(position);
                Intent intent = new Intent(OptionActvity.this, ProjectDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PROJECT_ID", String.valueOf(bindingAdapterItem1.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "OPTION_DATA":
                String data = (String) netResponse.getData();
                List<HomeDataBean.ProjectListBean> projectListBean = GsonUtil.GsonToList(data, HomeDataBean.ProjectListBean.class);

                initProject(projectListBean);
                break;

        }
    }


    private void initProject(List<HomeDataBean.ProjectListBean> projectList) {
        mainList2.clear();
        mainList2.addAll(projectList);
        projectAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
