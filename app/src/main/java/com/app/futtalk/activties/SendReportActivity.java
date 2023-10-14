package com.app.futtalk.activties;

import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FeedAdapter;
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.Report;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.FirebaseUtils;
import com.app.futtalk.utils.References;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class SendReportActivity extends BaseActivity {
    
    EditText etWriteComplaint;
    FeedPost feedPost;
    ImageView ivBack;
    Team team;

    Button submitReport;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);
        feedPost = (FeedPost) getIntent().getSerializableExtra("post");
        team = (Team) getIntent().getSerializableExtra("team");
        init();
        setListeners();

    }

    private void init(){
        context= this;
        ivBack= findViewById(R.id.ivBackArrow);
        etWriteComplaint= findViewById(R.id.etReport);
        submitReport = findViewById(R.id.btnSubmitReport);

    }

    private void setListeners(){
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()) {
                    submitReport();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private boolean isValid() {
        String complaint = etWriteComplaint.getText().toString().trim();
        if (complaint.isEmpty()) {
            etWriteComplaint.setError("You must enter a complaint");
            etWriteComplaint.requestFocus();
            return false;
        }

        if (complaint.length() < 20) {
            etWriteComplaint.setError("Your complaint should be at least 20 characters long");
            etWriteComplaint.requestFocus();
            return false;
        }

        return true;
    }


    private void submitReport() {
        Report report = new Report();
        report.setReporterId(CURRENT_USER.getId());
        report.setPostId(feedPost.getId());
        report.setDateTime(new Date().toString());
        report.setReason(etWriteComplaint.getText().toString());
        report.setTeamName(team.getName());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(References.reports).child(feedPost.getId());
        String reportId = databaseReference.push().getKey();
        report.setId(reportId);
        showProgressDialog("Submitting..");
        databaseReference.child(reportId).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                closeProgressDialog();
                if (task.isSuccessful()) {
                    showToastMessage("Reported successfully, admin will take the action soon");
                    finish();
                } else {
                    showToastMessage("Failed to submit the report");
                }
            }
        });
    }
}