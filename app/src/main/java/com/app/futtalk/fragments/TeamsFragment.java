package com.app.futtalk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;

public class TeamsFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewTeams;
    private TeamsAdapter teamsAdapter;

    private Button btnLiveFeed;

    private Button btnPlayers;

    private Button btnFixtures;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListeners();
    }

    private void init(){
        context= getActivity();

        btnLiveFeed= getView().findViewById(R.id.btnLiveFeed);

        btnPlayers= getView().findViewById(R.id.btnPlayers);

        btnFixtures = getView().findViewById(R.id.btnFixtures);

        recyclerViewTeams = getView().findViewById(R.id.recycler_view_teams);


        recyclerViewTeams.setLayoutManager((new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false)));
        teamsAdapter = new TeamsAdapter(getActivity(), DataHelper.getTeamData(7), R.layout.row_teams);
        recyclerViewTeams.setAdapter(teamsAdapter);

    }

    private void setListeners() {
       /* btnLiveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LiveFeedActivity.class);
                startActivity(intent);
            }
        });

        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayersActivity.class);
                startActivity(intent);
            }
        });

        btnFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleClubFixturesActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void addTeam(Team team) {
    }

    public void removeTeam(Team team) {
    }
}
