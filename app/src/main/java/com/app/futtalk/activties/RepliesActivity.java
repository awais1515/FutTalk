package com.app.futtalk.activties;

import static com.app.futtalk.utils.DbReferences.COMMENTS;
import static com.app.futtalk.utils.DbReferences.FEED;
import static com.app.futtalk.utils.DbReferences.REPLIES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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
import com.app.futtalk.utils.DbReferences;
import com.app.futtalk.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

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
    private EditText etReply;
    private Team team;
    private FeedPost feedPost;
    private Comment comment;
    private TextView tvNoReplyFound;
    private ImageView ivSend;
    private ProgressBar progressBar;



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

    }

    private void init(){
        context = this;
        ivProfilePic = findViewById(R.id.iv_profile_picture);
        etReply = findViewById(R.id.etReply);
        tvNoReplyFound = findViewById(R.id.tvNoReply);
        recyclerView = findViewById(R.id.recycler_view_replies);
        repliesAdapter= new RepliesAdapter(context, comment.getReplies(), R.layout.row_replies);
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
        showPublishInProgress(true);
        FirebaseDatabase.getInstance().getReference(FEED).child(team.getName()).child(feedPost.getId()).child(comment.getId()).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    tvNoReplyFound.setVisibility(View.GONE);
                    etReply.setText("");
                } else {
                    showToastMessage("Sorry couldn't added a reply");
                }
            }
        });
        etReply.setText("");
    }
    private void setData() {
        TextView tvCommentText = findViewById(R.id.Comment_text);
        tvCommentText.setText(comment.getText());
        TextView tvTimePassed = findViewById(R.id.timePassed);
        comment.setDateTime(new Date().toString());
        comment.setUid(FirebaseUtils.CURRENT_USER.getId());


    }

    public void showPublishInProgress( boolean isPublishing){
        if (isPublishing){
            ivSend.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        } else{
            ivSend.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }


    }



}