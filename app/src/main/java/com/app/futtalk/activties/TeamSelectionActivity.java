package com.app.futtalk.activties;

import static com.app.futtalk.utils.References.USERS;
import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.TeamsSelectionAdapter;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.League;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;
import com.app.futtalk.utils.References;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectionActivity extends BaseActivity {

    private static final String ALL_TEAMS = "ALL_TEAMS";
    Context context;

    private ChipGroup chipGroup;

    private List<LeagueInfo> leagueList = new ArrayList<>();
    private List<Team> allTeams = new ArrayList<>();
    private List<Team> filteredTeams = new ArrayList<>();
    ImageView ivBack;

    private RecyclerView recyclerViewTeamSelection;
    private TeamsSelectionAdapter teamsSelectionAdapter;
    SearchView searchView;

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
        searchView = findViewById(R.id.SearchViewTeams);
    }

    private void setListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Team> filterQueryTeams = filterQueryTeams(query);
                teamsSelectionAdapter.setTeamsSelectionList(filterQueryTeams);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() == 0) {
                    teamsSelectionAdapter.setTeamsSelectionList(filteredTeams);
                }
                return false;
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
                filteredTeams.addAll(teamSelectionList);
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
        FirebaseDatabase.getInstance().getReference(USERS).child(CURRENT_USER.getId()).child(References.FOLLOW_LEAGUES).addValueEventListener(new ValueEventListener() {
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

        if (leagueList.size() > 0) {
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
            for (LeagueInfo leagueInfo : leagueList) {
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

        } else {
            // No Favourite leagues added
            showSelectLeagueDialog();
        }
    }
    private void showSelectLeagueDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please add your Favourite Leagues")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click
                       Intent intent = new Intent(context, LeaguesSelectionActivity.class);
                       startActivity(intent);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void filterTeams(String filter) {
        if (filter.equals(ALL_TEAMS)) {
            teamsSelectionAdapter.setTeamsSelectionList(allTeams);
            filteredTeams.clear();
            filteredTeams.addAll(allTeams);
        } else {
            List<Team> teams = new ArrayList<>();
            for (Team team : allTeams) {
                if (team.getLeague().getName().equals(filter)) {
                    teams.add(team);
                }
            }
            filteredTeams = teams;
            teamsSelectionAdapter.setTeamsSelectionList(filteredTeams);
        }
    }
    private List<Team> filterQueryTeams(String searchQuery) {
        List<Team> teams = new ArrayList<>();
        for (Team team: filteredTeams) {
            if (team.getName().toLowerCase().trim().startsWith(searchQuery.toLowerCase().trim())
                    || team.getName().toLowerCase().trim().startsWith(searchQuery.toLowerCase().trim())){
                teams.add(team);
            }
        }
        return teams;
    }

}
