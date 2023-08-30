package com.app.futtalk.models;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("long")
    private String longDescription;
    @SerializedName("short")
    private String shortDescription;
    private Integer elapsed;

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getElapsed() {
        return elapsed;
    }

    public void setElapsed(Integer elapsed) {
        this.elapsed = elapsed;
    }
}
