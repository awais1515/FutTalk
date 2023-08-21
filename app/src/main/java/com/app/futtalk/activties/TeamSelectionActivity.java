package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.adapters.TeamsSelectionAdapter;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

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
        loadData();
    }

    private void init() {
        horizontalScrollView = findViewById(R.id.horizontalScrollViewLeagues);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        ivBack = findViewById(R.id.ivBack);

        recyclerViewTeamSelection = findViewById(R.id.recycler_view_teams_of_leagues);
        recyclerViewTeamSelection.setLayoutManager((new LinearLayoutManager(this)));


    }

    private void setListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        DataHelper.getAllTeamsFromApi(140, 2023, new TeamsDataListener() {
            @Override
            public void onTeamsLoaded(List<Team> teamSelectionList) {
                teamsSelectionAdapter = new TeamsSelectionAdapter(context, teamSelectionList, R.layout.row_team_selection);
                recyclerViewTeamSelection.setAdapter(teamsSelectionAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

    }
}