package com.app.futtalk.models;

public class Player {
    private String name;

    private int age;

    private String nationality;

    private String position;

    private String photo;

    public Player(){

    }
    public Player(String name, int age, String nationality, String position, String photo){

        this.name= name;
        this.age=age;
        this.nationality= nationality;
        this.position= position;
        this.photo = photo;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
