package com.app.futtalk.utils;

import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.models.Player;
import com.app.futtalk.models.Results;
import com.app.futtalk.models.Team;
import com.app.futtalk.models.UpcomingMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<UpcomingMatch> getUpComingMatches(int count) {

        UpcomingMatch upComingMatch1 = new UpcomingMatch();
        upComingMatch1.setId("1");
        upComingMatch1.setHomeTeam(getAllTeams().get(0));
        upComingMatch1.setAwayTeam(getAllTeams().get(1));
        upComingMatch1.setTime("07:30 ");
        upComingMatch1.setLeagueName("Royal Premium League");
        upComingMatch1.setVenue("Emirates Stadium London");
        upComingMatch1.setDate("Jul 21");

        UpcomingMatch UpcomingMatch2 = new UpcomingMatch();
        UpcomingMatch2.setId("2");
        UpcomingMatch2.setHomeTeam(getAllTeams().get(2));
        UpcomingMatch2.setAwayTeam(getAllTeams().get(3));
        UpcomingMatch2.setTime("16:30");
        UpcomingMatch2.setLeagueName("Spain Premium League");
        UpcomingMatch2.setVenue("Estadio Da Luz");
        UpcomingMatch2.setDate("Jul 21");

        List<UpcomingMatch> allUpcomingMatches =  Arrays.asList(upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2,upComingMatch1,UpcomingMatch2);

        if (count >= allUpcomingMatches.size()) {
            return  allUpcomingMatches;
        } else {
            List<UpcomingMatch> upcomingMatches = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                upcomingMatches.add(allUpcomingMatches.get(i));
            }
            return upcomingMatches;
        }
    }

    public static List<Team> getAllTeams() {
        Team team1 = new Team("1", "Barcelona", TeamLogoUrls.BARCELONA);
        Team team2 = new Team("2", "Real Madrid", TeamLogoUrls.REAL_MADRID);
        Team team3 = new Team("3", "Liver Pool", TeamLogoUrls.LIVER_POOL);
        Team team4 = new Team("4", "Manchester United", TeamLogoUrls.MANCHESTER_UNITED);
        return Arrays.asList(team1,team2,team3,team4);
    }

    public static List<Player> getPlayerData(int count) {

        Player player1 = new Player();
        player1.setName("L. Messi");
        player1.setAge(35);
        player1.setNationality("Argentina");
        player1.setPosition("CF");


        return Arrays.asList(player1,player1,player1,player1,player1,player1,player1,player1,player1,player1,player1,player1);
    }

    public static List<Team> getTeamData(int count) {

        Team teams = new Team();
        teams.setName("Barcelona");
        teams.getLogo();




        List<Team> teamList =  Arrays.asList(teams);

        if (count >= teamList.size()) {
            return  teamList;
        } else {
            List<Team> teamsList = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                teamsList.add(teamsList.get(i));
            }
            return teamsList;
        }
    }



    public static List<Results> getResults(int count) {

        Results results1 = new Results();
        results1.setId("1");
        results1.setHomeTeam(String.valueOf(getAllTeams().get(0)));
        results1.setAwayTeam(String.valueOf(getAllTeams().get(1)));
        results1.setLeagueName("Royal Premium League");
        results1.setVenue("Emirates Stadium London");
        results1.setDate("Jul 21");
        results1.setHomeTeamScore(3);
        results1.setAwayTeamScore(1);

        Results results2 = new Results();
        results2.setId("2");
        results2.setHomeTeam(String.valueOf(getAllTeams().get(2)));
        results2.setAwayTeam(String.valueOf(getAllTeams().get(3)));
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
}
