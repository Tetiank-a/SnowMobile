package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Spinner spinner;
    EditText userName;
    EditText userEmail;

    String username;
    String email;
    String levelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        spinner = (Spinner) findViewById(R.id.levelId);
        userName = (EditText) findViewById(R.id.regName);
        userEmail = (EditText) findViewById(R.id.regEmail);

        getLevels();
    }

    public void updateProfile(View view) {
        username = userName.getText().toString();
        email = userEmail.getText().toString();

        Api api = new Api(Profile.this);
        api.UpdateUser(username, email, levelID, ((Extended) getApplication()).getUserId(), ((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Profile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {

            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {

            }

            @Override
            public void onResponse(String message) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onResponse(JSONArray jsonArray) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }

    public void getLevels() {

        Api api = new Api(Profile.this);
        api.getLevels(new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Profile.this, "Incorrect email or password.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {
            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {
                ArrayList<LevelsData> levels = new ArrayList<LevelsData>();
                for (int i = 0; i < levelsData.size(); ++i) {
                    if (levelsData.get(i).levelName != "admin" &&
                            levelsData.get(i).levelName != "instructor") {
                        levels.add(levelsData.get(i));
                    }
                }
                ((Extended) getApplication()).setLevels(levels);

                // Displaying levels

                ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < levels.size(); ++i) {
                    if (!levels.get(i).levelName.equals("instructor") &&
                            !levels.get(i).levelName.equals("admin")) {
                        arrayList.add(levels.get(i).levelName);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Profile.this,
                        android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        String selectedLevel = parent.getItemAtPosition(position).toString();

                        // Getting an id of the level from the name

                        for (int i = 0; i < levels.size(); ++i) {
                            if (levels.get(i).levelName.equals(selectedLevel)) {
                                levelID = levels.get(i).levelID;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

    }
}