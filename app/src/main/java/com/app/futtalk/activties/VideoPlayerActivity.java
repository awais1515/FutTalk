package com.app.futtalk.activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.utils.DataHelper;
import com.app.futtalk.utils.References;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class VideoPlayerActivity extends BaseActivity {

    private ExoPlayer player;
    private Context context;
    private TextView tvFeedPost;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressBar);
        setUpVideoPlayer(feedPost.getStoryVideoURL());
    }
    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ivDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to remove this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFeaturedPost();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void deleteFeaturedPost() {
        showProgressDialog("Deleting...");
        FirebaseDatabase.getInstance().getReference(References.FEATURED_POSTS).child(feedPost.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Featured most deleted successfully");
                    DataHelper.deleteFeaturedPost(feedPost);
                    finish();
                } else {
                    showToastMessage("Unable to delete the featured post");
                }
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
        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Player.Listener.super.onIsPlayingChanged(isPlaying);
                if (isPlaying) {
                   progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        player.setPlayWhenReady(true);

        playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  player.pause();
            }
        });
    }

}