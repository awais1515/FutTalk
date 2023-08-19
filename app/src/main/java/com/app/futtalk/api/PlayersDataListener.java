package com.app.futtalk.api;

import com.app.futtalk.models.Player;
import com.app.futtalk.models.Team;

import java.util.List;

public interface PlayersDataListener {
    void onPlayersLoaded(List<Player> playerList);
    void onFailure(String message);
}
