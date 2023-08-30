package com.app.futtalk.api;

import com.app.futtalk.models.Results;

import java.util.List;

public interface FixtureResultsListener {
    void onFixturesResultsLoaded(List<Results> fixtureResultsListener);
    void onFailure(String message);
}
