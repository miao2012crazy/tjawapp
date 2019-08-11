package com.zysoft.tjawshapingapp.handler;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.zysoft.tjawshapingapp.R;
import com.zysoft.tjawshapingapp.common.GlideApp;
import com.zysoft.tjawshapingapp.common.GlideCircleTransform;
import com.zysoft.tjawshapingapp.common.GlideRoundTransform;
import com.zysoft.tjawshapingapp.common.UIUtils;

/**
 * Created by mr.miao on 2018/5/9.
 */

public class HandlerEvent {

    private final Context mActivity;

    public HandlerEvent(Context context) {
        this.mActivity = context;
    }


    public void startActivityBase(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    @BindingAdapter("imageUrl")
    public static void loadImg(ImageView imageView, String url) {
        imageView.setTag(null);
        if (url == null) {
            imageView.setImageResource(R.drawable.ic_img_error);
        } else {
            // Glide代替Volley
            if (url.startsWith("http")) {
                GlideApp.with(imageView.getContext())
                        .load(url)
                        .error(R.drawable.ic_img_error)
                        .centerCrop()
                        .transform(new GlideRoundTransform(4))
                        .into(imageView);
            }
        }

    }


    @BindingAdapter("imageUrlPicSelector")
    public static void loadimgpicselect(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.drawable.ic_img_error);
        } else {
            GlideApp.with(imageView.getContext())
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.ic_img_error)

                    .transform(new GlideRoundTransform(4))
                    .into(imageView);
        }

    }


    @BindingAdapter("imageUrlLocal")
    public static void loadImgLocal(ImageView imageView, int resId) {
        if (resId == -1) {
            imageView.setVisibility(View.GONE);
            return;
        }


        if (resId == 0) {
            imageView.setImageResource(R.drawable.ic_img_error);
        } else {
            // Glide代替Volley
//            Glide.with(imageView.getContext()).load(HttpUrl.getBaseUrl() + url).into(imageView);
            imageView.setImageResource(resId);
        }

    }

    @BindingAdapter("bitmapUrlLocal")
    public static void loadBitmapLocal(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null) {
            imageView.setImageBitmap(BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.ic_img_error));
        } else {
            // Glide代替Volley
//            Glide.with(imageView.getContext()).load(HttpUrl.getBaseUrl() + url).into(imageView);
            imageView.setImageBitmap(bitmap);
        }

    }


    @BindingAdapter("loadImgCircle")
    public static void loadImgCircle(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.drawable.ic_img_error);
        } else {
            // Glide代替Volley
            if (url.startsWith("http")) {
                GlideApp.with(imageView.getContext())
                        .load(url)
                        .error(R.drawable.ic_img_error)

                        .transform(new GlideCircleTransform())
                        .into(imageView);
            }
        }

    }

    @BindingAdapter("loadImgRound")
    public static void loadImgRound(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.drawable.ic_img_error);
        } else {
            // Glide代替Volley
            if (url.startsWith("http")) {
                GlideApp.with(imageView.getContext())
                        .load(url)
                        .error(R.drawable.ic_img_error)
                        .centerCrop()
                        .transform(new GlideRoundTransform(4))
                        .into(imageView);
            }
        }

    }


}
