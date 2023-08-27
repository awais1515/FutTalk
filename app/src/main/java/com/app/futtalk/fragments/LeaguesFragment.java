package com.app.futtalk.fragments;

import static com.app.futtalk.utils.DbReferences.USERS;
import static com.app.futtalk.utils.FirebaseUtils.CURRENT_USER;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeaguesAdapter;
import com.app.futtalk.models.League;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.utils.DbReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaguesFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewLeagues;
    private LeaguesAdapter leaguesAdapter;
    private List<LeagueInfo> leagueList = new ArrayList<>();
    private ProgressBar progressBar;
    private  TextView tvNoDataFound;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leagues, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();


    }

    private void init() {
        context = getActivity();
        recyclerViewLeagues = getView().findViewById(R.id.recycler_view_leagues);
        recyclerViewLeagues.setLayoutManager(new LinearLayoutManager(context));
        leaguesAdapter = new LeaguesAdapter(context, leagueList, R.layout.row_league_selection,false);
        tvNoDataFound = getView().findViewById(R.id.tvNoDataFound);
        progressBar = getView().findViewById(R.id.progressBar);
    }

    private void loadLeaguesFromFirebase() {
        FirebaseDatabase.getInstance().getReference(USERS).child(CURRENT_USER.getId()).child(DbReferences.FOLLOW_LEAGUES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leagueList.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    LeagueInfo league = dataSnapshot.getValue(LeagueInfo.class);
                    leagueList.add(league);
                }
                Collections.reverse(leagueList);
                leaguesAdapter.notifyDataSetChanged();
                if (leagueList.size() == 0) {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoDataFound.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

