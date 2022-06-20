package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText emailEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        emailEdit = (EditText) findViewById(R.id.emailField);
        passwordEdit = (EditText) findViewById(R.id.passwordField);

        Api api = new Api(Login.this);
        api.Login(emailEdit.getText().toString(), passwordEdit.getText().toString(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Login.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {
                //Toast.makeText(Login.this, "Token: " + sessionData.token, Toast.LENGTH_SHORT).show();
                ((Extended) getApplication()).setToken(sessionData.token);
                ((Extended) getApplication()).setUserId(sessionData.userID);
                ((Extended) getApplication()).setRole(sessionData.role);

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {

            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {

            }
        });
    }

    public void signUp(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

}