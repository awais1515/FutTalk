package com.app.futtalk.api;

import com.app.futtalk.models.Country;

import java.util.List;

public interface CountriesDataListener {
    void onCountriesLoaded(List<Country> countryList);
    void onFailure(String message);
}
