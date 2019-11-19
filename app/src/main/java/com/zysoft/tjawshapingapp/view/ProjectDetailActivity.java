package com.zysoft.tjawshapingapp.view;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.stx.xhb.xbanner.XBanner;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ImageAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.DisplayUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
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

public class ProjectDetailActivity extends CustomBaseActivity {
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<ProjectDetailBean.ImgDetailBean> mainList = new ArrayList<>();
    private ActivityDetailBinding binding;
    private ProjectDetailBean projectDetailBean;
    private boolean isSelect = false;
    private int mAmount;
    private TextView tv_select_time;
    private long time = 0;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        EventBus.getDefault().register(this);
        setToolBar();
        initImgList();
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

                }
//                else {
//                    Intent intent = new Intent(ProjectDetailActivity.this, ConfirmOrderActivity.class);
//                    Bundle bundle = new Bundle();
//                    ProjectDetailBean.ProjectInfoBean projectInfo = projectDetailBean.getProjectInfo();
//                    AppConstant.PROJECT_INFO = projectInfo;
//                    bundle.putString("PRODUCT_NUM", String.valueOf(mAmount));
//                    intent.putExtras(bundle);
//                    //确认订单
//                    startActivity(intent);
//                }
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
                double projectEarnestMoney = projectDetailBean.getProjectInfo().getProjectEarnestMoney();
                double projectOrginPrice = projectDetailBean.getProjectInfo().getProjectOrginPrice();
                binding.tvPreparePay.setText("预付款" + projectDetailBean.getProjectInfo().getProjectEarnestMoney() + "元，到院再付尾款" + (projectOrginPrice - projectEarnestMoney) + "元");
                mainList.clear();
                mainList.addAll(projectDetailBean.getImgDetail());
                imageAdapter.notifyDataSetChanged();
                break;

        }
    }


    private void initImgList() {
        imageAdapter = new ImageAdapter(mainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.recyclerList.setLayoutManager(linearLayoutManager);
        binding.recyclerList.setAdapter(imageAdapter);
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
                GlideApp.with(ProjectDetailActivity.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view);
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
        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        tv_select_time = view.findViewById(R.id.tv_select_time);
        TextView tv_tag = view.findViewById(R.id.tv_tag);
        TextView tv_project_name = view.findViewById(R.id.tv_project_name);
        mAmountView.setGoods_storage(999);
        GlideApp.with(this).load(projectInfo.getProductIcon()).transform(new GlideRoundTransform(4)).into(imageView);
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

        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time == 0) {
                    UIUtils.showToast("请选择时间！");
                    return;
                }
                isSelect = true;
                mAmount = mAmountView.getAmount();
                binding.tvSelect.setText("已选：" + mAmountView.getAmount() + "件服务");
                //跳转
                Intent intent = new Intent(ProjectDetailActivity.this, ConfirmOrderActivity.class);
                bundle.clear();
                bundle.putString("time", String.valueOf(time));
                bundle.putString("count", mAmount + "");
                bundle.putSerializable("project", projectDetailBean.getProjectInfo());
                intent.putExtras(bundle);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        tv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTIme();
            }
        });

    }

    private void selectTIme() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time = date.getTime();
                String s = CommonUtil.ms2date("MM-dd HH:mm", date.getTime());
                tv_select_time.setText(s);
            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
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
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }


    /**
     * 初始化setToolBar
     */
    private void setToolBar() {
        setSupportActionBar(binding.toolbaretail);
        binding.toolbaretail.setTitleTextColor(Color.BLACK);
        binding.toolbarLayout.setTitleEnabled(false);
        binding.toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);//设置展开后标题的位置
        binding.toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
//        binding.toolbarLayout.setExpandedTitleColor(Color.BLACK);//设置展开后标题的颜色
//        binding.toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后标题的颜色
        binding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //verticalOffset  当前偏移量 appBarLayout.getTotalScrollRange() 最大高度 便宜值
            int Offset = Math.abs(verticalOffset); //目的是将负数转换为绝对正数；
            //标题栏的渐变
            binding.toolbaretail.setBackgroundColor(changeAlpha(getResources().getColor(R.color.white)
                    , Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));

            /**
             * 当前最大高度便宜值除以2 在减去已偏移值 获取浮动 先显示在隐藏
             */
            if (Offset < appBarLayout.getTotalScrollRange() / 2) {
                binding.toolbaretail.setTitle("");
                binding.titleName.setText("");
                binding.titleName.setTextColor(Color.parseColor("#ffffff"));
                binding.toolbaretail.setAlpha((appBarLayout.getTotalScrollRange() / 2 - Offset * 1.0f) / (appBarLayout.getTotalScrollRange() / 2));
//                binding.toolbaretail.setNavigationIcon(R.mipmap.ic_return);
                binding.tvReturn.setBackground(UIUtils.getDrawable(R.mipmap.ic_return));
                binding.titleRightTv.setBackground(UIUtils.getDrawable(R.mipmap.icon_share));

                /**
                 * 从最低浮动开始渐显 当前 Offset就是  appBarLayout.getTotalScrollRange() / 2
                 * 所以 Offset - appBarLayout.getTotalScrollRange() / 2
                 */
            } else if (Offset > appBarLayout.getTotalScrollRange() / 2) {
                float floate = (Offset - appBarLayout.getTotalScrollRange() / 2) * 1.0f / (appBarLayout.getTotalScrollRange() / 2);
                binding.toolbaretail.setAlpha(floate);
                binding.toolbaretail.setTitle("");
                binding.titleName.setText("商品详情");
//                binding.toolbaretail.setNavigationIcon(R.mipmap.ic_return);
                binding.tvReturn.setBackground(UIUtils.getDrawable(R.mipmap.ic_return));
                binding.titleRightTv.setBackground(UIUtils.getDrawable(R.mipmap.icon_share));
                binding.titleName.setTextColor(Color.parseColor("#333333"));
                binding.toolbaretail.setAlpha(floate);
                binding.titleRightTv.setTextColor(Color.parseColor("#333333"));
            }
        });
        binding.tvReturn.setOnClickListener(v -> finish());
    }


    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, 255, 255, 255);
    }

}
