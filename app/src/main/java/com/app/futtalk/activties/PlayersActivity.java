package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.adapters.PlayersAdapter;
import com.app.futtalk.api.PlayersDataListener;
import com.app.futtalk.models.Player;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerViewPlayers;
    private PlayersAdapter playersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        context=this;
        init();
        setListeners();
        loadData();
    }

    private void init(){

        recyclerViewPlayers = findViewById(R.id.recycler_view_Player_stats);
        recyclerViewPlayers.setLayoutManager((new LinearLayoutManager(this)));
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadData(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        DataHelper.getPlayersFromApi(140, 2023, new PlayersDataListener() {
            @Override
            public void onPlayersLoaded(List<Player> playerList) {
                playersAdapter= new PlayersAdapter(context, playerList, R.layout.row_players);
                recyclerViewPlayers.setAdapter(playersAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}