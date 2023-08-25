package com.app.futtalk.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewTeams;
    private TeamsAdapter teamsAdapter;
    private List<Team> teamsList;


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

    private void init() {
        context = getActivity();
        recyclerViewTeams = getView().findViewById(R.id.recycler_view_teams);
        recyclerViewTeams.setLayoutManager(new LinearLayoutManager(context));
        teamsAdapter= new TeamsAdapter(context, teamsList ,R.layout.row_teams);
    }

    private void setListeners() {

    }



}

