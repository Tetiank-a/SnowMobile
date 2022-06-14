package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Api {

    SessionData sessionData;
    ArrayList<LevelsData> levelsData;

    public static final String SNOW_SCHOOL_API = "https://snowboard-school.herokuapp.com/api/";

    Context context;

    public Api(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(SessionData sessionData);

        void onResponse(ArrayList<LevelsData> levelsData);
    }



    public void Login(String email, String password, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "login";

        // Creating a JSON query from values
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email",email);
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            String userID = response.getString("_id");
                            String role = response.getString("role");
                            sessionData = new SessionData(token, userID, role);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(sessionData);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sessionData = null;
                volleyResponseListener.onError("Something wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public void getLevels(VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "levels";
        levelsData = new ArrayList<LevelsData>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); ++i) {
                                String levelID = response.getJSONObject(i).getString("_id");
                                String levelName = response.getJSONObject(i).getString("name");
                                LevelsData newLevel = new LevelsData(levelID, levelName);
                                levelsData.add(newLevel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(levelsData);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                levelsData = null;
                volleyResponseListener.onError("Something wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

}