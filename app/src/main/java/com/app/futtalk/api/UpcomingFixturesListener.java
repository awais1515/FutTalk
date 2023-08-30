package com.app.futtalk.api;

import com.app.futtalk.models.FixtureData;

import java.util.List;

public interface UpcomingFixturesListener {
    void onUpcomingFixturesLoaded(List<FixtureData> fixtureDataList);
    void onFailure(String message);
}
