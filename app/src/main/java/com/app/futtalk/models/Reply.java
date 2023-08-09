package com.app.futtalk.models;

import java.io.Serializable;

public class Reply implements Serializable {
    private String id;
    private String uid;
    private String text;
    private String dateTime;

    public Reply(){

    }

    public Reply(String id, String uid, String text, String dateTime){
        id= this.id;
        uid= this.uid;
        text= this.text;
        dateTime= this.dateTime;

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

}

