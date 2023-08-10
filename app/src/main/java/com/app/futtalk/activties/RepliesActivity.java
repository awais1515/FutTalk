package com.app.futtalk.activties;

import static com.app.futtalk.utils.DbReferences.FEED;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.RepliesAdapter;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Reply;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.User;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Settings;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RepliesActivity extends BaseActivity {
    private Context context;
    private CircleImageView ivProfilePic;
    private RecyclerView recyclerView;
    private RepliesAdapter repliesAdapter;
    private List<Reply> replyList;
    private EditText etReply;
    private Team team;
    private FeedPost feedPost;
    private Comment comment;
    private TextView tvNoReplyFound;
    private TextView tvUserName;
    private TextView tvTimePassed;
    private ImageView ivSend;
    private ProgressBar progressBar;
    private TextView tvCommentText;
    private Handler handler;
    private Runnable taskRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies);
        team = (Team) getIntent().getSerializableExtra("team");
        feedPost = (FeedPost) getIntent().getSerializableExtra("feedPost");
        comment= (Comment) getIntent().getSerializableExtra("comment");
        init();
        setData();
        setListeners();
        updateTimes();

    }

    private void init(){
        context = this;
        ivProfilePic = findViewById(R.id.iv_profile_picture);
        etReply = findViewById(R.id.etReply);
        tvUserName = findViewById(R.id.tvProfileName);
        tvNoReplyFound = findViewById(R.id.tvNoReply);
        tvTimePassed = findViewById(R.id.tvTimePassed);
        tvCommentText = findViewById(R.id.tvCommentText);
        recyclerView = findViewById(R.id.recycler_view_replies);
        replyList = new ArrayList<>(comment.getReplies());
        Collections.reverse(replyList);
        repliesAdapter= new RepliesAdapter(context, replyList, R.layout.row_replies);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(repliesAdapter);
        ivSend = findViewById(R.id.ivSendReply);
        progressBar = findViewById(R.id.progressBar);

    }

    private void setListeners(){
        findViewById(R.id.ivBackArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.ivSendReply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = etReply.getText().toString().trim();
                if (!reply.isEmpty()) {
                    addReply(reply);
                }
            }
        });
    }
    private void addReply(String replies){
        Reply reply= new Reply();
        reply.setUid(FirebaseUtils.CURRENT_USER.getId());
        reply.setDateTime(new Date().toString());
        reply.setText(replies);
        comment.getReplies().add(reply);
        repliesAdapter.notifyDataSetChanged();
        showPublishInProgress(true);
        FirebaseDatabase.getInstance().getReference(FEED).child(team.getName()).child(feedPost.getId()).child(DbReferences.COMMENTS).child(comment.getId()).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showPublishInProgress(false);
                if (task.isSuccessful()) {
                    tvNoReplyFound.setVisibility(View.GONE);
                    etReply.setText("");
                    replyList.add(0,reply);
                    repliesAdapter.notifyDataSetChanged();
                } else {
                    showToastMessage("Sorry couldn't added a reply");
                }
            }
        });
        etReply.setText("");
    }
    private void setData() {
        tvCommentText = findViewById(R.id.tvCommentText);
        tvCommentText.setText(comment.getText());
        tvTimePassed.setText(Utils.getTimeAgo(comment.getDateTime()));
        if (comment.getReplies().size() > 0) {
            tvNoReplyFound.setVisibility(View.GONE);
        }
        FirebaseDatabase.getInstance().getReference(DbReferences.USERS).child(comment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvUserName.setText(user.getFirstName());
                Utils.setPicture(context,ivProfilePic, user.getProfileUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showPublishInProgress(boolean isPublishing){
        if (isPublishing){
            ivSend.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        } else{
            ivSend.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void updateTimes() {
        handler = new Handler();
        taskRunnable = new Runnable() {
            @Override
            public void run() {
                repliesAdapter.notifyDataSetChanged();
                handler.postDelayed(this, Settings.FEED_REFRESH_DURATION);
            }
        };
        handler.post(taskRunnable);
    }



}