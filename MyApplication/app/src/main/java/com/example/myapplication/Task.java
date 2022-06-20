package com.example.myapplication;

public class Task {
    String taskId;
    String name;
    String link;
    LevelsData level;
    String recId;
    String userId;
    String text;

    public Task(String taskId, String name, String link, LevelsData level, String recId, String userId, String text) {
        this.taskId = taskId;
        this.name = name;
        this.link = link;
        this.level = level;
        this.recId = recId;
        this.userId = userId;
        this.text = text;
    }

    public Task() {
    }
}
