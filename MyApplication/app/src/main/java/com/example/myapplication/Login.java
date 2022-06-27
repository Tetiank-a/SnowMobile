package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Login extends AppCompatActivity {

    EditText emailEdit;
    EditText passwordEdit;
    TextView tw_email;
    TextView tw_password;
    Button button2;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println("Device language : " + Locale.getDefault().getDisplayLanguage());
        if (Locale.getDefault().getDisplayLanguage().equals("українська"))
            ((Extended) getApplication()).setLang("ukr");
        else
            ((Extended) getApplication()).setLang("eng");
        translate();
    }

    public void translate() {
        tw_email = (TextView) findViewById(R.id.tw_email);
        tw_password = (TextView) findViewById(R.id.tw_password);
        button2 = (Button) findViewById(R.id.button2);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        if (((Extended) getApplication()).getLang().equals("ukr")) {
            tw_email.setText("Пошта");
            tw_password.setText("Пароль");
            button2.setText("Реєстрація");
            btn_submit.setText("Вхід");
        } else {
            tw_email.setText("Email");
            tw_password.setText("Password");
            button2.setText("Sign Up");
            btn_submit.setText("Submit");
        }
    }

    public void login(View view) {
        emailEdit = (EditText) findViewById(R.id.emailField);
        passwordEdit = (EditText) findViewById(R.id.passwordField);

        Api api = new Api(Login.this);
        api.Login(emailEdit.getText().toString(), passwordEdit.getText().toString(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
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

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }

    public void signUp(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

}