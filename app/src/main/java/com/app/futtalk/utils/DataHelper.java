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
import com.app.futtalk.models.LeagueInfo;
import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.models.Player;
import com.app.futtalk.models.Results;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.UpcomingFixture;
import com.app.futtalk.models.UpcomingMatchOld;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHelper {

    public static List<LiveMatch> getLiveMatches(int count) {

        LiveMatch liveMatch1 = new LiveMatch();
        liveMatch1.setId("1");
        liveMatch1.setHomeTeam(getAllTeams().get(0));
        liveMatch1.setAwayTeam(getAllTeams().get(1));
        liveMatch1.setMinutes(80);
        liveMatch1.setLeagueName("Royal Premium League");
        liveMatch1.setVenue("Emirates Stadium London");
        liveMatch1.setDate("Jul 21");
        liveMatch1.setHomeTeamScore(2);
        liveMatch1.setAwayTeamScore(1);

        LiveMatch liveMatch2 = new LiveMatch();
        liveMatch2.setId("2");
        liveMatch2.setHomeTeam(getAllTeams().get(2));
        liveMatch2.setAwayTeam(getAllTeams().get(3));
        liveMatch2.setMinutes(43);
        liveMatch2.setLeagueName("Spain Premium League");
        liveMatch2.setVenue("Estadio Da Luz");
        liveMatch2.setDate("Jul 21");
        liveMatch2.setHomeTeamScore(1);
        liveMatch2.setAwayTeamScore(3);

        List<LiveMatch> allMatches =  Arrays.asList(liveMatch1,liveMatch2,liveMatch1,liveMatch2, liveMatch1,liveMatch2,liveMatch1,liveMatch2, liveMatch1,liveMatch2,liveMatch1,liveMatch2);

        if (count >= allMatches.size()) {
            return  allMatches;
        } else {
            List<LiveMatch> liveMatches = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                liveMatches.add(allMatches.get(i));
            }
            return liveMatches;
        }
    }

    public static List<UpcomingMatchOld> getUpComingMatches(int count) {

        UpcomingMatchOld upComingMatch1Old = new UpcomingMatchOld();
        upComingMatch1Old.setId("1");
        upComingMatch1Old.setHomeTeam(getAllTeams().get(0));
        upComingMatch1Old.setAwayTeam(getAllTeams().get(1));
        upComingMatch1Old.setTime("07:30 ");
        upComingMatch1Old.setLeagueName("Royal Premium League");
        upComingMatch1Old.setVenue("Emirates Stadium London");
        upComingMatch1Old.setDate("Jul 21");

        UpcomingMatchOld upcomingMatchOld2 = new UpcomingMatchOld();
        upcomingMatchOld2.setId("2");
        upcomingMatchOld2.setHomeTeam(getAllTeams().get(2));
        upcomingMatchOld2.setAwayTeam(getAllTeams().get(3));
        upcomingMatchOld2.setTime("16:30");
        upcomingMatchOld2.setLeagueName("Spain Premium League");
        upcomingMatchOld2.setVenue("Estadio Da Luz");
        upcomingMatchOld2.setDate("Jul 21");

        List<UpcomingMatchOld> allUpcomingMatchOlds =  Arrays.asList(upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2, upComingMatch1Old, upcomingMatchOld2);

        if (count >= allUpcomingMatchOlds.size()) {
            return allUpcomingMatchOlds;
        } else {
            List<UpcomingMatchOld> upcomingMatchOlds = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                upcomingMatchOlds.add(allUpcomingMatchOlds.get(i));
            }
            return upcomingMatchOlds;
        }
    }

    // synchronized call (flow didn't break)
    public static List<Team> getAllTeams() {
        Team team1 = new Team(1, "Barcelona", TeamLogoUrls.BARCELONA);
        Team team2 = new Team(2, "Real Madrid", TeamLogoUrls.REAL_MADRID);
        Team team3 = new Team(3, "Liver Pool", TeamLogoUrls.LIVER_POOL);
        Team team4 = new Team(4, "Manchester United", TeamLogoUrls.MANCHESTER_UNITED);
        return Arrays.asList(team1,team2,team3,team4);
    }

    public static List<Results> getResults(int count) {

        Results results1 = new Results();
        results1.setId("1");
        results1.setHomeTeam(getAllTeams().get(0));
        results1.setAwayTeam(getAllTeams().get(1));
        results1.setLeagueName("Royal Premium League");
        results1.setVenue("Emirates Stadium London");
        results1.setDate("Jul 21");
        results1.setHomeTeamScore(3);
        results1.setAwayTeamScore(1);

        Results results2 = new Results();
        results2.setId("2");
        results2.setHomeTeam(getAllTeams().get(2));
        results2.setAwayTeam(getAllTeams().get(3));
        results2.setLeagueName("Spain Premium League");
        results2.setVenue("Estadio Da Luz");
        results2.setDate("Jul 21");
        results2.setHomeTeamScore(1);
        results2.setAwayTeamScore(3);

        List<Results> allResults =  Arrays.asList(results1,results2,results1,results2, results1,results2,results1,results2, results1,results2,results1,results2);

        if (count >= allResults.size()) {
            return  allResults;
        } else {
            List<Results> results = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                results.add(allResults.get(i));
            }
            return results;
        }
    }

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
                List<UpcomingFixture> allUpcomingFixtures = new ArrayList<>();
                for (Map map: apiResponse.getResponse()){
                    Gson gson= new Gson();
                    String jsonString= gson.toJson(map);
                    UpcomingFixture upcomingFixture= gson.fromJson(jsonString, UpcomingFixture.class);
                    allUpcomingFixtures.add(upcomingFixture);
                }
                upcomingFixturesListener.onUpcomingFixturesLoaded(allUpcomingFixtures);
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

}
