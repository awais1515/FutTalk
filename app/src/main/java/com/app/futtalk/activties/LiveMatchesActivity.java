package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.utils.DataHelper;

public class LiveMatchesActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerViewLiveMatches;
    private LiveMatchesAdapter liveMatchesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_matches);
        context = this;
        recyclerViewLiveMatches = findViewById(R.id.recycler_view);
        recyclerViewLiveMatches.setLayoutManager((new LinearLayoutManager(this)));
        liveMatchesAdapter = new LiveMatchesAdapter(this, DataHelper.getLiveMatches(7), R.layout.row_view_live_match);
        recyclerViewLiveMatches.setAdapter(liveMatchesAdapter);
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