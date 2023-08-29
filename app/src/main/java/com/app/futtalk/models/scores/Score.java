package com.app.futtalk.models.scores;

import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("halftime")
    private HalfTime halfTime;
    @SerializedName("fulltime")
    private FullTime fullTime;
    @SerializedName("extratime")
    private ExtraTime extraTime;
    @SerializedName("penalty")
    private Penalty penalty;

    public HalfTime getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(HalfTime halfTime) {
        this.halfTime = halfTime;
    }

    public FullTime getFullTime() {
        return fullTime;
    }

    public void setFullTime(FullTime fullTime) {
        this.fullTime = fullTime;
    }

    public ExtraTime getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(ExtraTime extraTime) {
        this.extraTime = extraTime;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }
}
