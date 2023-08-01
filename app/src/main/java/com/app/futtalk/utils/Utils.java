package com.app.futtalk.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utils {

    public static void setPicture(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
