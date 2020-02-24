package com.zysoft.tjawshapingapp.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.bean.UserCustomerBean;
import com.zysoft.tjawshapingapp.bean.UserTeamBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;

import java.util.List;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019-11-29.
 */
public class UserTeamAdapter extends BaseQuickAdapter<UserTeamBean.TeamListBeansBean, BaseViewHolder> {
    public UserTeamAdapter(List<UserTeamBean.TeamListBeansBean> mainList) {
        super(R.layout.item_user_team, mainList);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserTeamBean.TeamListBeansBean item) {
        helper.setText(R.id.tv_nick_name, item.getUserNickName())
                .setText(R.id.tv_desc, "业绩奖励")
                .setText(R.id.tv_team_count, String.valueOf(item.getTeamCount()))
                .setText(R.id.tv_date, "时间：" + item.getRegDate())
                .setText(R.id.tv_income, " ¥ " + item.getTeamIncome())
                .setImageResource(R.id.iv_levels,  getUserLevel(item.getUserLevel()))
                .setText(R.id.tv_price, "消费¥ " + item.getUserConPrice())
//        .addOnClickListener(R.id.iv_im);
        ;

        ImageView view = helper.getView(R.id.iv_user_head);
        if (!item.getUserHeadImg().equals(view.getTag())) {
            view.setTag(null);
            GlideApp.with(view.getContext())
                    .load(item.getUserHeadImg())
                    .centerCrop()
                    .transform(new GlideCircleTransform())
                    .into(view);
            view.setTag(item.getUserHeadImg());
        }
    }

    private int getUserLevel(int userLevel) {
        switch (userLevel) {
            case 0:
                return R.mipmap.ic_level_0;
            case 1:
                return R.mipmap.ic_level_1;
            case 2:
                return R.mipmap.ic_level_2;
            case 3:
                return R.mipmap.ic_level_3;
                default:
                    return 0;
        }
    }
}
