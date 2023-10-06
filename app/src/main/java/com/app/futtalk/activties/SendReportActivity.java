package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;

public class SendReportActivity extends AppCompatActivity {

    FeedPost feedPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);
        feedPost = (FeedPost) getIntent().getSerializableExtra("post");

    }
}