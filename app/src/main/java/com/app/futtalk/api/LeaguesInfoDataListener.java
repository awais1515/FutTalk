package com.app.futtalk.api;

import com.app.futtalk.models.Country;
import com.app.futtalk.models.LeagueInfo;

import java.util.List;

public interface LeaguesInfoDataListener {
    void onLeaguesLoaded(List<LeagueInfo> leaguesInfoList);
    void onFailure(String message);
}
