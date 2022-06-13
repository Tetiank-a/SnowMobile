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

        // Calling a POST request
       /* JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email","4@gmail.com");
            object.put("password","4444444444");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject res = ((Extended) this.getApplication()).postData(object, "login");
        System.out.println("String Response : "+ res.toString());*/
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + ((Extended) this.getApplication()).result);
        JSONObject loginData = new JSONObject();
        try {
            //input your API parameters
            loginData.put("email","4@gmail.com");
            loginData.put("password","4444444444");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((Extended) this.getApplication()).postData("login", loginData);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://snowboard-school.herokuapp.com";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}