package com.app.futtalk.api.response;

import java.util.ArrayList;
import java.util.List;

public class TeamsResponse {
   private List<TeamData> teamData = new ArrayList<>();

    public List<TeamData> getTeamData() {
        return teamData;
    }

    public void setTeamData(List<TeamData> teamData) {
        this.teamData = teamData;
    }
}
