package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.CommentsAdapter;
import com.app.futtalk.models.Comment;
import com.app.futtalk.utils.Utils;
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
    private RecyclerView recyclerViewComments;
    private List<Comment> commentList;
    private CommentsAdapter commentsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        init();
        setData();
        setListeners();
        getComments();
    }
    private void init() {
        ivProfilePic = findViewById(R.id.ivProfilePic);
        recyclerViewComments = findViewById(R.id.recycler_view_comments);
        commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(context, commentList, R.layout.activity_comments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewComments.setAdapter(commentsAdapter);

    }

    private void setListeners() {
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

    private void getComments() {
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}