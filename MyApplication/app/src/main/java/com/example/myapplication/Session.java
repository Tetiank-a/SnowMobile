package com.example.myapplication;

public class Session {

    String id;
    String recId;
    String instructorId;
    String taskId;
    String userId;
    String dtStart;
    String dtFinish;
    Location location;

    public Session(String id, String recId, String instructorId, String taskId, String userId,
                   String dtStart, String dtFinish, Location location) {
        this.id = id;
        this.recId = recId;
        this.instructorId = instructorId;
        this.taskId = taskId;
        this.userId = userId;
        this.dtStart = dtStart;
        this.dtFinish = dtFinish;
        this.location = location;
    }


}
