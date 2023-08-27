package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeaguesAdapter;
import com.app.futtalk.api.CountriesDataListener;
import com.app.futtalk.api.LeaguesInfoDataListener;
import com.app.futtalk.models.Country;
import com.app.futtalk.models.League;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class LeaguesSelectionActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewLeaguesSelection;

    private LeaguesAdapter leaguesAdapter;

    private SearchView searchview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues_selection);
        init();
       loadLeagues();
       setListeners();
    }

    private void init(){
        context=this;
        recyclerViewLeaguesSelection = findViewById(R.id.recycler_view_league_selection);
        recyclerViewLeaguesSelection.setLayoutManager((new LinearLayoutManager(this)));
        searchview = findViewById(R.id.SearchViewCountries);

    }

    private void setListeners(){
        searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectCountryActivity.class);
                startActivity(intent);
            }
        });

    }


    private void loadLeagues() {
        DataHelper.getLeaguesFromApi(2023, new LeaguesInfoDataListener() {
            @Override
            public void onLeaguesLoaded(List<LeagueInfo> leagueList) {
                leaguesAdapter = new LeaguesAdapter(context, leagueList, R.layout.row_league_selection, false);
                recyclerViewLeaguesSelection.setAdapter(leaguesAdapter);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}