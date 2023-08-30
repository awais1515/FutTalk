package com.app.futtalk.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    String HOST_NAME = "api-football-v1.p.rapidapi.com";
    String API_KEY = "9e340a2656mshb4d8111c15967d8p1ca87bjsn0333f8e6ccde";
    String BASE_URL = "https://api-football-v1.p.rapidapi.com/v3/";

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })
    @GET("teams")
    Call <ApiResponse> getTeams(@Query("league") int leagueId, @Query("season") int season);

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })
    @GET("players")
    Call <ApiResponse> getPlayers(@Query("team") int teamId, @Query("season") int season);

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })
    @GET("fixtures")
    Call<ApiResponse> getFixtures(@Query("league") int league, @Query("season") int season, @Query("from") String from, @Query("to") String to);

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })

    @GET("fixtures")
    Call<ApiResponse> getAllFixtures(@Query("last") int last);

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })

    @GET("countries")
    Call<ApiResponse> getCountries();

    @Headers({
            "x-rapidapi-host: " + HOST_NAME,
            "x-rapidapi-key: " + API_KEY
    })
    @GET("leagues")
    Call<ApiResponse> getLeagues(@Query("season") int season);

}
