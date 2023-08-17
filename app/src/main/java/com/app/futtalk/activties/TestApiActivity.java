package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.futtalk.R;
import com.app.futtalk.utils.DataHelper;

public class TestApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        findViewById(R.id.btnFetchTeams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper.getAllTeamsFromApi();
            }
        });

        findViewById(R.id.btnFetchPlayers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper.getPlayersFromApi(529, 2023);
            }
        });

    }
}