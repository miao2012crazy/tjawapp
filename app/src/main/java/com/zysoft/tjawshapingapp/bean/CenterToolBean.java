package com.zysoft.tjawshapingapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zysoft.tjawshapingapp.R;

/**
 * Created by mr.miao on 2019/5/21.
 */

public class CenterToolBean implements MultiItemEntity {
    public static final int center_0=0;
    public static final int center_1=1;
    public static final int center_2=2;

    private int itemType;
    private String tag_name;
    private int tag_drawable;
    private int id;

    public CenterToolBean(int itemType) {
        this.itemType = itemType;
    }

    public CenterToolBean(int itemType, String tag_name, int tag_drawable, int id) {
        this.itemType = itemType;
        this.tag_name = tag_name;
        this.tag_drawable = tag_drawable;
        this.id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getTag_drawable() {
        return tag_drawable;
    }

    public void setTag_drawable(int tag_drawable) {
        this.tag_drawable = tag_drawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

//    @Override
//    public int getViewType() {
//        switch (type){
//            case 0:
//                //我的订单  常用工具 热门服务
//                return R.layout.item_center_1;
//            case 1:
//                //已预约 。。。
//                return R.layout.item_center_2;
//            case 2:
//                //
//                return R.layout.item_center_3;
////            case 3:
////                //广告
////                return R.layout.item_center_4;
//        }
//
//
//
//
//        return 0;
//    }
}
