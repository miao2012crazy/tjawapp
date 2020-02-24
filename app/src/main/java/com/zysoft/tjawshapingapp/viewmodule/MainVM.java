package com.zysoft.tjawshapingapp.viewmodule;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.ActivityMainBinding;
import com.zysoft.tjawshapingapp.ui.BottomBar;
import com.zysoft.tjawshapingapp.view.HomeFragment;
import com.zysoft.tjawshapingapp.view.IMFragment;
import com.zysoft.tjawshapingapp.view.ShopFragment;
import com.zysoft.tjawshapingapp.view.UserCenterFragment;
import com.zysoft.tjawshapingapp.view.videonew.VideoFragment;

import org.greenrobot.eventbus.EventBus;

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
        mBind.bottomBar.setContainer(R.id.fl_container).setTitleBeforeAndAfterColor("#000000", "#0086c9")
                .addItem(HomeFragment.class,
                        "首页",
                        R.mipmap.ic_home_default,
                        R.mipmap.ic_home_check)
                .addItem(VideoFragment.class,
                        "视频",
                        R.mipmap.ic_video_default,
                        R.mipmap.ic_video_check)
//                .addItem(ShopFragment.class,
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
        mBind.bottomBar.setCheckedChangeListener(positon -> {
            EventBus.getDefault().post(new NetResponse("TAB_POSION",positon));
            EventBus.getDefault().post(new NetResponse("MSG",""));
        });
    }
}
