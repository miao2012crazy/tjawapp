package com.zysoft.tjawshapingapp.view.videonew;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.VideoAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentVideoNewBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.ProjectDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;


/**
 * Created by mr.miao on 2019/8/11.
 */

public class VideoFragment extends CustomBaseFragment {


    private FragmentVideoNewBinding bind;

    private List<ProjectVideoBean> mDatas = new ArrayList<>();
    private VideoAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    private String TAG="video";
    private VideoView videoView;
    private boolean isPlaying=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_video_new, container, false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mLayoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);

        mAdapter = new VideoAdapter(mDatas);
        bind.recyclerList.recyclerList.setLayoutManager(mLayoutManager);
        bind.recyclerList.recyclerList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btn_project_detail:
                        int projectId = mDatas.get(position).getProjectId();
                        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("PROJECT_ID", String.valueOf(projectId));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });


        initListener();
        NetModel.getInstance().getDataFromNet("GET_VIDEO", HttpUrls.GET_VIDEO, map);
    }




    private void initListener() {
        mLayoutManager.setOnViewPagerListener(new com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                Log.e(TAG,"初始化完成");
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext,int position) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                Log.e(TAG,"选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                playVideo(0);
            }

        });
    }


    private void playVideo(int position) {
        View itemView = bind.recyclerList.recyclerList.getChildAt(0);
        videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final RelativeLayout rootView = itemView.findViewById(R.id.root_view);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                Log.e(TAG,"onInfo");
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(mp -> Log.e(TAG,"onPrepared"));


        imgPlay.setOnClickListener(v -> {
            isPlaying = true;
            if (videoView.isPlaying()){
                Log.e(TAG,"isPlaying:"+ videoView.isPlaying());
                imgPlay.animate().alpha(1f).start();
                videoView.pause();
                isPlaying = false;
            }else {
                Log.e(TAG,"isPlaying:"+ videoView.isPlaying());
                imgPlay.animate().alpha(0f).start();
                videoView.start();
                isPlaying = true;
            }
        });
    }


    private void releaseVideo(int index){
        View itemView = bind.recyclerList.recyclerList.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }



    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_VIDEO":
                String data = (String) netResponse.getData();
                List<ProjectVideoBean> projectVideoBeans = GsonUtil.GsonToList(data, ProjectVideoBean.class);
                mDatas.clear();
                mDatas.addAll(projectVideoBeans);
//                pagerAdapter.setUrlList(projectVideoBeans);
//                pagerAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                break;
            case "check":
                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (videoView!=null){
            videoView.start();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (videoView!=null){
            videoView.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}


