package com.app.futtalk.activties;

import static com.app.futtalk.utils.DbReferences.COMMENTS;
import static com.app.futtalk.utils.DbReferences.FEED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.CommentsAdapter;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.FirebaseUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsActivity extends BaseActivity {

    private Context context;
    private CircleImageView ivProfilePic;
    private RecyclerView recyclerView;
    private List<Comment> commentList;
    private CommentsAdapter commentsAdapter;
    private EditText etComment;
    private Team team;
    private FeedPost feedPost;

    private TextView tvNoCommentFound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        team = (Team) getIntent().getSerializableExtra("team");
        feedPost = (FeedPost) getIntent().getSerializableExtra("feedPost");
        init();
        setData();
        setListeners();
        fetchComments();
    }
    private void init() {
        context=this;
        ivProfilePic = findViewById(R.id.ivProfilePic);
        etComment = findViewById(R.id.etComment);
        tvNoCommentFound = findViewById(R.id.tvNoComment);
        recyclerView = findViewById(R.id.recycler_view_comments);
        commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(context, commentList, R.layout.row_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(commentsAdapter);

    }

    private void setListeners() {

        findViewById(R.id.ivBackArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.ivSendComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etComment.getText().toString().trim();
                if (!comment.isEmpty()) {
                    addComment(comment);
                }
            }
        });
    }

    private void addComment(String commentText) {
        Comment comment = new Comment();
        comment.setUid(FirebaseUtils.CURRENT_USER.getId());
        comment.setDateTime(new Date().toString());
        comment.setText(commentText);
        feedPost.getComments().add(comment);
        FirebaseDatabase.getInstance().getReference(FEED).child(team.getName()).child(feedPost.getId()).setValue(feedPost);
        etComment.setText("");
    }

    private void setData() {
        //Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private void fetchComments() {
        FirebaseDatabase.getInstance().getReference(FEED).child(team.getName()).child(feedPost.getId()).child(COMMENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tvNoCommentFound.setVisibility(View.GONE);
                Comment comment = snapshot.getValue(Comment.class);
                commentList.add(0, comment);
                commentsAdapter.notifyDataSetChanged();

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


}