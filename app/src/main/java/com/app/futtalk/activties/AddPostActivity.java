package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.futtalk.R;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends BaseActivity {

    private Context context;
    private EditText etStory;
    private TextView tvTeamName;
    private CircleImageView ivProfilePic;
    private Button btnPublish;
    private Team team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        team = (Team) getIntent().getSerializableExtra("team");
        init();
        setData();
        setListeners();
    }

    private void init() {
        context = this;
        tvTeamName = findViewById(R.id.tv_team_name);
        etStory = findViewById(R.id.etStory);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnPublish = findViewById(R.id.btnPublish);
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    publishStory();
                }
            }
        });
    }

    private void setData() {
        tvTeamName.setText(team.getName());
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

    private boolean isValid() {
        String textStory = etStory.getText().toString().trim();
        if (textStory.isEmpty()) {
            showToastMessage("Please write a story");
            return false;
        }

        if (textStory.length() < 10) {
            showToastMessage("your story must contain atleast 10 characters");
            return false;
        }
        return true;
    }

    private void publishStory() {
        String textStory = etStory.getText().toString().trim();
        FeedPost feedPost = new FeedPost();
        feedPost.setText(textStory);
        feedPost.setDateTime(new Date().toString());
        feedPost.setUid(CURRENT_USER.getId());

        showProgressDialog("Publishing...");
        FirebaseDatabase.getInstance().getReference(DbReferences.FEED).child(team.getName()).push().setValue(feedPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Story published successfully");
                    finish();
                } else {
                    showToastMessage("Failed to publish the story");
                }
            }
        });
    }
}