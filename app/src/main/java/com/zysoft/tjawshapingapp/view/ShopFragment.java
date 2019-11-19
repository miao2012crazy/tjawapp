package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.OptionTabAdapter;
import com.zysoft.tjawshapingapp.adapter.ProductAdapter;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.HomeDataBean;
import com.zysoft.tjawshapingapp.bean.ProductHomeBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentShopBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-06.
 */
public class ShopFragment extends CustomBaseFragment {

    private ViewDataBinding binding;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private List<ProductHomeBean.ProductListBean> projectListBeans = new ArrayList<>();

    private FragmentShopBinding bind;
    private ProductHomeBean homeDataBean;
    private ProductAdapter productAdapter;
    private int index=0;
    //0 刷新 1加载
    private boolean isRefresh=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);
        bind = (FragmentShopBinding) binding;
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initProduct();

        bind.ivRecomment1.setOnClickListener(v -> {
            if (homeDataBean != null) {
                List<ProductHomeBean.ProductRecommendBean> recommend = homeDataBean.getProductRecommend();
                if (recommend.size() != 0) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    bundle.clear();
                    bundle.putString("PRODUCT_ID", String.valueOf(recommend.get(0).getId()));

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bind.ivRecomment2.setOnClickListener(v -> {
            if (homeDataBean != null) {
                List<ProductHomeBean.ProductRecommendBean> recommend = homeDataBean.getProductRecommend();
                if (recommend.size() != 0) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    bundle.clear();
                    bundle.putString("PRODUCT_ID", String.valueOf(recommend.get(1).getId()));

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        bind.rlMsg.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ShopCartActivity.class);
            startActivity(intent);
        });
        bind.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                bind.smartRefresh.setEnableLoadMore(true);
                projectListBeans.clear();
                index=0;
                isRefresh=true;
                getData(index,isRefresh);
            }
        });
        bind.smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index=index+1;
                isRefresh=false;
                getData(index,isRefresh);
            }
        });
    }

    private void getData(int index, boolean isRefresh) {
        map.clear();
        map.put("isRefresh",isRefresh?0:1);
        map.put("index", index);
        NetModel.getInstance().getAllData("PRODUCT_HOME_DATA", HttpUrls.GET_PRODUCT_HOME_DATA, map);
    }

    private void initProduct() {
        productAdapter = new ProductAdapter(projectListBeans);
        productAdapter.setEmptyView(UIUtils.inflate(R.layout.layout_no_data));
        productAdapter.openLoadAnimation();

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        bind.recyclerOption.setLayoutManager(gridLayoutManager);
        bind.recyclerOption.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            bundle.clear();
            bundle.putString("PRODUCT_ID", String.valueOf(projectListBeans.get(position).getId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "PRODUCT_HOME_DATA":
                String data = (String) netResponse.getData();
                homeDataBean = GsonUtil.GsonToBean(data, ProductHomeBean.class);
                if (isRefresh){
                    initLoop(homeDataBean.getProductLoop());
                    initRecommendImage(homeDataBean.getProductRecommend(), homeDataBean.getGg());
                }
                if (homeDataBean.getProductList().size()==0){
                    bind.smartRefresh.setNoMoreData(true);
                    bind.smartRefresh.setEnableLoadMore(false);
                }
                if (bind.smartRefresh.isRefreshing()){
                    bind.smartRefresh.finishRefresh(true);
                }
                if (bind.smartRefresh.isLoading()){
                    bind.smartRefresh.finishLoadMore();
                }
                projectListBeans.addAll(homeDataBean.getProductList());
                productAdapter.notifyDataSetChanged();
                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }

    private void initRecommendImage(List<ProductHomeBean.ProductRecommendBean> recommend, ProductHomeBean.GgBean appDL) {
        if (recommend.size() == 1) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).centerCrop().transform(new GlideRoundTransform(4)).into(bind.ivRecomment1);
        } else if (recommend.size() >= 2) {
            GlideApp.with(this).load(recommend.get(0).getProductIcon()).transform(new GlideRoundTransform(4)).into(bind.ivRecomment1);
            GlideApp.with(this).load(recommend.get(1).getProductIcon()).into(bind.ivRecomment2);
        }
        GlideApp.with(this).load(appDL.getProjectImg()).transform(new GlideRoundTransform(4)).into(bind.ivRecomment3);


    }


//
//

    /**
     * List<HomeDataBean.LoopBean> loopBeans
     *
     * @param loop
     */
    private void initLoop(List<ProductHomeBean.ProductLoopBean> loop) {

        images.clear();
        titles.clear();
        for (ProductHomeBean.ProductLoopBean loopbean : loop) {
            images.add(loopbean.getLoopImgPath());
            titles.add("1");
        }
        if (bind.banner == null) {
            return;
        }
        titles.add("");
        bind.banner.removeAllViews();
        bind.banner.setData(images, titles);
        bind.banner.setOnItemClickListener((banner, position) -> {
            ProductHomeBean.ProductLoopBean loopBean = loop.get(position);
            switch (loopBean.getIsProduct()){
                case 0:
                    //0活动
                    bundle.clear();
                    bundle.putString("title", "官方活动");
                    bundle.putString("url", loopBean.getLoopLink());
                    startActivityBase(WebViewActivity.class, bundle);
                    break;
                case 1:
                    //1 商品
                    bundle.clear();
                    bundle.putString("PRODUCT_ID", loopBean.getProductId());
                    startActivityBase(ProductDetailActivity.class, bundle);
                    break;
                case 2:
                    // 2 项目
                    bundle.clear();
                    bundle.putString("PROJECT_ID", loopBean.getProductId());
                    startActivityBase(ProjectDetailActivity.class, bundle);

                    break;
            }
        });
        // XBanner适配数据
        bind.banner.setmAdapter((banner, view, position) -> GlideApp.with(ShopFragment.this).load(images.get(position)).error(R.mipmap.sample_add_dl).into((ImageView) view));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bind.smartRefresh.setEnableLoadMore(true);
        projectListBeans.clear();
        index=0;
        isRefresh=true;
        getData(index,isRefresh);
    }


}
