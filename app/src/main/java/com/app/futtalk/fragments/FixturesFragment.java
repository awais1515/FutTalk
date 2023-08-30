package com.app.futtalk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class FixturesFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewFixtures;
    private FixturesAdapter fixturesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fixtures, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        DataHelper.getFixturesFromApi(40, 2023, "2023-08-21", "2023-09-21", new UpcomingFixturesListener() {
            @Override
            public void onUpcomingFixturesLoaded(List<FixtureData> fixtureDataList) {
                getView().findViewById(R.id.pbLoader).setVisibility(View.GONE);
                if (fixtureDataList.size() == 0) {
                    getView().findViewById(R.id.tvNoDataFound).setVisibility(View.VISIBLE);
                }
                fixturesAdapter = new FixturesAdapter(context, fixtureDataList,R.layout.row_view_fixtures);
                recyclerViewFixtures.setAdapter(fixturesAdapter);
            }

            @Override
            public void onFailure(String message) {
                getView().findViewById(R.id.pbLoader).setVisibility(View.GONE);
            }
        });
    }

    private void init(View view) {
        context = getActivity();
        recyclerViewFixtures = view.findViewById(R.id.recycler_view_fixtures);
        recyclerViewFixtures.setLayoutManager((new LinearLayoutManager(getActivity())));

    }
}

