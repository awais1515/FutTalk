package com.app.futtalk.fragments;

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
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.utils.DataHelper;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewLiveMatches;
    LiveMatchesAdapter liveMatchesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialize all of our views
        recyclerViewLiveMatches = view.findViewById(R.id.recycler_view_live_matches);
        recyclerViewLiveMatches.setLayoutManager((new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false)));
        liveMatchesAdapter = new LiveMatchesAdapter(getActivity(), DataHelper.getLiveMatches());
        recyclerViewLiveMatches.setAdapter(liveMatchesAdapter);
    }
}
