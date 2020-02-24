package com.zysoft.tjawshapingapp.view;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.stx.xhb.xbanner.XBanner;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.ImageAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.DisplayUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.LogUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityDetailBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.ui.AmountView;
import com.zysoft.tjawshapingapp.ui.textfont.AppTextView;
import com.zysoft.tjawshapingapp.wxapi.WxShareUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

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
    private ProjectDetailBean.ProjectInfoBean projectInfo;
    private String project_id;
    private TimePickerDialog mDialogAll;
    private UserInfoBean userBean;
    private QMUIDialog.MessageDialogBuilder messageDialogBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        EventBus.getDefault().register(this);
        showTipe(2, "正在加载中...");
        setToolBar();
        initImgList();
        project_id = getIntent().getExtras().getString("PROJECT_ID");
        map.clear();
        map.put("projectId", project_id);
        NetModel.getInstance().getAllData("GETPROJECTDETAIL", HttpUrls.GET_PROJECT_DETAIL, map);

        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog(projectInfo);
            }
        });
        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoBean userInfoBean = AppConstant.getUserInfoBean();
                if (userInfoBean == null) {
                    return;
                }
//                if (!isSelect) {
                showBottomDialog(projectInfo);
//                }
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
        binding.btnImKf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connetKF();
            }
        });

