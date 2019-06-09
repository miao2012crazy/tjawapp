package com.zysoft.tjawshapingapp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zysoft.baseapp.base.BindingAdapterItem;
import com.zysoft.baseapp.commonUtil.GsonUtil;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.baseapp.constant.NetResponse;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.OrderBean;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.LayoutListBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2019/5/26.
 */

public class CouponsListFragment extends BaseLazyFragment {

    private List<BindingAdapterItem> mainList = new ArrayList<>();
    private String type;
    private LayoutListBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, R.layout.layout_list, container, false);
        bind = (LayoutListBinding) inflate;
        return inflate.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        type = getArguments().getString("type");
        map.put("userId", AppConstant.USER_INFO_BEAN.getUserId());
        map.put("type", type);
        map.put("index", "0");
        NetModel.getInstance().getDataFromNet("GET_COUPONS_LIST" + type, HttpUrls.GET_COUPONS_LIST, map);

    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();


    }


    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        if (netResponse.getTag().equals("GET_COUPONS_LIST" + type)) {

            List<CouponsBean> couponsBeans = GsonUtil.GsonToList((String) netResponse.getData(), CouponsBean.class);
            mainList.clear();
            mainList.addAll(couponsBeans);
            setList_V(bind.recyclerList, mainList, handlerEvent,bindingAdapterItem -> {
                CouponsBean couponsBean = (CouponsBean) bindingAdapterItem;
                if (couponsBean.getCouponsState() != 0) {
                    return;
                }
                switch (couponsBean.getType()) {

                    case 0:
                        QMUITipDialog qmuiTipDialog1 = new QMUITipDialog.Builder(getActivity()).setTipWord("全场商品下单即可使用！").create();
                        qmuiTipDialog1.setCanceledOnTouchOutside(true);
                        qmuiTipDialog1.show();
                        break;
                    case 1:
                        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("PROJECT_ID", couponsBean.getProjectId());
                        intent.putExtras(bundle);
                        startActivity(intent);

                        break;
                    case 2:
//                        QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(getActivity()).setTipWord("全场商品下单即可使用！").create();
//                        qmuiTipDialog2.setCanceledOnTouchOutside(true);
//                        qmuiTipDialog2.show();

                        break;
                    case 3:
                        QMUITipDialog qmuiTipDialog3 = new QMUITipDialog.Builder(getActivity()).setTipWord("全场项目和商品均可使用！").create();
                        qmuiTipDialog3.setCanceledOnTouchOutside(true);
                        qmuiTipDialog3.show();
                        break;
                    case 4:
                        Intent intent2 = new Intent(getActivity(), OptionActvity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("OPTION_ID", couponsBean.getOptionId());
                        bundle2.putString("OPTION_NAME", couponsBean.getOptionName());
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;
                    case 5:
                        QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(getActivity()).setTipWord("全场项目下单即可使用！").create();
                        qmuiTipDialog.setCanceledOnTouchOutside(true);
                        qmuiTipDialog.show();

                        break;

                }


            });
        }

    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }


}