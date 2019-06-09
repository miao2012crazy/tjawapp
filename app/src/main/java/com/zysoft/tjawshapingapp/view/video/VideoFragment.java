package com.zysoft.tjawshapingapp.view.video;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zysoft.baseapp.commonUtil.LogUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.applaction.CustomApplaction;
import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.databinding.FmVideoBinding;
import com.zysoft.tjawshapingapp.view.ProjectDetailActivity;

import java.lang.reflect.Field;

import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_BEGIN;


/**
 * 作者： ch
 * 时间： 2018/7/30 0030-下午 2:55
 * 描述：
 * 来源：
 */


public class VideoFragment extends BaseLazyFragment {
    TXCloudVideoView txvVideo;
    RelativeLayout rlBackRight;
    DrawerLayout dlBackPlay;
    ImageView ivPlayThun;
    private TXVodPlayer mVodPlayer;
    private ProjectVideoBean projectVideoBean;
    public static final String URL = "URL";
    private String proxyUrl;
    private FmVideoBinding bind;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txvVideo = bind.txvVideo;
//        rlBackRight= bind.rlBackRight;
//        dlBackPlay= bind.dlBackPlay;
        ivPlayThun = bind.ivPlayThun;
        projectVideoBean = (ProjectVideoBean) getArguments().getSerializable(URL);
        bind.tvProjectName.setText(projectVideoBean.getVideoName());
        bind.tvDesc.setText(projectVideoBean.getVideoDesc());
        HttpProxyCacheServer proxy = CustomApplaction.getProxy(UIUtils.getContext());
        proxyUrl = proxy.getProxyUrl(projectVideoBean.getVideoPath());
        //创建player对象
        mVodPlayer = new TXVodPlayer(UIUtils.getContext());
//关键player对象与界面view
        mVodPlayer.setPlayerView(txvVideo);
        mVodPlayer.setAutoPlay(true);
//        url = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
        mVodPlayer.setLoop(true);
        mVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int i, Bundle bundle) {
                if (i == PLAY_EVT_PLAY_BEGIN) {
                    //开始播放
                    ivPlayThun.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(UIUtils.getContext()).load(proxyUrl).apply(requestOptions).into(ivPlayThun);


        bind.btnProjectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PROJECT_ID", String.valueOf(projectVideoBean.getProjectId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


//        Glide.with(UIUtils.getContext())
//                .load(proxyUrl)
//                .into(ivPlayThun);
//        dlBackPlay.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//
//            }
//
//            @Override
//            public void onDrawerOpened(@NonNull View drawerView) {
//                if (mVodPlayer != null) {
//                    mVodPlayer.pause();
//                }
//            }
//
//            @Override
//            public void onDrawerClosed(@NonNull View drawerView) {
//                if (mVodPlayer != null) {
//                    mVodPlayer.resume();
//                }
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        });
//
//
//        //设置菜单内容之外其他区域的背景色
//        dlBackPlay.setScrimColor(Color.TRANSPARENT);
//        //设置 全屏滑动
//        setDrawerRightEdgeSize(getActivity(), dlBackPlay, 1);
        LogUtils.e("预加载" + projectVideoBean.getVideoPath());


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fm_video, container, false);
        return bind.getRoot();
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();

        mVodPlayer.startPlay(proxyUrl);
    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();
        if (mVodPlayer != null) {
            mVodPlayer.pause();
        }
        LogUtils.e("video" + ":::pause();");
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (mVodPlayer != null) {
            mVodPlayer.resume();
        }
        LogUtils.e("video" + ":::onResume();");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("video" + isVisibleToUser);
        if (mVodPlayer == null) {
            return;
        }
        if (isVisibleToUser) {
            mVodPlayer.resume();
        } else {
            mVodPlayer.pause();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mVodPlayer != null) {
            // true代表清除最后一帧画面
            mVodPlayer.stopPlay(true);
        }
        if (txvVideo != null) {
            txvVideo.onDestroy();
        }
        LogUtils.e("video" + ":::onDestroy();");
    }


    /**
     * 设置 全屏滑动
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    private void setDrawerRightEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field mRightDragger =
                    drawerLayout.getClass().getDeclaredField("mRightDragger");//Right
            mRightDragger.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) mRightDragger.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }
}
