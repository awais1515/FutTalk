package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveFeedActivity extends AppCompatActivity {

    private Context context;
    private Team team;
    private TextView tvTeamName;
    private CircleImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
        team = (Team) getIntent().getSerializableExtra("team");
        init();
        setData();
        setListeners();
    }

    private void init() {
        context = this;
        tvTeamName = findViewById(R.id.tv_team_name);
        ivProfilePic = findViewById(R.id.ivProfilePic);
    }

    private void setListeners() {
        findViewById(R.id.btnAddPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPostActivity.class);
                intent.putExtra("team", team);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        tvTeamName.setText(team.getName());
        Utils.setPicture(this, ivProfilePic, CURRENT_USER.getProfileUrl());
    }

}