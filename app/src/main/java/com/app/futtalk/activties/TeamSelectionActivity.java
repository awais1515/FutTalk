package com.app.futtalk.activties;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeagueScrollerAdapter;
import com.app.futtalk.adapters.TeamsSelectionAdapter;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.League;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectionActivity extends BaseActivity {

    Context context;

    private ChipGroup chipGroupLeaguesScroller;

    private List<League> leagueList = new ArrayList<>();

    private LeagueScrollerAdapter leagueScrollerAdapter;
    ImageView ivBack;

    private RecyclerView recyclerViewTeamSelection;
    private TeamsSelectionAdapter teamsSelectionAdapter;

    private LeagueInfo leagueInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        init();
        setListeners();
        loadData();
    }

    private void init() {
        context = this;
        ivBack = findViewById(R.id.ivBack);
        chipGroupLeaguesScroller = findViewById(R.id.chipGroup);

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

       showProgressDialog("Loading..");
        DataHelper.getAllTeamsFromApi(140, 2023, new TeamsDataListener() {
            @Override
            public void onTeamsLoaded(List<Team> teamSelectionList) {
                teamsSelectionAdapter = new TeamsSelectionAdapter(context, teamSelectionList, R.layout.row_team_selection);
                recyclerViewTeamSelection.setAdapter(teamsSelectionAdapter);
                closeProgressDialog();
            }

            @Override
            public void onFailure(String message) {
                closeProgressDialog();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addChip(String text, ImageView ivTeamLogo) {
        Chip chip = (Chip) getLayoutInflater().inflate(R.layout.row_chip, null);
        chip.setText(leagueInfo.getLeague().getName());
        chip.setCheckable(false);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroupLeaguesScroller.removeView(view);
            }
        });
        chipGroupLeaguesScroller.addView(chip);
    }
}