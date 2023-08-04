package com.app.futtalk.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void setPicture(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static String getTimeAgo(String dateString) {
        String timeAgo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        try {
            Date date = sdf.parse(dateString);
            Date currentDate = new Date();

            long timeDifferenceMillis = currentDate.getTime() - date.getTime();
            timeAgo = getTimeAgo(timeDifferenceMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeAgo;
    }

    private static String getTimeAgo(long timeDifferenceMillis) {
        long seconds = timeDifferenceMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long years = days / 365;

        if (years > 0) {
            return years + (years == 1 ? " year ago" : " years ago");
        } else if (days > 0) {
            return days + (days == 1 ? " day ago" : " days ago");
        } else if (hours > 0) {
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else if (minutes > 0) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else {
            return "few seconds ago";
        }
    }
}
