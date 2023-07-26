package com.app.futtalk.models;

public class Results {

    private String id;
    private String leagueName;
    private String venue;
    private String date;
    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;





    public Results(){

    }



    public Results(String id, String leagueName, String venue, String date, Team homeTeam, Team awayTeam, int homeTeamScore, int awayTeamScore){
        this.id = id;
        this.leagueName = leagueName;
        this.venue = venue;
        this.date = date;
        this.homeTeam = String.valueOf(homeTeam);
        this.awayTeam = String.valueOf(awayTeam);
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;


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

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }
}


