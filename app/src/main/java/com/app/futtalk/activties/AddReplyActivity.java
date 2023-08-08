package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.models.Comment;
import com.app.futtalk.models.Reply;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddReplyActivity extends BaseActivity {

    private Context context;
    private EditText etReply;
    private CircleImageView ivProfilePic;
    private ImageView iv_send_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);
        init();
        setData();
        setListeners();
    }

    private void init() {
        context = this;
        etReply = findViewById(R.id.etReply);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        iv_send_reply = findViewById(R.id.iv_send_reply);
    }

    private void setListeners() {
        iv_send_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    publishReply();
                }
            }
        });
    }

    private void setData() {Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private boolean isValid() {
        String textStory = etReply.getText().toString().trim();
        if (textStory.isEmpty()) {
            showToastMessage("Please write a Reply");
            return false;
        }

        if (textStory.length() < 10) {
            showToastMessage("Your reply must contain at least 10 characters");
            return false;
        }
        return true;
    }

    private void publishReply() {
        String textReply = etReply.getText().toString().trim();
        String dateTime = new Date().toString();
        Reply reply = new Reply();
        reply.setUid(CURRENT_USER.getId());
        reply.setText(textReply);
        reply.setDateTime(dateTime);

        showProgressDialog("Publishing...");
        DatabaseReference replies = FirebaseDatabase.getInstance().getReference("replies");
        replies.push().setValue(reply).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Reply published successfully");
                    finish();
                } else {
                    showToastMessage("Failed to publish this reply");
                }
            }
        });
    }
}
