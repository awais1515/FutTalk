package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.adapters.ReportsAdapter;
import com.app.futtalk.models.FeedPost;

public class ReportsDetailActivity extends AppCompatActivity {

    Context context;
    private FeedPost feedPost;
    private RecyclerView recyclerView;
    private ReportsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_detail);
        feedPost = (FeedPost) getIntent().getSerializableExtra("post");
        init();
        setListeners();
    }

    private void init() {
        context = this;
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ReportsAdapter(context, feedPost.getReports(), R.layout.row_report_new);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        findViewById(R.id.ivBackArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}