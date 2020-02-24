package com.zysoft.tjawshapingapp.view.videonew;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.adapter.VideoAdapter;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;
import com.zysoft.tjawshapingapp.bean.ProjectVideoBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.LogUtils;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.databinding.FragmentVideoNewBinding;
import com.zysoft.tjawshapingapp.http.HttpUrls;
import com.zysoft.tjawshapingapp.module.NetModel;
import com.zysoft.tjawshapingapp.view.ProjectDetailActivity;
import com.zysoft.tjawshapingapp.view.webView.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


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
    private int position=0;
    private int index=0;
    private int startPosition;

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
                        ProjectVideoBean projectVideoBean = mDatas.get(position);
                        if (projectVideoBean.getType()==0){
                            //活动
                            if (TextUtils.isEmpty(projectVideoBean.getLink())){
                                return;
                            }
                            //用户注册协议
                            bundle.clear();
                            bundle.putString("title", "官方活动");
                            bundle.putString("url", projectVideoBean.getLink());
                            startActivityBase(WebViewActivity.class, bundle);
                        }
                        if (projectVideoBean.getType()==1){
                            //商品
                        }
                        if (projectVideoBean.getType()==2){
                            //项目
                            if (projectVideoBean.getProjectId()==0){
                                return;
                            }
                            Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("PROJECT_ID", String.valueOf(projectVideoBean.getProjectId()));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        int projectId = projectVideoBean.getProjectId();
                        if (projectId!=0){

                        }
                        break;
                }
            }
        });


        initListener();
        bind.smartRefresh.setOnRefreshListener(refreshLayout -> {
            startPosition =0;
            refreshLayout.setNoMoreData(false);
            mDatas.clear();
            getData(0);



        });
        bind.smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            startPosition = mDatas.size();
            index=index+1;
            getData(index);
        });

        getData(0);

    }

    private void getData(int index) {
        map.put("index",index);
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
                int index;
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
        videoView.setOnInfoListener((mp, what, extra) -> {
            mediaPlayer[0] = mp;
            Log.e(TAG,"onInfo");
            mp.setLooping(true);
            imgThumb.animate().alpha(0).setDuration(200).start();
            return false;
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

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.w("页面回显");
    }




    @Subscribe
    public void revceiveData(NetResponse netResponse) {
        switch (netResponse.getTag()) {
            case "GET_VIDEO":

                String data = (String) netResponse.getData();
                List<ProjectVideoBean> projectVideoBeans = GsonUtil.GsonToList(data, ProjectVideoBean.class);
                if (bind.smartRefresh.isRefreshing()){
                    bind.smartRefresh.finishRefresh();
                }
                if (bind.smartRefresh.isLoading()){
                    if (projectVideoBeans.size()==0){
                        bind.smartRefresh.finishLoadMoreWithNoMoreData();
                    }else {
                        bind.smartRefresh.finishLoadMore(true);
                    }
                }
                mDatas.addAll(projectVideoBeans);
//                pagerAdapter.setUrlList(projectVideoBeans);
//                pagerAdapter.notifyDataSetChanged();
                mAdapter.notifyItemRangeChanged(startPosition,projectVideoBeans.size());
                break;
            case "check":
//                UIUtils.showToast(String.valueOf(netResponse.getData()));
                break;
            case "TAB_POSION":

                Object data1 = netResponse.getData();
                position = Integer.parseInt(String.valueOf(data1));
                if (String.valueOf(data1).equals("1")){
                    //点击的1
                    if (videoView!=null){
                        videoView.start();
                    }
                }else {
                    //非1 暂停视频
                    if (videoView!=null){
                        videoView.pause();
                    }
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.w("页面可见");
        if (videoView!=null&&position==1){
            videoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.w("页面不可见");
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


