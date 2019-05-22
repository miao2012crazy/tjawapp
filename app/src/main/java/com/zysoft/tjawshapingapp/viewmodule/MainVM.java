package com.zysoft.tjawshapingapp.viewmodule;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.view.HomeFragment;
import com.zysoft.tjawshapingapp.view.CustomVideoFragment;
import com.zysoft.tjawshapingapp.view.IMFragment;
import com.zysoft.tjawshapingapp.view.UserCenterFragment;

/**
 * Created by mr.miao on 2019/3/31.
 */

public class MainVM {

    private final ActivityMainBinding mBind;

    public MainVM(ActivityMainBinding binding) {
        this.mBind = binding;
        initBottom();
    }

    private void initBottom() {
        mBind.bottomBar.setContainer(R.id.fl_container).setTitleBeforeAndAfterColor("#000000", "#ff758c")
                .addItem(HomeFragment.class,
                        "首页",
                        R.mipmap.ic_home_default,
                        R.mipmap.ic_home_check)
                .addItem(CustomVideoFragment.class,
                        "视频",
                        R.mipmap.ic_video_default,
                        R.mipmap.ic_video_check)
//                .addItem(HomeFragment.class,
//                        "商城",
//                        R.mipmap.ic_mall_default,
//                        R.mipmap.ic_mall_check)
                .addItem(IMFragment.class,
                        "消息",
                        R.mipmap.ic_notice_default,
                        R.mipmap.ic_notice_check)
                .addItem(UserCenterFragment.class,
                        "我的",
                        R.mipmap.ic_center_default,
                        R.mipmap.ic_center_check)
                .build();
    }
}
