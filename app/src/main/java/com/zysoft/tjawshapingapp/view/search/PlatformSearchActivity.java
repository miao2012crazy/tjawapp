package com.zysoft.tjawshapingapp.view.search;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.databinding.ActivityPlatformSearchBinding;
import com.zysoft.tjawshapingapp.gen.SearchBeanDao;

import java.util.List;

import static com.qmuiteam.qmui.util.QMUIDisplayHelper.dpToPx;

/**
 * Created by Xia_焱 on  2019/9/11.
 * 邮箱：XiahaotianV@163.com
 * 平台搜索
 */
public class PlatformSearchActivity extends CustomBaseActivity {


    private ActivityPlatformSearchBinding binding;
    private String[] strArr = {"鼻部", "眼睛", "丰胸"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_platform_search);
        binding = (ActivityPlatformSearchBinding) viewDataBinding;

        binding.search.ivBackK.setOnClickListener(v -> finish());
        for (int i = 0; i < strArr.length; i++) {
            TextView textView = buildLabel(strArr[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(Color.parseColor("#666666"));
            binding.flTj.addView(textView);
        }
        initLs();
        binding.search.tvSearchGo.setOnClickListener(v -> {
            String s = binding.search.etContext.getText().toString();
            if (TextUtils.isEmpty(s)){
                showTipe(0,"请输入搜索关键字");
                return;
            }
            initLs();
            SearchBean searchBean = new SearchBean(null, s);
            SearchBeanDao searchBeanDao = CustomApplaction.getSession().getSearchBeanDao();
            searchBeanDao.queryBuilder().where(SearchBeanDao.Properties.SerarchValue.eq(s)).buildDelete().executeDeleteWithoutDetachingEntities();
            searchBeanDao.insert(searchBean);
            AppConstant.SEARCH_VALUE=s;
            initLs();
            startActivityBase(SearchResultActivity.class);

        });
        binding.ivDelK.setOnClickListener(v -> {
            SearchBeanDao searchBeanDao = CustomApplaction.getSession().getSearchBeanDao();
            searchBeanDao.deleteAll();
            initLs();
        });


    }

    private TextView buildLabel(final String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding((int) dpToPx(16), (int) dpToPx(8), (int) dpToPx(16), (int) dpToPx(8));
        textView.setBackgroundResource(R.drawable.bg_gray);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.search.etContext.setText(text);
            }
        });
        return textView;
    }



    private void  initLs(){
        List<SearchBean> list = CustomApplaction.getSession().queryBuilder(SearchBean.class).orderDesc(SearchBeanDao.Properties.Id).offset(0).limit(10).list();
        binding.flLs.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            TextView textView = buildLabel(list.get(i).getSerarchValue());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(Color.parseColor("#666666"));
            binding.flLs.addView(textView);
        }
    }

}
