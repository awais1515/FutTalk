package com.app.futtalk.api;

import com.app.futtalk.models.UpcomingFixture;

import java.util.List;

public interface UpcomingFixturesListener {
    void onUpcomingFixturesLoaded(List<UpcomingFixture> playerList);
    void onFailure(String message);
}
