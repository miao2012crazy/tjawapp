package com.zysoft.tjawshapingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.view.imglook.ImgLookActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sqzglg on 2017/10/10.
 * 仿微信朋友圈图片展示
 */

public class FriendsCircleImageLayout extends ViewGroup {
    /**
     * 显示的行数
     */
    private int mColumnCount;
    /**
     * 默认间距
     */
    private final float DEFAULT_SPACING = 10.0f;
    private float mSpacing;
    /**
     * 图片宽高比(党为多张图片的时候为1)
     * 一般在url中会有宽高 可计算
     */
    private float mItemAspectRatio;
    /**
     * 最宽的时候相对可用空间比例（当childCount=1的时候）
     * 当只有一张图片的 最大显示宽度和高度相对于可用宽的的比例
     */
    private final float MAX_WIDTH_PERCENTAGE = 270f / 350;

    private int mItemWidth;
    private int mItemHeight;
    private ArrayList<String> imgList = new ArrayList<>();


    public FriendsCircleImageLayout(Context context) {
        this(context, null);
    }

    public FriendsCircleImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendsCircleImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SPACING,
                context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        final int width = MeasureSpec.getSize(widthMeasureSpec);

        //当只有一张图片的时候 显示一张相对比较大的图片
        //当大于1小于等于4张图片的时候 一排显示两张图片

        //当只有一张图片的时候
        if (count == 1) {
            mColumnCount = 1;
            int mItemMaxWidth = (int) (width * MAX_WIDTH_PERCENTAGE);
            int mItemMaxHeight = mItemMaxWidth;
            if (mItemAspectRatio < 1) {
                mItemHeight = mItemMaxHeight;
                mItemWidth = (int) (mItemHeight * mItemAspectRatio);
            } else {
                mItemWidth = mItemMaxWidth;
                mItemHeight = (int) (mItemMaxWidth / mItemAspectRatio);
            }
        } else {
            if (count <= 2 || count == 4) {
                mColumnCount = 2;
            } else {
                mColumnCount = 3;
            }
            mItemWidth = (int) ((width - getPaddingLeft() - getPaddingRight() - 2 * mSpacing) / 3);
            mItemHeight = (int) (mItemWidth / mItemAspectRatio);
        }


        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = mItemWidth;
            layoutParams.height = mItemHeight;
//            layoutParams.width = 110;
//            layoutParams.height = 110;
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getDesiredHeight(mItemHeight), MeasureSpec.EXACTLY);
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(
                    getDesiredWidth(mItemWidth), MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();
        //获取子控件的宽高约束规则
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private int getDesiredHeight(int mItemHeight) {
        int totalHeight = getPaddingTop() + getPaddingBottom();
        int count = getChildCount();
        if (count > 0) {
            int row = (count - 1) / mColumnCount;
            totalHeight = (int) ((row + 1) * mItemHeight + (row) * mSpacing) + totalHeight;
        }
        return totalHeight;
    }

    private int getDesiredWidth(int mItemWidth) {
        int totalWidth = getPaddingLeft() + getPaddingRight();
        int count = getChildCount();
        if (count > 0) {
            if (count < mColumnCount) {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mSpacing) + totalWidth;
            } else {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mSpacing) + totalWidth;
            }

        }
        return totalWidth;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View imageView = getChildAt(i);

            int column = i % mColumnCount;
            int row = i / mColumnCount;
            int left = (int) (getPaddingLeft() + column * (mSpacing + mItemWidth));
            int top = (int) (getPaddingTop() + row * (mSpacing + mItemHeight));

            imageView.layout(left, top, left + mItemWidth, top + mItemHeight);
        }
    }


    /**
     * 显示图片
     */
    public void setImageUrls(final List<ProjectDetailBean.UserPLBean.PlImgListBean> imageUrls) {
        imgList.clear();
        for (ProjectDetailBean.UserPLBean.PlImgListBean item : imageUrls) {
            imgList.add(item.getImgPath());
        }

        removeAllViews();
        if (imageUrls.size() == 0) {
            return;
        }

        int count = imageUrls.size();
        if (count == 1) {
            //一般在url中会有宽高 可以算出宽高比
            //测试url固定用的 1000:1376
//            mItemAspectRatio = AppUtil.getAspectRatio(imageUrls.get(0));
            mItemAspectRatio = 1000 / 1376f;
        } else {
            mItemAspectRatio = 1;
        }

        for (int i = 0; i < imageUrls.size(); i++) {

            AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
            appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            appCompatImageView.setTag(i);
            GlideApp.with(appCompatImageView.getContext())
                    .load(imageUrls.get(i).getImgPath())
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(appCompatImageView);
            addView(appCompatImageView);
            //点击查看大图
            appCompatImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.LOOK_IMG_ID = String.valueOf(v.getTag());
                    EventBus.getDefault().post(new NetResponse("LOOK_IMG", imgList));

                }
            });
        }
    }


    /**
     * 显示图片
     */
    public void setImageUrls(final List<ProjectDetailBean.UserPLBean.PlImgListBean> imageUrls, int subCount) {
        ArrayList<String> imgList = new ArrayList<>();
        for (ProjectDetailBean.UserPLBean.PlImgListBean item : imageUrls) {
            imgList.add(item.getImgPath());
        }

        removeAllViews();
        if (imageUrls.size() == 0) {
            return;
        }

        int count = imageUrls.size();
        if (count == 1) {
            //一般在url中会有宽高 可以算出宽高比
            //测试url固定用的 1000:1376
//            mItemAspectRatio = AppUtil.getAspectRatio(imageUrls.get(0));
            mItemAspectRatio = 1000 / 1376f;
        } else {
            mItemAspectRatio = 1;
        }

        List<ProjectDetailBean.UserPLBean.PlImgListBean> imageUrls1 = imageUrls.subList(0, subCount);
        for (int i = 0; i < imageUrls1.size(); i++) {
            AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
            appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            appCompatImageView.setTag(i);
            GlideApp.with(appCompatImageView.getContext())
                    .load(imageUrls1.get(i).getImgPath())
                    .centerCrop()
                    .transform(new GlideRoundTransform(4))
                    .into(appCompatImageView);
            addView(appCompatImageView);
            //点击查看大图
            appCompatImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.LOOK_IMG_ID = String.valueOf(v.getTag());
                    EventBus.getDefault().post(new NetResponse("LOOK_IMG", imgList));
                }
            });
        }
    }


    public int getItemWidth() {
        return mItemWidth;
    }

    public int getItemHeight() {
        return mItemHeight;
    }

    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        mColumnCount = columnCount;
        invalidate();
    }

    public float getSpacing() {
        return mSpacing;
    }

    public void setSpacing(float spacing) {
        mSpacing = spacing;
        invalidate();
    }

    public float getItemAspectRatio() {
        return mItemAspectRatio;
    }

    public void setItemAspectRatio(float itemAspectRatio) {
        mItemAspectRatio = itemAspectRatio;
    }
}