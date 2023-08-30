package com.app.futtalk.models;

import com.app.futtalk.models.scores.Score;

public class FixtureData {
    private Fixture fixture;
    private League league;
    private FixtureTeams teams;
    private Score score;
    private Goals goals;


    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public FixtureTeams getTeams() {
        return teams;
    }

    public void setTeams(FixtureTeams teams) {
        this.teams = teams;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }
}
