package com.zysoft.tjawshapingapp.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zysoft.baseapp.BR;
import com.zysoft.baseapp.base.BindingAdapterItem;

/**
 * Created by mr.miao on 2019/1/19.
 */

public class CustomTitleBean extends BaseObservable implements BindingAdapterItem {

    /**
     * 中间标题
     */
    private String title;
    /**
     * 右侧标题
     */
    private String right_title;
    /**
     * 是否显示返回键
     */
    private boolean is_show_return;
    private int iv_right;
    /**
     *
     */
    private boolean is_show_right = false;

    public CustomTitleBean(String title, boolean is_show_return, int iv_right, boolean is_show_right) {
        this.title = title;
        this.is_show_return = is_show_return;
        this.iv_right = iv_right;
        this.is_show_right = is_show_right;
    }

    public boolean isIs_show_right() {
        return is_show_right;
    }

    public void setIs_show_right(boolean is_show_right) {
        this.is_show_right = is_show_right;
    }

    public CustomTitleBean(String title, String right_title, boolean is_show_return, int iv_right) {
        this.title = title;
        this.right_title = right_title;
        this.is_show_return = is_show_return;
        this.iv_right = iv_right;
    }

    public boolean isIs_show_return() {

        return is_show_return;
    }

    public void setIs_show_return(boolean is_show_return) {
        this.is_show_return = is_show_return;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getRight_title() {
        return right_title;
    }

    public void setRight_title(String right_title) {
        this.right_title = right_title;
        notifyPropertyChanged(BR.right_title);
    }


    public int getIv_right() {
        return iv_right;
    }

    public void setIv_right(int iv_right) {
        this.iv_right = iv_right;
    }

    @Override
    public int getViewType() {
        return com.zysoft.baseapp.R.layout.layout_title_bar;
    }
}
