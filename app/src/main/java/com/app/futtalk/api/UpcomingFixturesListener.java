package com.app.futtalk.api;

import com.app.futtalk.models.UpcomingFixture;

import java.util.List;

public interface UpcomingFixturesListener {
    void onUpcomingFixturesLoaded(List<UpcomingFixture> upcomingFixtureList);
    void onFailure(String message);
}
