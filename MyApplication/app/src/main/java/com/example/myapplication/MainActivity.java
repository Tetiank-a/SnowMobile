package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageButton button1;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RequestQueue For Handle Network Request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        textView = (TextView) findViewById(R.id.tv_text);
        button1 = (ImageButton) findViewById(R.id.profile);
    }


    public void getHelloFromServer(View view) {
        Api api = new Api(MainActivity.this);
        api.Login("4@gmail.com", "4444444444", new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {
                Toast.makeText(MainActivity.this, "Token: " + sessionData.token, Toast.LENGTH_SHORT).show();
                ((Extended) getApplication()).setToken(sessionData.token);
                ((Extended) getApplication()).setUserId(sessionData.userID);
                ((Extended) getApplication()).setRole(sessionData.role);
            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {

            }

            @Override
            public void onResponse(String message) {

            }
        });
    }

}