package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.utils.Utils;
import com.bumptech.glide.Glide;

public class FullImageViewActivity extends AppCompatActivity {

    private FeedPost feedPost;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        feedPost = (FeedPost) getIntent().getSerializableExtra("feed_post");
        image = findViewById(R.id.iv_image);
        Glide.with(this)
                .load(feedPost.getStoryImageURL())
                .into(image);
        findViewById(R.id.ivBackArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}