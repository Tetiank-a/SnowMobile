package com.example.myapplication;

public class FilteredSession {

    String id;
    User instructor;
    User student;
    String dtStart;
    String dtFinish;
    Location location;

    public FilteredSession(String id, User instructor, User student, String dtStart, String dtFinish, Location location) {
        this.id = id;
        this.instructor = instructor;
        this.student = student;
        this.dtStart = dtStart;
        this.dtFinish = dtFinish;
        this.location = location;
    }
}
