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

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {


    User user = new User();
    Spinner spinner;
    EditText userName;
    EditText userEmail;
    EditText userPassword1;
    EditText userPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinner = (Spinner) findViewById(R.id.levelId);
        userName = (EditText) findViewById(R.id.regName);
        userEmail = (EditText) findViewById(R.id.regEmail);
        userPassword1 = (EditText) findViewById(R.id.regPassword1);
        userPassword2 = (EditText) findViewById(R.id.regPassword2);

        getLevels();

    }

    public void signUp(View view) {
        user.name = userName.getText().toString();
        user.email = userEmail.getText().toString();
        user.password = userPassword1.getText().toString();
        user.repeatPassword = userPassword2.getText().toString();

        Api api = new Api(SignUp.this);
        api.SignUp(user, new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SignUp.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {

            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {

            }

            @Override
            public void onResponse(String message) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void getLevels() {

        Api api = new Api(SignUp.this);
        api.getLevels(new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SignUp.this, "Incorrect email or password.",
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignUp.this,
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
                                user.levelID = levels.get(i).levelID;
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> parent) {
                    }
                });
            }

            @Override
            public void onResponse(String message) {

            }
        });

    }
}