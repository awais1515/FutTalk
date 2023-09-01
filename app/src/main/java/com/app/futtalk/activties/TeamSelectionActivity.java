package com.app.futtalk.activties;

import static com.app.futtalk.utils.DbReferences.USERS;
import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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
import com.app.futtalk.utils.DbReferences;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamSelectionActivity extends BaseActivity {

    private static final String ALL_TEAMS = "ALL_TEAMS";
    Context context;

    private ChipGroup chipGroup;

    private List<LeagueInfo> leagueList = new ArrayList<>();
    private List<Team> allTeams = new ArrayList<>();
    ImageView ivBack;

    private RecyclerView recyclerViewTeamSelection;
    private TeamsSelectionAdapter teamsSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        init();
        setListeners();
        showProgressDialog("Loading..");
        loadLeaguesFromFirebase();
    }

    private void init() {
        context = this;
        ivBack = findViewById(R.id.ivBack);
        chipGroup = findViewById(R.id.chipGroup);

        recyclerViewTeamSelection = findViewById(R.id.recycler_view_teams_of_leagues);
        recyclerViewTeamSelection.setLayoutManager((new LinearLayoutManager(this)));
        teamsSelectionAdapter = new TeamsSelectionAdapter(context, allTeams, R.layout.row_team_selection);
        recyclerViewTeamSelection.setAdapter(teamsSelectionAdapter);
    }

    private void setListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData(League league) {
        DataHelper.getAllTeamsFromApi(league.getId(), 2023, new TeamsDataListener() {
            @Override
            public void onTeamsLoaded(List<Team> teamSelectionList) {
                for (Team team : teamSelectionList) {
                    team.setLeague(league);
                }
                allTeams.addAll(teamSelectionList);
                teamsSelectionAdapter.setTeamsSelectionList(allTeams);
                closeProgressDialog();
            }

            @Override
            public void onFailure(String message) {
                closeProgressDialog();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });



    }

    private void loadLeaguesFromFirebase() {
        FirebaseDatabase.getInstance().getReference(USERS).child(CURRENT_USER.getId()).child(DbReferences.FOLLOW_LEAGUES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leagueList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LeagueInfo league = dataSnapshot.getValue(LeagueInfo.class);
                    leagueList.add(league);
                    loadData(league.getLeague());
                }
                addChips();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addChips() {

        Chip all = (Chip) getLayoutInflater().inflate(R.layout.row_chip, null);
        all.setText("All");
        all.setChecked(true);
        all.setCloseIconVisible(false);
        chipGroup.addView(all);
        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterTeams(ALL_TEAMS);
                }
            }
        });

        for (LeagueInfo leagueInfo: leagueList) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.row_chip, null);
            chip.setText(leagueInfo.getLeague().getName());
            chip.setCheckable(true);
            chip.setCloseIconVisible(false);
            chipGroup.addView(chip);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        filterTeams(leagueInfo.getLeague().getName());
                    }
                }
            });
        }
    }

    private void filterTeams(String filter) {
        if (filter.equals(ALL_TEAMS)) {
            teamsSelectionAdapter.setTeamsSelectionList(allTeams);
        } else {
            List<Team> filteredTeams = new ArrayList<>();
            for (Team team : allTeams) {
                if (team.getLeague().getName().equals(filter)) {
                    filteredTeams.add(team);
                }
            }
            teamsSelectionAdapter.setTeamsSelectionList(filteredTeams);
        }
    }
}