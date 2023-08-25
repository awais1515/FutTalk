package com.app.futtalk.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static void setPicture(Context context, ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public static void setVideo(Context context, ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(imageView);
        }
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

    public static String getDateFromTimestamp(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = inputFormat.parse(timestamp);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeFromTimestamp(String timestamp, String timeZone) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = inputFormat.parse(timestamp);

            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");
            outputFormat.setTimeZone(TimeZone.getTimeZone(timeZone)); // Set the desired timezone
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }

}
