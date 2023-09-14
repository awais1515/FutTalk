package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoPlayerActivity extends AppCompatActivity {

    private ExoPlayer player;
    private Context context;
    private TextView tvFeedPost;

    FeedPost feedPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        context = this;
        tvFeedPost = findViewById(R.id.tv_feed_text);
        setListeners();
        feedPost = (FeedPost) getIntent().getSerializableExtra("feed_post");
        tvFeedPost.setText(feedPost.getText());
        setUpVideoPlayer(feedPost.getStoryVideoURL());
    }
    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpVideoPlayer(String videoURL) {
        StyledPlayerView playerView = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(context).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoURL);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

    }
}