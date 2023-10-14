package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FeedAdapter;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Report;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.AdsHelper;
import com.app.futtalk.utils.References;
import com.app.futtalk.utils.Settings;
import com.app.futtalk.utils.Utils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportedPostsActivity extends BaseActivity {

    private Context context;
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
        setContentView(R.layout.activity_reported_posts);
        init();
        //postChecker();
        setListeners();
    }

    private void init() {
        context = this;
        tvTeamName = findViewById(R.id.tv_team_name);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        recyclerView = findViewById(R.id.recycler_view);
        feedPosts = new ArrayList<>();
        player = new ExoPlayer.Builder(context).build();
        feedAdapter = new FeedAdapter(context, null, feedPosts, R.layout.row_feed, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(feedAdapter);
        progressBar = findViewById(R.id.progressBar);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        getReportsData();
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

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void getReportsData() {
        FirebaseDatabase.getInstance().getReference(References.reports).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren()) {
                    progressBar.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    // for each reported post
                    List<Report> reports = new ArrayList<>();
                    for(DataSnapshot reportSnapShot : dataSnapshot.getChildren()) {
                        Report report = reportSnapShot.getValue(Report.class);
                        reports.add(report);
                    }

                    if (reports.size() > 0) {
                        Report report1 = reports.get(0);
                        FirebaseDatabase.getInstance().getReference(References.FEED).child(report1.getTeamName()).child(report1.getPostId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                FeedPost feedPost = snapshot.getValue(FeedPost.class);
                                feedPost.setReports(reports);
                                isDataLoaded = true;
                                progressBar.setVisibility(View.GONE);
                                tvNoDataFound.setVisibility(View.GONE);
                                feedPost.setId(dataSnapshot.getKey());
                                feedPosts.add(feedPost);
                                feedAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void postChecker() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDataLoaded) {
                    progressBar.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        },15000);
    }*/


}