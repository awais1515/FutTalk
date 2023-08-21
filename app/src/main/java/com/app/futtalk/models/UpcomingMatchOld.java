package com.app.futtalk.models;

public class UpcomingMatchOld {

    private String id;
    private String leagueName;
    private String venue;
    private String date;
    private String time;
    private Team homeTeam;
    private Team awayTeam;


    public UpcomingMatchOld(String id, String leagueName, String venue, String date, String time, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.leagueName = leagueName;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public UpcomingMatchOld() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}
