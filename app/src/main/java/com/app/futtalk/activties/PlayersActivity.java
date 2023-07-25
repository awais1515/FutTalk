package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.adapters.LiveMatchesAdapter;
import com.app.futtalk.adapters.PlayersAdapter;
import com.app.futtalk.utils.DataHelper;

public class PlayersActivity extends AppCompatActivity {

    private Context context;

    private RecyclerView recyclerViewPlayers;
    private PlayersAdapter playersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        context = this;
        recyclerViewPlayers = findViewById(R.id.recycler_view_Player_stats);
        recyclerViewPlayers.setLayoutManager((new LinearLayoutManager(this)));
        playersAdapter = new PlayersAdapter(this, DataHelper.getPlayerData(7), R.layout.row_players);
        recyclerViewPlayers. setAdapter(playersAdapter);
        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}