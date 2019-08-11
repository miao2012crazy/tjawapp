package com.zysoft.tjawshapingapp.common;

import android.widget.ImageView;

/**
 * Created by mr.miao on 2019/8/9.
 */

public class GlideUtil {


    public static void showPic(String url, ImageView imageView){
        if (!imageView.getTag().equals(url)){
            imageView.setTag(null);
            GlideApp.with(imageView.getContext()).load(url).centerCrop().transform(new GlideRoundTransform( 4)).into(imageView);
            imageView.setTag(url);
        }

    }



}
