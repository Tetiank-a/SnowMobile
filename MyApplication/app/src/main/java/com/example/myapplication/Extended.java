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

public class Extended extends Application {
    private String token;
    private String userId;
    private String role;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String newRole) {
        role = newRole;
    }

    public void setToken(String newToken) {
        token = newToken;
    }

    public void setUserId(String newUserId) {
        userId = newUserId;
    }

}
