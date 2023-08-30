package com.app.futtalk.models;

import java.util.Arrays;
import java.util.List;

public class StatusFlags {
    public static final String NOT_STARTED = "NS";
    public static final String FIRST_HALF = "1H";
    public static final String HALF_TIME = "HT";
    public static final String SECOND_HALF = "2H";
    public static final String EXTRA_TIME = "ET";
    public static final String BREAK_TIME = "BT";
    public static final String PENALTY = "P";
    public static final String MATCH_INTERRUPTED = "P";
    public static final String FINISHED_REGULAR = "FT";
    public static final String FINISHED_AFTER_EXTRA_TIME = "AET";
    public static final String FINISHED_AFTER_PENALTY = "PEN";

    public static List<String> inProgressMatches = Arrays.asList(FIRST_HALF,HALF_TIME,SECOND_HALF,EXTRA_TIME,BREAK_TIME,PENALTY,MATCH_INTERRUPTED);
    public static List<String> upComingMatches = Arrays.asList(NOT_STARTED);
    public static List<String> completedMatches = Arrays.asList(FINISHED_REGULAR,FINISHED_AFTER_EXTRA_TIME,FINISHED_AFTER_PENALTY);

}
