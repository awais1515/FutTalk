package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.api.PlayersDataListener;
import com.app.futtalk.api.Service;
import com.app.futtalk.api.ApiResponse;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.models.Player;
import com.app.futtalk.models.Team;
import com.app.futtalk.utils.DataHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestApiActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        findViewById(R.id.btnFetchTeams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper.getAllTeamsFromApi(140, 2023, new TeamsDataListener() {
                    @Override
                    public void onTeamsLoaded(List<Team> teamList) {
                        // we can set our adpater
                    }

                    @Override
                    public void onFailure(String message) {
                        // we can show error
                    }
                });
                // you need a list of Teams because you want to set the teams in the adapter
            }
        });

        findViewById(R.id.btnFetchPlayers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DataHelper.getPlayersFromApi(529, 2023);
                DataHelper.getPlayersFromApi(159, 2023, new PlayersDataListener() {
                    @Override
                    public void onPlayersLoaded(List<Player> playerList) {
                        // set data to adapter
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        });

    }

}