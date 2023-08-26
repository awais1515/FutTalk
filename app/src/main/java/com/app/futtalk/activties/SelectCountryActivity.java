package com.app.futtalk.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.app.futtalk.R;
import com.app.futtalk.api.CountriesDataListener;
import com.app.futtalk.models.Country;
import com.app.futtalk.utils.DataHelper;

import java.util.List;

public class SelectCountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        //Todo prepare layout file for the country activity and prepare row_view for the country and write adapter for the countries

    }


    private void loadCountries() {
        DataHelper.getCountriesFromApi(new CountriesDataListener() {
            @Override
            public void onCountriesLoaded(List<Country> countryList) {
                Log.d("abc", "Countries loaded");
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}