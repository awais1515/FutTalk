package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FeedAdapter;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.AdsHelper;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.References;
import com.app.futtalk.utils.Settings;
import com.app.futtalk.utils.Utils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedPostActivity extends BaseActivity {

    private Context context;
    private Team team;
    private TextView tvTeamName;
    private CircleImageView ivProfilePic;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    private List<FeedPost> feedPosts;
    private Handler handler;
    private Runnable taskRunnable;
    private ProgressBar progressBar;
    private ExoPlayer player;

    private TextView tvNoDataFound;
    private boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_posts);
        team = (Team) getIntent().getSerializableExtra("team");
        init();
        setData();
        fetchPostsData();
        postChecker();
        setListeners();
    }

    private void init() {
        context = this;
        tvTeamName = findViewById(R.id.tv_team_name);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        recyclerView = findViewById(R.id.recycler_view);
        feedPosts = new ArrayList<>();
        player = new ExoPlayer.Builder(context).build();
        feedAdapter = new FeedAdapter(context, team, feedPosts, R.layout.row_feed, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(feedAdapter);
        progressBar = findViewById(R.id.progressBar);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        handler = new Handler();
        taskRunnable = new Runnable() {
            @Override
            public void run() {
                feedAdapter.notifyDataSetChanged();
                handler.postDelayed(this, Settings.FEED_REFRESH_DURATION);
            }
        };
        handler.post(taskRunnable);
    }

    private void setListeners() {
        findViewById(R.id.btnAddPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsHelper.getInstance().showRewardedInterstitialAd(FeedPostActivity.this, new AdsHelper.AdWatchListener() {
                    @Override
                    public void onAdWatched() {
                        Intent intent = new Intent(context, AddPostActivity.class);
                        intent.putExtra("team", team);
                        startActivity(intent);
                    }
                });
            }
        });

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData() {
        tvTeamName.setText(team.getName());
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private void fetchPostsData() {
        FirebaseDatabase.getInstance().getReference(References.FEED).child(team.getName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                isDataLoaded = true;
                progressBar.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.GONE);
                FeedPost feedPost = snapshot.getValue(FeedPost.class);
                feedPost.setId(snapshot.getKey());
                feedPosts.add(0, feedPost);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void postChecker() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDataLoaded) {
                    progressBar.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        },15000);
    }


}