package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.adapters.TeamsSelectionAdapter;
import com.app.futtalk.utils.DataHelper;

public class TeamSelectionActivity extends AppCompatActivity {

    Context context;
    HorizontalScrollView horizontalScrollView;
    ImageView ivBack;

    private RecyclerView recyclerViewTeamSelection;
    private TeamsSelectionAdapter teamsSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        init();
        setListeners();
    }

    private void init(){
        horizontalScrollView = findViewById(R.id.horizontalScrollViewLeagues);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        ivBack = findViewById(R.id.ivBack);


        recyclerViewTeamSelection = findViewById(R.id.recycler_view_teams_of_leagues);

        recyclerViewTeamSelection.setLayoutManager((new LinearLayoutManager(this)));
        teamsSelectionAdapter = new TeamsSelectionAdapter(this, DataHelper.getTeamData(7), R.layout.row_team_selection);
        recyclerViewTeamSelection.setAdapter(teamsSelectionAdapter);

    }

    private void setListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}