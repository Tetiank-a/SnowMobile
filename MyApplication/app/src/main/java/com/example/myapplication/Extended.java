package com.example.myapplication;

import android.app.Application;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Extended extends Application {
    private String token;
    private String userId;
    private String role;
    private ArrayList<LevelsData> levels;
    private Task selectedTask;
    private ArrayList<Location> locations;
    private ArrayList<Session> sessions;
    private ArrayList<FilteredSession> filteredSessions = new ArrayList<>();

    // may be "eng" or "ukr"
    private String lang = "eng";

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public Task getSelectedTask() { return selectedTask; }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<LevelsData> getLevels() { return levels; }

    public void setRole(String newRole) {
        role = newRole;
    }

    public void setToken(String newToken) {
        token = newToken;
    }

    public void setUserId(String newUserId) {
        userId = newUserId;
    }

    public void setLevels(ArrayList<LevelsData> newLevels) { levels = newLevels; }

    public void setFilteredSessions(ArrayList<FilteredSession> newFilteredSessions) { filteredSessions = newFilteredSessions; }

    public ArrayList<FilteredSession> getFilteredSessions() { return filteredSessions; }

    public void updateFilteredSession(int position) {
        filteredSessions.remove(position);
    }

}
