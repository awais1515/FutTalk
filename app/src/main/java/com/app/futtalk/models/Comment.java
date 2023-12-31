package com.app.futtalk.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comment implements Serializable {
    private String id;
    private String uid;
    private String text;
    private String dateTime;

    private List<Reply> replies = new ArrayList<>();




    public Comment() {

    }

    public Comment(String id, String uid, String text, String dateTime) {
        this.id = id;
        this.uid= uid;
        this.text= text;
        this.dateTime= dateTime;
    }

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

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
