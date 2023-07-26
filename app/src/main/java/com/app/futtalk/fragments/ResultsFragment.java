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
import com.app.futtalk.adapters.TeamsAdapter;
import com.app.futtalk.utils.DataHelper;

public class ResultsFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerViewResults;
    private TeamsAdapter resultsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

     private void init(){
         context=getActivity();
         recyclerViewResults.setLayoutManager((new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false)));
         resultsAdapter = new TeamsAdapter(getActivity(), DataHelper.getTeamData(7), R.layout.row_view_results);
         recyclerViewResults.setAdapter(resultsAdapter);
     }


}
