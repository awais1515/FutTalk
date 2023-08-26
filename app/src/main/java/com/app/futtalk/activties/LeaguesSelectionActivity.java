package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeaguesSelectionAdapter;

public class LeaguesSelectionActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewLeaguesSelection;

    private LeaguesSelectionAdapter leaguesSelectionAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues_selection);
        init();
    }

    private void init(){
        context=this;
        recyclerViewLeaguesSelection = findViewById(R.id.recycler_view_league_selection);
        recyclerViewLeaguesSelection.setLayoutManager((new LinearLayoutManager(this)));


    }
}