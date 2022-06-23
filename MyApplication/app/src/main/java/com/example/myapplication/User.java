package com.example.myapplication;

public class User {
    String id = "";
    String name = "";
    String email = "";
    String password = "";
    String repeatPassword = "";
    String levelID = "";

    public User(String name, String email, String password, String repeatPassword, String levelID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.levelID = levelID;
    }

    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.repeatPassword = "";
        this.levelID = "";
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
