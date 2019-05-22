package com.zysoft.tjawshapingapp.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.zysoft.baseapp.base.BaseActivity;
import com.zysoft.baseapp.base.BindingAdapter;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GlideRoundTransform;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.DeviceUtils;
import com.zysoft.tjawshapingapp.common.DisplayUtil;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityDetailBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.AmountView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/18.
 */

public class ProjectDetailActivity extends BaseActivity {
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<BindingAdapterItem> mainList = new ArrayList<>();
    private ActivityDetailBinding binding;
    private ProjectDetailBean projectDetailBean;
    private boolean isSelect = false;
    private int mAmount;
    private TextView tv_select_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        EventBus.getDefault().register(this);
        setStatusBar("#00000000");
        String project_id = getIntent().getExtras().getString("PROJECT_ID");
        map.clear();
        map.put("projectId", project_id);
        NetModel.getInstance().getAllData("GETPROJECTDETAIL", HttpUrls.GET_PROJECT_DETAIL, map);
        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog(projectDetailBean.getProjectInfo());
            }
        });
        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelect) {
                    showBottomDialog(projectDetailBean.getProjectInfo());

                } else {
                    Intent intent = new Intent(ProjectDetailActivity.this, ConfirmOrderActivity.class);
                    Bundle bundle = new Bundle();
                    ProjectDetailBean.ProjectInfoBean projectInfo = projectDetailBean.getProjectInfo();
                    AppConstant.PROJECT_INFO = projectInfo;
                    bundle.putString("PRODUCT_NUM", String.valueOf(mAmount));
                    intent.putExtras(bundle);
                    //确认订单
                    startActivity(intent);
                }
            }
        });

    }


    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GETPROJECTDETAIL":
                projectDetailBean = GsonUtil.GsonToBean((String) netResponse.getData(), ProjectDetailBean.class);
                initBanner(projectDetailBean.getLoop());
                binding.setItem(projectDetailBean.getProjectInfo());
                int projectEarnestMoney = projectDetailBean.getProjectInfo().getProjectEarnestMoney();
                int projectOrginPrice = projectDetailBean.getProjectInfo().getProjectOrginPrice();
                binding.tvPreparePay.setText("预付款" + projectDetailBean.getProjectInfo().getProjectEarnestMoney() + "元，到院再付尾款" + (projectOrginPrice - projectEarnestMoney) + "元");
                initImgList(projectDetailBean.getImgDetail());

                break;

        }
    }

    private void initImgList(List<ProjectDetailBean.ImgDetailBean> imgDetail) {
        mainList.clear();
        mainList.addAll(imgDetail);
        setList_V(binding.recyclerList, mainList, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {

            }
        });


    }

    private void initBanner(List<ProjectDetailBean.LoopBean> loop) {
        images.clear();
        titles.clear();
        for (ProjectDetailBean.LoopBean loopbean : loop) {
            images.add(loopbean.getImgPath());
            titles.add("1");
        }
        if (binding.banner == null) {
            return;
        }
        titles.add("");
        binding.banner.removeAllViews();
        binding.banner.setData(images, titles);
        // XBanner适配数据
        binding.banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(ProjectDetailActivity.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void showBottomDialog(ProjectDetailBean.ProjectInfoBean projectInfo) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_custom_layout, null);
        dialog.setContentView(view);

        AmountView mAmountView = view.findViewById(R.id.amountView);
        ImageView imageView = view.findViewById(R.id.iv_icon);
        TextView tv_or_price = view.findViewById(R.id.tv_or_price);
        TextView tv_member_price = view.findViewById(R.id.tv_member_price);
        TextView tv_prepare_price = view.findViewById(R.id.tv_prepare_price);
        tv_select_time = view.findViewById(R.id.tv_select_time);
        TextView tv_tag = view.findViewById(R.id.tv_tag);
        TextView tv_project_name = view.findViewById(R.id.tv_project_name);
        mAmountView.setGoods_storage(999);
        Glide.with(this).load(projectInfo.getProductIcon()).transform(new GlideRoundTransform(UIUtils.getContext(), 4)).into(imageView);
        tv_or_price.setText("¥" + projectInfo.getProjectOrginPrice());
        tv_member_price.setText("¥" + projectInfo.getProjectMemberPrice());
        tv_prepare_price.setText("¥" + projectInfo.getProjectEarnestMoney());
        tv_tag.setText("会员价");
        tv_project_name.setText(projectInfo.getProjectName());
        mAmountView.setOnAmountChangeListener((view1, amount) -> UIUtils.showToast(amount + ""));

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(UIUtils.getContext(), 400));
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            isSelect = true;
            mAmount = mAmountView.getAmount();
            binding.tvSelect.setText("已选：" + mAmountView.getAmount() + "件服务");
        });

        tv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTIme();

            }
        });
//        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

    }

    private void selectTIme() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_select_time.setText(date.getTime() + "");
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

}
