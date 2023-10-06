package com.app.futtalk.utils;

import android.util.Log;

import com.app.futtalk.api.CountriesDataListener;
import com.app.futtalk.api.LeaguesInfoDataListener;
import com.app.futtalk.api.PlayersDataListener;
import com.app.futtalk.api.Service;
import com.app.futtalk.api.ApiResponse;
import com.app.futtalk.api.TeamsDataListener;
import com.app.futtalk.api.UpcomingFixturesListener;
import com.app.futtalk.models.Country;
import com.app.futtalk.models.FeedPost;
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.models.Player;
import com.app.futtalk.models.Results;
import com.app.futtalk.models.StatusFlags;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.FixtureData;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHelper {

    private static List<FixtureData> sharedFixturesList = new ArrayList<>();
    private static List<FeedPost> sharedFeaturedPostsList = new ArrayList<>();

    public static void getAllTeamsFromApi (int leagueId, int season, TeamsDataListener teamsDataListener) {

        Log.d("abc", "ready to make a call");
        // Async calls (flow is broken here.. why? because we made a request to the internet for fetching data and we don't know how much time it's going to take
        Service.getInstance().getMyApi().getTeams(leagueId, season).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                Log.d("abc", "data loaded");
                List<Team> allTeams = new ArrayList<>();
                for (Map map: apiResponse.getResponse()) {
                    // get json of the team
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(map.get("team"));
                    Team team = gson.fromJson(jsonString, Team.class);
                    allTeams.add(team);
                }
                Log.d("abc", "loaded all the teams in our list");
                teamsDataListener.onTeamsLoaded(allTeams);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("abc", "error");
                teamsDataListener.onFailure(t.getMessage());
            }
        });
    }

    public static void getPlayersFromApi(int teamId, int season, PlayersDataListener playersDataListener) {

        Service.getInstance().getMyApi().getPlayers(teamId, season).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                List<Player> allPlayers = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString= gson.toJson(map.get("player"));
                    Player player= gson.fromJson(jsonString, Player.class);
                    List<LinkedTreeMap> statistics = (List<LinkedTreeMap>) map.get("statistics");
                    if (statistics != null && statistics.size() > 0) {
                        Map firstLeague = statistics.get(0);
                        Map  games = (LinkedTreeMap) firstLeague.get("games");
                        String position = (String) games.get("position");
                        player.setPosition(position);
                    }
                    allPlayers.add(player);
                }
                playersDataListener.onPlayersLoaded(allPlayers);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                playersDataListener.onFailure(t.getMessage());
            }
        });

    }

    public static void getFixturesFromApi(int leagueId, int season, String fromDate, String toDate, UpcomingFixturesListener upcomingFixturesListener) {
        Service.getInstance().getMyApi().getFixtures(leagueId, season, fromDate, toDate).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                List<FixtureData> allFixtureData = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString= gson.toJson(map);
                    FixtureData fixtureData = gson.fromJson(jsonString, FixtureData.class);
                    allFixtureData.add(fixtureData);
                }
                upcomingFixturesListener.onUpcomingFixturesLoaded(allFixtureData);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                upcomingFixturesListener.onFailure(t.getMessage());
            }
        });
    }

    public static void getAllFixturesFromApi(int last, UpcomingFixturesListener upcomingFixturesListener) {
        Service.getInstance().getMyApi().getAllFixtures(last).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                List<FixtureData> allFixtureData = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString= gson.toJson(map);
                    FixtureData fixtureData = gson.fromJson(jsonString, FixtureData.class);
                    allFixtureData.add(fixtureData);
                }
                upcomingFixturesListener.onUpcomingFixturesLoaded(allFixtureData);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                upcomingFixturesListener.onFailure(t.getMessage());
            }
        });
    }

    public static void getCountriesFromApi(CountriesDataListener countriesDataListener) {
        Service.getInstance().getMyApi().getCountries().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                List<Country> allCountries = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString = gson.toJson(map);
                    Country country = gson.fromJson(jsonString, Country.class);
                    allCountries.add(country );
                }
                countriesDataListener.onCountriesLoaded(allCountries);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                countriesDataListener.onFailure(t.getMessage());
            }
        });
    }


    public static void getLeaguesFromApi(int season, LeaguesInfoDataListener leaguesInfoDataListener) {
        Service.getInstance().getMyApi().getLeagues(season).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                List<LeagueInfo> leaguesInfoList = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString = gson.toJson(map);
                    LeagueInfo leagueInfo = gson.fromJson(jsonString, LeagueInfo.class);
                    leaguesInfoList.add(leagueInfo);
                }
                leaguesInfoDataListener.onLeaguesLoaded(leaguesInfoList);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                leaguesInfoDataListener.onFailure(t.getMessage());
            }
        });
    }

    public static void setSharedFixturesList(List<FixtureData> sharedFixturesList) {
        DataHelper.sharedFixturesList = sharedFixturesList;
    }

    public static List<FeedPost> getSharedFeaturedPostsList() {
        return sharedFeaturedPostsList;
    }

    public static void setSharedFeaturedPostsList(List<FeedPost> sharedFeaturedPostsList) {
        DataHelper.sharedFeaturedPostsList = sharedFeaturedPostsList;
    }

    public static void deleteFeaturedPost(FeedPost feedPost) {
        for (int i = 0; i < sharedFeaturedPostsList.size(); i++) {
            if (sharedFeaturedPostsList.get(i).getId().equals(feedPost.getId())) {
                sharedFeaturedPostsList.remove(i);
                break;
            }
        }
    }

    public static List<FixtureData> getUpComingMatches() {
        List<FixtureData> upComingMatches = new ArrayList<>();
        for (FixtureData fixtureData: sharedFixturesList) {
            if (StatusFlags.upComingMatches.contains(fixtureData.getFixture().getStatus().getShortDescription())) {
                upComingMatches.add(fixtureData);
            }
        }
        Collections.reverse(upComingMatches);
        return upComingMatches;
    }

    public static List<FixtureData> getLiveMatches() {
        List<FixtureData> liveMatches = new ArrayList<>();
        for (FixtureData fixtureData: sharedFixturesList) {
            if (StatusFlags.inProgressMatches.contains(fixtureData.getFixture().getStatus().getShortDescription())) {
                liveMatches.add(fixtureData);
            }
        }
        return liveMatches;
    }


    public static List<FixtureData> getCompletedMatches() {
        List<FixtureData> completedMatches = new ArrayList<>();
        for (FixtureData fixtureData: sharedFixturesList) {
            if (StatusFlags.completedMatches.contains(fixtureData.getFixture().getStatus().getShortDescription())) {
                completedMatches.add(fixtureData);
            }
        }
        return completedMatches;
    }

    public static boolean isAdmin() {
        String adminEmail = "dante@gmail.com";
        if (FirebaseUtils.CURRENT_USER.getEmail().equals(adminEmail)) {
            return true;
        }
        return false;
    }

    public static boolean isOwner(String postUserID) {
        if (FirebaseUtils.CURRENT_USER.getId().equals(postUserID)) {
            return true;
        }
        return false;
    }

}
