package com.app.futtalk.api.response;

import com.app.futtalk.models.Team;

public class TeamData extends CallResponse{
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
