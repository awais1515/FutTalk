package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.utils.DataHelper;

public class FixturesActivity extends AppCompatActivity {

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
        fixturesAdapter = new FixturesAdapter(this, DataHelper.getUpComingMatches(7), R.layout.row_view_fixtures);
        recyclerViewLiveMatches.setAdapter(fixturesAdapter);
        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}