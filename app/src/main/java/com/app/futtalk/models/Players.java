package com.app.futtalk.models;

public class Players {
    private String name;

    private int age;

    private String nationality;

    private String position;

    private String playerImage;

    public Players(){

    }
    public Players(String name, int age, String nationality, String position, String playerImage){

        this.name= name;
        this.age=age;
        this.nationality= nationality;
        this.position= position;
        this.playerImage= playerImage;

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

    public String getNationality(String nationality) {
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

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }
}
