package com.app.futtalk.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedPost implements Serializable{

    private String id;
    private String uid;
    private String text;
    private String dateTime;
    private String storyImageURL;

    private String storyVideoURL;
    private List<Comment> comments = new ArrayList<>();
    private List<String> likes = new ArrayList<>();
    private StoryTypes storyType;
    @Exclude
    private List<Report> reports = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getStoryImageURL() {
        return storyImageURL;
    }

    public void setStoryImageURL(String storyImageURL) {
        this.storyImageURL = storyImageURL;
    }

    public String getStoryVideoURL() {return storyVideoURL;}

    public void setStoryVideoURL(String storyVideoURL) {
        this.storyVideoURL = storyVideoURL;
    }

    public StoryTypes getStoryType() {
        return storyType;
    }

    public void setStoryType(StoryTypes storyType) {
        this.storyType = storyType;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
