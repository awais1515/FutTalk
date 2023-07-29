package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;

public class LiveFeedActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
    }

    private void init() {
        context = this;
    }

    private void setListeners() {
        findViewById(R.id.btnAddPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }
}