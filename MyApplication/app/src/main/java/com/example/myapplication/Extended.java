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

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public void setToken(String newToken) {
        token = newToken;
    }

    public void setUserId(String newUserId) {
        userId = newUserId;
    }

    public void postData(String addUrl, JSONObject object) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Enter the correct url for your api service site
        String url = getResources().getString(R.string.string_url) + addUrl;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //resResponse = response;
                        System.out.println("String Response : "+ response.toString());
                        //result = response.toString();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resResponse = new JSONObject();
                System.out.println("Error getting response");
                //result = "error";
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
