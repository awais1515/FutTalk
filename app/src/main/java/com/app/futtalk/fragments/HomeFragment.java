package com.app.futtalk.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.activties.FixturesActivity;
import com.app.futtalk.activties.LiveMatchesActivity;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.UpcomingFixture;
import com.app.futtalk.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewLiveMatches;
    private RecyclerView recyclerViewUpcomingMatches;
    private LiveMatchesAdapter liveMatchesAdapter;
    private FixturesAdapter fixturesAdapter;

    private TextView tvViewAllLiveMatches;
    private TextView tvViewAllUpcomingMatches;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialize all of our views
        init(view);
        setListeners();
        loadData();
    }

    private void init(View view) {
        context = getActivity();

        // setup live matches
        recyclerViewLiveMatches = view.findViewById(R.id.recycler_view_live_matches);
        tvViewAllLiveMatches = view.findViewById(R.id.tvViewAllLiveMatches);
        recyclerViewLiveMatches.setLayoutManager((new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false)));
        liveMatchesAdapter = new LiveMatchesAdapter(getActivity(), DataHelper.getLiveMatches(3), R.layout.row_view_live_match_home);
        recyclerViewLiveMatches.setAdapter(liveMatchesAdapter);

        // setup upcoming matches
        recyclerViewUpcomingMatches = view.findViewById(R.id.recycler_view_fixtures);
        tvViewAllUpcomingMatches = view.findViewById(R.id.tv_all_upcoming_matches);
        recyclerViewUpcomingMatches.setLayoutManager((new LinearLayoutManager(getActivity())));

    }

    private void setListeners() {
        tvViewAllLiveMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveMatchesActivity.class);
                startActivity(intent);
            }
        });

        tvViewAllUpcomingMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FixturesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        DataHelper.getFixturesByApi(40, 2023, "2023-08-21", "2023-09-21", new UpcomingFixturesListener() {
            @Override
            public void onUpcomingFixturesLoaded(List<UpcomingFixture> upcomingFixtureList) {
                fixturesAdapter = new FixturesAdapter(getActivity(), upcomingFixtureList, R.layout.row_view_fixtures);
                recyclerViewUpcomingMatches.setAdapter(fixturesAdapter);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "Failed to load Data", Toast.LENGTH_LONG).show();
            }
        });

    }
}
