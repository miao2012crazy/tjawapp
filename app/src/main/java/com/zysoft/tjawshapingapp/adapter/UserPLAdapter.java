package com.zysoft.tjawshapingapp.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.ui.FriendsCircleImageLayout;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-12-05.
 */
public class UserPLAdapter extends BaseQuickAdapter<ProjectDetailBean.UserPLBean, BaseViewHolder> {
    public UserPLAdapter(List<ProjectDetailBean.UserPLBean> mainList) {
        super(R.layout.item_msg_pl, mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetailBean.UserPLBean item) {
        ImageView ivHeader = helper.getView(R.id.iv_header);
        if (!item.getUserPl().getUserHeadImg().equals(ivHeader.getTag())) {
            ivHeader.setTag(null);
            GlideApp.with(ivHeader.getContext())
                    .load(item.getUserPl().getUserHeadImg())
                    .centerCrop()
                    .transform(new GlideCircleTransform())
                    .into(ivHeader);
            ivHeader.setTag(item.getUserPl().getUserHeadImg());
        }
        int length = item.getUserPl().getPlContent().length();

        helper.setText(R.id.tv_nick_name,item.getUserPl().getUserNickName())
                .setText(R.id.tv_reg_date, item.getUserPl().getRegDate())
        .setText(R.id.tv_msg_content,item.getUserPl().getPlContent())
        .setText(R.id.tv_msg_content_2,item.getUserPl().getPlContent())
        .setText(R.id.tv_project_name,"项目："+item.getUserPl().getProjectName())
        .setVisible(R.id.tv_open,length>=88).addOnClickListener(R.id.tv_open);
        if (item.getPlImgList()!=null&&item.getPlImgList().size()!=0){
            FriendsCircleImageLayout view = helper.getView(R.id.friends_circle_item_image_layout);
            if (item.getPlImgList().size()<3){
                view.setImageUrls(item.getPlImgList(),item.getPlImgList().size());
            }else {
                view.setImageUrls(item.getPlImgList(),3);

            }
        }
    }
}
