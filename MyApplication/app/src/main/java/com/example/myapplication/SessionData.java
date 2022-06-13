package com.example.myapplication;

public class SessionData {
    String token;
    String userID;
    String role;

    public SessionData(String token, String userID, String role) {
        this.token = token;
        this.userID = userID;
        this.role = role;
    }
}
