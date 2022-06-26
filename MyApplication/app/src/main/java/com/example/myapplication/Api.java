package com.example.myapplication;

import android.content.Context;
import android.provider.CallLog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Api {

    SessionData sessionData;
    ArrayList<LevelsData> levelsData = new ArrayList<LevelsData>();

    JSONArray jsonArray;

    public static final String SNOW_SCHOOL_API = "https://snowboard-school.herokuapp.com/api/";

    Context context;

    public Api(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(SessionData sessionData);

        void onResponse(ArrayList<LevelsData> levelsData);

        void onResponse(String message);

        void onResponse(JSONArray jsonArray);

        void onResponse(JSONObject jsonObject);
    }


    public void Login(String email, String password, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "login";

        // Creating a JSON query from values
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email", email);
            object.put("password", password);
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
                System.out.println(error.getMessage());
                volleyResponseListener.onError("Something went wrong");
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

    public void getLocations(String token, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "locations";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyResponseListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Something wrong");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void getSessions(String token, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "sessions";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyResponseListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Something wrong");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void getUser(String userId, String token, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "users/" + userId;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyResponseListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                volleyResponseListener.onError("Something wrong");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }


    public void SignUp(User user, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "signup";


        // Creating a JSON query from values
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email", user.email);
            object.put("password", user.password);
            object.put("password_repeat", user.repeatPassword);
            object.put("level_id", user.levelID);
            object.put("username", user.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponseListener.onResponse("ok");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!user.repeatPassword.equals(user.password)) {
                    volleyResponseListener.onError("Passwords do not match");
                } else {
                    if (user.password.length() < 8) {
                        volleyResponseListener.onError("The password is too short");
                    } else {
                        volleyResponseListener.onError("Please check if all fields are filled");
                    }
                }
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void UpdateUser(String username, String email, String levelID, String userID, String token, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "users/" + userID;


        // Creating a JSON query from values
        JSONObject object = new JSONObject();
        JSONObject lvlObject = new JSONObject();
        try {
            lvlObject.put("_id", levelID);
            lvlObject.put("name", "-");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            //input your API parameters
            object.put("email", email);
            object.put("level", lvlObject);
            object.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(object.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponseListener.onResponse("ok");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Please check if all fields are filled");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };


        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getTasks(String token, VolleyResponseListener volleyResponseListener) {
        String url = SNOW_SCHOOL_API + "tasks";
        jsonArray = new JSONArray();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        jsonArray = response;
                        volleyResponseListener.onResponse(jsonArray);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Something wrong");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }
}