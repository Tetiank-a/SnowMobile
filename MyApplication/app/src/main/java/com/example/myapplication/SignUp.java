package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // getting all possible levels;

    }

    public void getLevels(View view) {

        Api api = new Api(SignUp.this);
        api.getLevels(new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SignUp.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {
            }

            @Override
            public void onResponse(ArrayList<LevelsData> levelsData) {
                ArrayList<LevelsData> levels = new ArrayList<LevelsData>();
                for (int i = 0; i < levelsData.size(); ++i) {
                    if (levelsData.get(i).levelName != "admin" && levelsData.get(i).levelName != "instructor") {
                        levels.add(levelsData.get(i));
                    }
                }
                ((Extended) getApplication()).setLevels(levels);
                
                // Alert that levels are received
                Toast.makeText(SignUp.this, "Login: " + levels.get(0).levelName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}