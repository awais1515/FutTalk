package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCommentActivity extends BaseActivity {

    private Context context;
    private EditText etComment;
    private CircleImageView ivProfilePic;
    private ImageView iv_send_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        init();
        setData();
        setListeners();
    }

    private void init() {
        context = this;
        etComment = findViewById(R.id.etComment);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        iv_send_comment = findViewById(R.id.iv_send_comment);
    }

    private void setListeners() {
        iv_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    publishComment();
                }
            }
        });
    }

    private void setData() {
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private boolean isValid() {
        String textStory = etComment.getText().toString().trim();
        if (textStory.isEmpty()) {
            showToastMessage("Please write a Comment");
            return false;
        }

        if (textStory.length() < 10) {
            showToastMessage("your comment must contain at least 10 characters");
            return false;
        }
        return true;
    }

    private void publishComment() {
        String textComment = etComment.getText().toString().trim();
        String dateTime = new Date().toString();
        Comment comment = new Comment();
        comment.setUid(CURRENT_USER.getId());
        comment.setText(textComment);
        comment.setDateTime(dateTime);

        showProgressDialog("Publishing...");
        DatabaseReference comments = FirebaseDatabase.getInstance().getReference("comments");
        comments.push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Comment published successfully");
                    finish();
                } else {
                    showToastMessage("Failed to publish the comment");
                }
            }
        });
        }
    }

