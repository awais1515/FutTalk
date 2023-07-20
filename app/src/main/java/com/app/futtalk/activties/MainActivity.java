package com.app.futtalk.activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.futtalk.R;
import com.app.futtalk.fragments.FixturesFragment;
import com.app.futtalk.fragments.HomeFragment;
import com.app.futtalk.fragments.ResultsFragment;
import com.app.futtalk.fragments.TeamsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView btnMenu;
    HomeFragment homeFragment;
    ResultsFragment resultsFragment;
    TeamsFragment teamsFragment;
    FixturesFragment fixturesFragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListeners();
        setupDrawer();
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.btnMenu);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        homeFragment = new HomeFragment();
        resultsFragment = new ResultsFragment();
        teamsFragment = new TeamsFragment();
        fixturesFragment = new FixturesFragment();
        loadFragment(homeFragment);
    }

    private void setListeners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_home) {
                    loadFragment(homeFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_teams) {
                    loadFragment(teamsFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_fixtures) {
                    loadFragment(fixturesFragment);
                    return true;
                } else if (item.getItemId() == R.id.item_results) {
                    loadFragment(resultsFragment);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void setupDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}