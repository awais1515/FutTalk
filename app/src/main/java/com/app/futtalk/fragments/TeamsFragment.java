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
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class TeamsFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewTeams;
    private TeamsAdapter teamsAdapter;



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
        recyclerViewTeams = getView().findViewById(R.id.recycler_view_teams);
        recyclerViewTeams.setLayoutManager(new LinearLayoutManager(context));
        loadData();
    }

    private void setListeners() {

    }



    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        DataHelper.getAllTeamsFromApi(140, 2023, new TeamsDataListener() {
            @Override
            public void onTeamsLoaded(List<Team> teamList) {
                // now we have team list and we are ready to set our adapter
                teamsAdapter = new TeamsAdapter(getActivity(),teamList, R.layout.row_teams);
                recyclerViewTeams.setAdapter(teamsAdapter);
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
