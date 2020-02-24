package com.zysoft.tjawshapingapp.view.imglook;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.CustomImgAdapter;
import com.zysoft.tjawshapingapp.adapter.CustomPagerAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.databinding.ActivityImgLookBinding;
import com.zysoft.tjawshapingapp.view.imuisample.views.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-05.
 */
public class ImgLookActivity extends Activity {

    private ActivityImgLookBinding binding;
    private List<String> imgList;
    private String selectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_img_look);
        binding = (ActivityImgLookBinding) dataBinding;
        imgList = getIntent().getStringArrayListExtra("pathList");
        selectId = getIntent().getExtras().getString("selectId");
        binding.viewPager.setAdapter(new CustomImgAdapter(this, imgList));
        binding.viewPager.setCurrentItem(Integer.parseInt(selectId));
        binding.ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
