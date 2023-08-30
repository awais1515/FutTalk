package com.app.futtalk.activties;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class FixturesActivity extends BaseActivity {

    private Context context;
    private RecyclerView recyclerViewLiveMatches;
    private FixturesAdapter fixturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        context = this;
        recyclerViewLiveMatches = findViewById(R.id.recycler_view);
        recyclerViewLiveMatches.setLayoutManager((new LinearLayoutManager(this)));
        setListeners();
        loadData();
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        fixturesAdapter = new FixturesAdapter(context, DataHelper.getUpComingMatches(), R.layout.row_view_fixtures);
        recyclerViewLiveMatches.setAdapter(fixturesAdapter);
    }
}