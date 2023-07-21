package com.app.futtalk.models;

public class User {

    private String id;
    private String name;
    private String email;
    private String profileUrl;

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
}
