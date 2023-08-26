package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeaguesAdapter;
import com.app.futtalk.api.CountriesDataListener;
import com.app.futtalk.api.LeaguesInfoDataListener;
import com.app.futtalk.models.Country;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class LeaguesSelectionActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewLeaguesSelection;

    private LeaguesAdapter leaguesAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues_selection);
        init();
       loadLeagues();
    }

    private void init(){
        context=this;
        recyclerViewLeaguesSelection = findViewById(R.id.recycler_view_league_selection);
        recyclerViewLeaguesSelection.setLayoutManager((new LinearLayoutManager(this)));
        // in leagues adapter you will have to pass true for selectionMode


    }


    private void loadLeagues() {
        DataHelper.getLeaguesFromApi(2023, new LeaguesInfoDataListener() {
            @Override
            public void onLeaguesLoaded(List<LeagueInfo> leaguesInfoList) {
                Log.d("abc", "leagues loaded");
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}