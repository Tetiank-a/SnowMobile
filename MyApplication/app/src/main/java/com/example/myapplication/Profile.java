package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    TextView fromLabel;
    TextView toLabel;
    TextView locationLabel;
    Button filter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        translate();

        spinner = (Spinner) findViewById(R.id.locationSelected);
        userName = (EditText) findViewById(R.id.d1);
        userEmail = (EditText) findViewById(R.id.d2);

        getLevels();
    }

    @SuppressLint("SetTextI18n")
    public void translate() {
        fromLabel = (TextView) findViewById(R.id.fromLabel);
        toLabel = (TextView) findViewById(R.id.toLabel);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        filter1 = (Button) findViewById(R.id.Filter);

        if (((Extended) getApplication()).getLang().equals("ukr")) {
            fromLabel.setText("Ім'я");
            toLabel.setText("Пошта");
            locationLabel.setText("Рівень");
            filter1.setText("Оновити");
        } else {
            fromLabel.setText("Name");
            toLabel.setText("Email");
            locationLabel.setText("Level");
            filter1.setText("Update");
        }
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
                    if (!levelsData.get(i).levelName.equals("admin") &&
                            !levelsData.get(i).levelName.equals("instructor")) {
                        LevelsData levelX = levelsData.get(i);
                        if (((Extended) getApplication()).getLang().equals("ukr")) {
                            if (levelX.levelName.equals("kid"))
                                levelX.levelName = "дитина";
                            if (levelX.levelName.equals("elementary"))
                                levelX.levelName = "початковий";
                            if (levelX.levelName.equals("intermediate"))
                                levelX.levelName = "середній";
                            if (levelX.levelName.equals("advanced"))
                                levelX.levelName = "професійний";
                        }
                        levels.add(levelX);
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