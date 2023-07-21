package com.app.futtalk.utils;

import com.app.futtalk.models.LiveMatch;
import com.app.futtalk.models.Team;

import java.util.Arrays;
import java.util.List;

public class DataHelper {

    public static List<LiveMatch> getLiveMatches() {

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

        return Arrays.asList(liveMatch1,liveMatch2,liveMatch1,liveMatch2);
    }

    public static List<Team> getAllTeams() {
        Team team1 = new Team("1", "Barcelona", TeamLogoUrls.BARCELONA);
        Team team2 = new Team("2", "Real Madrid", TeamLogoUrls.REAL_MADRID);
        Team team3 = new Team("3", "Liver Pool", TeamLogoUrls.LIVER_POOL);
        Team team4 = new Team("4", "Manchester United", TeamLogoUrls.MANCHESTER_UNITED);
        return Arrays.asList(team1,team2,team3,team4);
    }


}
