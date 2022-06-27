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

public class SignUp extends AppCompatActivity {


    User user = new User();
    Spinner spinner;
    EditText userName;
    EditText userEmail;
    EditText userPassword1;
    EditText userPassword2;

    TextView label;
    TextView fromLabel;
    TextView toLabel;
    TextView textPassword1;
    TextView textPassword2;
    TextView textView;
    Button predict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        translate();

        spinner = (Spinner) findViewById(R.id.locationSelected);
        userName = (EditText) findViewById(R.id.d1);
        userEmail = (EditText) findViewById(R.id.d2);
        userPassword1 = (EditText) findViewById(R.id.d3);
        userPassword2 = (EditText) findViewById(R.id.d4);

        getLevels();

    }

    @SuppressLint("SetTextI18n")
    public void translate() {
        label = (TextView) findViewById(R.id.from);
        fromLabel = (TextView) findViewById(R.id.fromLabel);
        toLabel = (TextView) findViewById(R.id.toLabel);
        textPassword1 = (TextView) findViewById(R.id.textPassword1);
        textPassword2 = (TextView) findViewById(R.id.textPassword2);
        textView = (TextView) findViewById(R.id.locationLabel);
        predict = (Button) findViewById(R.id.Filter);

        if (((Extended) getApplication()).getLang().equals("ukr")) {
            label.setText("Реєстрація");
            fromLabel.setText("Ім'я");
            toLabel.setText("Пошта");
            textPassword1.setText("Пароль");
            textPassword2.setText("Підтвердити пароль");
            textView.setText("Рівень");
            predict.setText("Зареєструватися");
        } else {
            label.setText("Sign Up");
            fromLabel.setText("Username");
            toLabel.setText("Email");
            textPassword1.setText("Password");
            textPassword2.setText("Repeat password");
            textView.setText("Level");
            predict.setText("Sign Up");
        }
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

            @Override
            public void onResponse(JSONArray jsonArray) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

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

            @Override
            public void onResponse(JSONArray jsonArray) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

    }
}