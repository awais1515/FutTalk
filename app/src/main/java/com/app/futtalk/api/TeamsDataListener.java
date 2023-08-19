package com.app.futtalk.api;

import com.app.futtalk.models.Team;

import java.util.List;

public interface TeamsDataListener {
    void onTeamsLoaded(List<Team> teamList);
    void onFailure(String message);
}
