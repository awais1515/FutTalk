package com.app.futtalk.activties;

import static com.app.futtalk.utils.DbReferences.COMMENTS;
import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.adapters.CommentsAdapter;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsActivity extends AppCompatActivity {

    private Context context;
    private CircleImageView ivProfilePic;
    private RecyclerView recyclerView;
    private List<Comment> commentList;
    private CommentsAdapter commentsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        init();
        setData();
        setListeners();
        fetchComments();
    }
    private void init() {
        context=this;
        ivProfilePic = findViewById(R.id.ivProfilePic);
        recyclerView = findViewById(R.id.recycler_view_comments);
        commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(context, commentList, R.layout.activity_comments);
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
        findViewById(R.id.SendComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddCommentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private void fetchComments() {
        FirebaseDatabase.getInstance().getReference(COMMENTS).child(COMMENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
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