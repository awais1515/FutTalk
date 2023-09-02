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
import androidx.viewpager.widget.ViewPager;

import com.app.futtalk.R;
import com.app.futtalk.adapters.FixturesAdapter;
import com.app.futtalk.adapters.ViewPagerAdapter;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.FixtureData;
import com.app.futtalk.utils.DataHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FixturesFragment extends Fragment {

    private Context context;
    private ViewPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fixtures, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        context = getActivity();
        ViewPager viewPager = getView().findViewById(R.id.viewPager);
        TabLayout tabLayout = getView().findViewById(R.id.tabLayout);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        // Attach the ViewPager to the TabLayout
        tabLayout.setupWithViewPager(viewPager);

        // Set the tab titles
        tabLayout.getTabAt(0).setText("Upcoming Matches");
        tabLayout.getTabAt(1).setText("Results");
    }
}

