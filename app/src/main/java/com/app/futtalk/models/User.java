package com.app.futtalk.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private String email;
    private String profileUrl;
    private String bio;
    private List<String> favourites = new ArrayList<>();

    public User() {
    }

    public User(String id, String name, String email, String profileUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }
}