//        binding.titleRightTv.setOnClickListener(v -> {
//            GlideApp.with(this).asBitmap().load(projectInfo.getProductIcon()).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                    //分享网页
//                    WxShareUtils.shareWeb({ProjectDetailActivity.this, "我在爱薇国际APP上看中了一个项目，分享给你看一看", projectInfo.getProjectName(), resource, projectInfo.getId());
//
//            });
//            })
        binding.titleRightTv.setOnClickListener(v -> {
            GlideApp.with(this).asBitmap().load(projectInfo.getProductIcon()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    //分享网页
                    WxShareUtils.shareWeb(ProjectDetailActivity.this, "我在爱薇国际APP上看中了一个项目，分享给你看一看", projectInfo.getProjectName(), resource, String.valueOf(projectInfo.getId()));
                }
            });
        });


        binding.layoutPl.expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });


        binding.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.layoutPl.expandableLayout0.isExpanded()) {
                    binding.layoutPl.tvMsgContent.setMaxLines(2);
                    binding.layoutPl.expandableLayout0.collapse();
                    binding.tvOpen.setText("查看全文");

                } else {
                    binding.layoutPl.tvMsgContent.setMaxLines(4);
                    binding.tvOpen.setText("收起");

                    binding.layoutPl.expandableLayout0.expand();
                }
            }
        });

        binding.llPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.clear();
                bundle.putString("project_id", project_id);
                startActivityBase(PLDetailActivity.class, bundle);
            }
        });
        initTimerDialog();

        binding.tvApplyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoBean userInfoBean = AppConstant.getUserInfoBean();
                if (userInfoBean == null) {
                    return;
                }
                //校验用户等级
                map.clear();
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                NetModel.getInstance().getDataFromNet("GET_USER", HttpUrls.GET_USER, map);


            }
        });
        binding.tvCouponsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstant.USER_INFO_BEAN == null) {
                    startActivityBase(LoginActivity.class);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initTimerDialog() {
        long tenYears = 30 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        time = millseconds;
                        String s = CommonUtil.ms2date("MM-dd HH:mm", millseconds);
                        tv_select_time.setText(s);
                    }
                })

                .setCancelStringId("取消")
                .setSureStringId("确认")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_app))
                .setWheelItemTextSize(12)
                .build();
    }

    @Subscribe
    public void receive(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GETPROJECTDETAIL":
                projectDetailBean = GsonUtil.GsonToBean((String) netResponse.getData(), ProjectDetailBean.class);
                projectInfo = projectDetailBean.getProjectInfo();
                initBanner(projectDetailBean.getLoop());
                binding.setItem(projectDetailBean.getProjectInfo());
                double projectEarnestMoney = projectDetailBean.getProjectInfo().getProjectEarnestMoney();
                double projectOrginPrice = projectDetailBean.getProjectInfo().getProjectOrginPrice();
                binding.tvPreparePay.setText("预付款" + projectDetailBean.getProjectInfo().getProjectEarnestMoney() + "元，到院再付尾款" + (projectOrginPrice - projectEarnestMoney) + "元");
                mainList.clear();
                List<ProjectDetailBean.ImgDetailBean> imgDetail = projectDetailBean.getImgDetail();
                mainList.addAll(imgDetail);
                if (imgDetail.size() == 0) {
                    binding.recyclerList.setVisibility(View.GONE);
                }

                imageAdapter.notifyDataSetChanged();

                //评价总数
                binding.tvPlCount.setText(projectDetailBean.getUserPLCount() == 0 ? "暂无评价" : projectDetailBean.getUserPLCount() + "个评价");
                if (projectDetailBean.getUserPLCount() == 0) {
                    binding.layoutPl.layoutPl.setVisibility(View.GONE);
                    binding.tvOpen.setVisibility(View.GONE);
                } else {
                    //评论信息
                    binding.layoutPl.setItem(projectDetailBean.getUserPL());
                }
                closeDialog();
                if (AppConstant.USER_INFO_BEAN == null) {
                    binding.tvCouponsCount.setText("登录后查看");
                    return;
                }
                map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
                map.put("projectId", projectInfo.getId());
                NetModel.getInstance().getAllData("COUPONS_PROJECT", HttpUrls.GETUSERCOUPONS, map);
                break;
            case "GET_USER":
                String data = (String) netResponse.getData();
                userBean = GsonUtil.GsonToBean(data, UserInfoBean.class);
                if (userBean.getUserLevel() != 0) {
                    showTipWhisBtn("提示", "您已经是会员，可以享受会员价格，付款时仍然原价支付，项目完成后由医院返还50%。").show();
                } else {
                    showAddDL();
                }
                break;
            case "COUPONS_PROJECT":
                List<CouponsBean> couponsBeans = GsonUtil.GsonToList((String) netResponse.getData(), CouponsBean.class);
                if (couponsBeans.size() != 0) {
                    binding.tvCouponsCount.setText("共" + couponsBeans.size() + " 张");
                } else {
                    binding.tvCouponsCount.setText("暂无优惠券");
                }
                break;
        }
    }


    public void showAddDL() {
        if (messageDialogBuilder != null) {
            messageDialogBuilder.show();
        } else {
            messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(ProjectDetailActivity.this)
                    .setTitle("提示")
                    .setMessage("加盟爱薇会员后即可开通!")
                    .addAction("联系客服", (dialog, index) -> {
                        dialog.dismiss();
                        connetKF();
                    })
                    .addAction("容我想想", (dialog, index) -> {
                        dialog.dismiss();
                    });
            messageDialogBuilder.show();
        }


    }


    private void connetKF() {
        if (AppConstant.APP_CONFIG_BEAN == null) {
            return;
        }
        Conversation conversation = Conversation.createSingleConversation(AppConstant.APP_CONFIG_BEAN.getKf());
        Intent intent6 = new Intent(ProjectDetailActivity.this, IMDetailActivity.class);
        bundle.clear();
        UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
        bundle.putString("recvUserName", targetInfo.getUserName());
        bundle.putString("recvNickName", targetInfo.getNickname());
        bundle.putString("recvUserAppkey", targetInfo.getAppKey());
        intent6.putExtras(bundle);
        startActivity(intent6);
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
        if (loop.size() == 0) {
            binding.banner.setVisibility(View.GONE);
        } else {
            binding.banner.setVisibility(View.VISIBLE);
        }
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
        AppTextView tv_or_price = view.findViewById(R.id.tv_or_price);
        AppTextView tv_member_price = view.findViewById(R.id.tv_member_price);
        AppTextView tv_prepare_price = view.findViewById(R.id.tv_prepare_price);
        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        tv_select_time = view.findViewById(R.id.tv_select_time);
        TextView tv_project_name = view.findViewById(R.id.tv_project_name);
        mAmountView.setGoods_storage(999);
        GlideApp.with(this).load(projectInfo.getProductIcon()).transform(new GlideRoundTransform(4)).into(imageView);
        tv_or_price.setText("¥" + projectInfo.getProjectOrginPrice());
        tv_member_price.setText("¥" + (projectInfo.getProjectOrginPrice() * 0.5));
        tv_prepare_price.setText("¥" + projectInfo.getProjectEarnestMoney());
        tv_project_name.setText(projectInfo.getProjectName());

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
        btn_confirm.setOnClickListener(v -> {
            if (time == 0) {
                showTipe(3, "请选择时间！");

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
        });
        tv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTIme();
            }
        });

    }

    private void selectTIme() {

        mDialogAll.show(getSupportFragmentManager(), "month_day_hour_minute");
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
                binding.tvReturn.setBackground(UIUtils.getDrawable(R.mipmap.ic_return_2));
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
                binding.tvReturn.setBackground(UIUtils.getDrawable(R.mipmap.ic_return_2));
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
