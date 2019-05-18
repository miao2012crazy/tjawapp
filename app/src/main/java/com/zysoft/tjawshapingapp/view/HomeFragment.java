package com.zysoft.tjawshapingapp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.sunfusheng.marqueeview.MarqueeView;
import com.zysoft.app91.R;
import com.zysoft.app91.applaction.CustomApplaction;
import com.zysoft.app91.base.BaseFragment;
import com.zysoft.app91.base.BindingAdapter;
import com.zysoft.app91.base.BindingAdapterItem;
import com.zysoft.app91.bean.HomeDataBean;
import com.zysoft.app91.bean.LookNumBean;
import com.zysoft.app91.bean.TitleBean;
import com.zysoft.app91.commonUtil.GsonUtil;
import com.zysoft.app91.commonUtil.UIUtils;
import com.zysoft.app91.constant.AppConstant;
import com.zysoft.app91.constant.NetResponse;
import com.zysoft.app91.databinding.FragmentHomeBinding;
import com.zysoft.app91.httputil.HttpConstant;
import com.zysoft.app91.httputil.HttpUrl;
import com.zysoft.app91.model.NetModel;
import com.zysoft.app91.viewmodel.HomeVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/3/31.
 */

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding bind;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    protected List<BindingAdapterItem> mainList2 = new ArrayList<>();
    private NetModel netModel;
    private List<String> info = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        HomeVM homeVM = new HomeVM(bind);
        initTab();
        bind.tvKf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(getActivity(), KFActivity.class, null);
            }
        });
        bind.tvFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(getActivity(), FBTagActivity.class, null);
            }
        });
        netModel = new NetModel();
        map.clear();
        netModel.getHomeData(HttpConstant.GET_HOME_DATA, HttpUrl.GET_HOME_DATA, map);

        TitleBean titleBean = new TitleBean("首页", "", false);
        bind.title.setItem(titleBean);
        bind.tvLocaltion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress();
            }
        });
        bind.tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.indexRoot.fullScroll(View.FOCUS_UP);
            }
        });
        bind.tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(getActivity(), TVNewActivity.class, null);
            }
        });
        bind.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                map.clear();
                netModel.getHomeData(HttpConstant.GET_HOME_DATA, HttpUrl.GET_HOME_DATA, map);
                refreshLayout.finishRefresh();
            }
        });

    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(getActivity())
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
//                new_address_area.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                bind.tvLocaltion.setText(province + city + district);

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Subscribe
    public void receiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case HttpConstant.GET_HOME_DATA:
                HomeDataBean homeDataBean = GsonUtil.GsonToBean((String) netResponse.getData(), HomeDataBean.class);
                List<HomeDataBean.LoopListBean> loop = homeDataBean.getLoopList();
                initLoop(loop);
                initOption(homeDataBean.getTabOptionList());
                initList(homeDataBean.getMsgs());
                initGG(homeDataBean.getNoticeList(), homeDataBean.getAdList());
                netModel.getMsgs("GET_LOOK_NUm", HttpUrl.GET_LOOK_NUM, map);
                break;
            case HttpConstant.GET_MSG:
                List<HomeDataBean.MsgsBean> msgsBeans = GsonUtil.jsonToList((String) netResponse.getData(), HomeDataBean.MsgsBean.class);
                initList(msgsBeans);
                break;
            case "LOCATION":
                AMapLocation location = (AMapLocation) netResponse.getData();
                if (location != null) {
                    bind.tvLocaltion.setText(location.getCountry() + location.getCity());
                }
                break;
            case "GET_LOOK_NUm":
                String data = (String) netResponse.getData();
                Log.e("miao",data);

                List<LookNumBean> lookNumBeans = GsonUtil.jsonToList(data, LookNumBean.class);
                try {
                    bind.tvLookNum.setText(lookNumBeans.get(0).getLookNum() + "");

                } catch (Exception ex) {

                }


                break;
        }
    }


    /**
     * c初始化公告
     *
     * @param noticeList
     * @param adList
     */
    private void initGG(List<HomeDataBean.NoticeListBean> noticeList, List<HomeDataBean.AdListBean> adList) {

        info.clear();
        for (HomeDataBean.NoticeListBean item :
                noticeList) {
            info.add(item.getNoticeTitle());
        }

        bind.marqueeview.startWithList(info);
        bind.marqueeview.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                UIUtils.showToast(textView.getText().toString());
                AppConstant.NOTICE_TITLE=noticeList.get(position).getNoticeTitle();
                AppConstant.NOTICE_CONTENT=noticeList.get(position).getNoticeContent();
                startActivityBase(getActivity(), NoticeActivity.class, null);

            }
        });

        Glide.with(this).load(adList.get(0).getAdImg()).into(bind.ivGg);
    }

    /**
     * 初始化分类
     *
     * @param tabOptionList
     */
    private void initOption(List<HomeDataBean.TabOptionListBean> tabOptionList) {
        mainList2.clear();
        mainList2.addAll(tabOptionList);
        setList_H(bind.recyclerTag, mainList2, 5, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                HomeDataBean.TabOptionListBean tabOptionListBean = (HomeDataBean.TabOptionListBean) bindingAdapterItem;
                map.clear();
                map.put("option", tabOptionListBean.getId() + "");
                map.put("type", "0");
                netModel.getMsgs(HttpConstant.GET_MSG, HttpUrl.GET_MSG, map);
            }
        });
    }

    private void initList(List<HomeDataBean.MsgsBean> msgs) {
        mainList.clear();
        mainList.addAll(msgs);
        setList_V(bind.recyclerView, mainList, new BindingAdapter.CustomOnClickListener() {
            @Override
            public void onItemClick(BindingAdapterItem bindingAdapterItem) {
                AppConstant.MSG_BEAN = (HomeDataBean.MsgsBean) bindingAdapterItem;
                map.put("msgId", AppConstant.MSG_BEAN.getId());
                netModel.getMsgs("Http", HttpUrl.UPDATE_NUM, map);
                startActivityBase(getActivity(), MsgDetailActivity.class, null);
            }
        });
    }

    private void initTab() {
        bind.tabView.addTab(bind.tabView.newTab().setText("最新消息"));
        bind.tabView.addTab(bind.tabView.newTab().setText("顺风车"));
        bind.tabView.addTab(bind.tabView.newTab().setText("附近消息"));
        bind.tabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    map.clear();
                    map.put("mainClass", "1");
                    netModel.getMsgs(HttpConstant.GET_MSG, HttpUrl.GETMAINCLASS, map);
                } else {
                    map.clear();
                    netModel.getHomeData(HttpConstant.GET_HOME_DATA, HttpUrl.GET_HOME_DATA, map);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * List<HomeDataBean.LoopBean> loopBeans
     *
     * @param loop
     */
    private void initLoop(List<HomeDataBean.LoopListBean> loop) {
        XBanner xBanner = bind.banner;
        images.clear();
        titles.clear();
        for (HomeDataBean.LoopListBean loopbean : loop) {
            images.add(loopbean.getLoopImgPath());
            titles.add("1");
        }
        //images.add("https://98chuangyequan.oss-cn-shanghai.aliyuncs.com/images/QyyA7yKXas.jpg");
        // 为XBanner绑定数据
        if (xBanner == null) {
            return;
        }
        titles.add("");
        xBanner.removeAllViews();
        xBanner.setData(images, titles);
        // XBanner适配数据
        xBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(HomeFragment.this).load(images.get(position)).into((ImageView) view);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomApplaction.getmLocationClient().startLocation();
    }
}
