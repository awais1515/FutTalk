package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.futtalk.R;
import com.app.futtalk.adapters.CountriesAdapter;
import com.app.futtalk.api.CountriesDataListener;
import com.app.futtalk.models.Country;
import com.app.futtalk.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectCountryActivity extends AppCompatActivity {
    Context context;
    private RecyclerView recyclerViewCountries;
    ImageView ivBack;

    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        init();
        loadCountries();


    }

    private void init(){
        context = this;
        ivBack = findViewById(R.id.ivBack);
        recyclerViewCountries = findViewById(R.id.recycler_view_countries);
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(this));

    }


    private void loadCountries() {
        DataHelper.getCountriesFromApi(new CountriesDataListener() {
            @Override
            public void onCountriesLoaded(List<Country> countryList) {
                countriesAdapter= new CountriesAdapter(context, countryList, R.layout.row_country);
                recyclerViewCountries.setAdapter(countriesAdapter);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}