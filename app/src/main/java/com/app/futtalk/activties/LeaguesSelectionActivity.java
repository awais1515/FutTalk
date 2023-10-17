package com.app.futtalk.activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LeaguesAdapter;
import com.app.futtalk.api.LeaguesInfoDataListener;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.utils.DataHelper;
import com.app.futtalk.utils.References;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaguesSelectionActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewLeaguesSelection;

    private LeaguesAdapter leaguesAdapter;

    private SearchView searchview;

    private List<LeagueInfo> allLeagues;

    ImageView ivBack;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues_selection);
        init();
        if (DataHelper.isAdmin()) {
            loadLeaguesFromApi();
        } else {
            loadLeaguesFromFirebase();
        }
       setListeners();
    }

    private void init(){
        context = this;
        recyclerViewLeaguesSelection = findViewById(R.id.recycler_view_league_selection);
        recyclerViewLeaguesSelection.setLayoutManager((new LinearLayoutManager(context)));
        searchview = findViewById(R.id.SearchViewCountries);
        ivBack = findViewById(R.id.ivBack);

    }

    private void setListeners(){

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<LeagueInfo> filteredLeagues = filterLeagues(query, allLeagues);
                leaguesAdapter.setLeaguesList(filteredLeagues);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() == 0) {
                    leaguesAdapter.setLeaguesList(allLeagues);
                }
                return false;
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private List<LeagueInfo> filterLeagues(String searchQuery, List<LeagueInfo> allLeagues) {
        List<LeagueInfo> filteredList = new ArrayList<>();
        for (LeagueInfo leagueInfo: allLeagues) {
            if (leagueInfo.getLeague().getName().toLowerCase().trim().startsWith(searchQuery.toLowerCase().trim())
                || leagueInfo.getCountry().getName().toLowerCase().trim().startsWith(searchQuery.toLowerCase().trim())){
                filteredList.add(leagueInfo);
            }
        }
        return filteredList;
    }

    private void loadLeaguesFromFirebase() {
        FirebaseDatabase.getInstance().getReference(References.LEAGUES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allLeagues = new ArrayList<>();
               for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                   LeagueInfo league = dataSnapshot.getValue(LeagueInfo.class);
                   allLeagues.add(league);
               }
                leaguesAdapter = new LeaguesAdapter(context, allLeagues, R.layout.row_league_selection, true);
                recyclerViewLeaguesSelection.setAdapter(leaguesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadLeaguesFromApi() {
        DataHelper.getLeaguesFromApi(2023, new LeaguesInfoDataListener() {
            @Override
            public void onLeaguesLoaded(List<LeagueInfo> leagueList) {
                allLeagues = leagueList;
                leaguesAdapter = new LeaguesAdapter(context, allLeagues, R.layout.row_league_selection, true);
                recyclerViewLeaguesSelection.setAdapter(leaguesAdapter);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }


}