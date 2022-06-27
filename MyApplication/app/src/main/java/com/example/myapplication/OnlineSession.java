package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class OnlineSession extends AppCompatActivity {

    TextView bodyAngle;
    TextView speedX;
    TextView speedY;
    TextView speedZ;
    TextView forceLeftFront;
    TextView forceLeftBack;
    TextView forceRightFront;
    TextView forceRightBack;
    TextView result;

    TextView label;
    TextView fromLabel;
    TextView toLabel;
    TextView textPassword1;
    TextView textPassword2;
    TextView textView;
    Button predict;

    IotData iotData = new IotData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_session);

        translate();

        bodyAngle = (TextView) findViewById(R.id.d1);
        speedX = (TextView) findViewById(R.id.d2);
        speedY = (TextView) findViewById(R.id.d3);
        speedZ = (TextView) findViewById(R.id.d4);
        result = (TextView) findViewById(R.id.result);


    }

    @SuppressLint("SetTextI18n")
    public void translate() {
        label = (TextView) findViewById(R.id.label);
        fromLabel = (TextView) findViewById(R.id.fromLabel);
        toLabel = (TextView) findViewById(R.id.toLabel);
        textPassword1 = (TextView) findViewById(R.id.textPassword1);
        textPassword2 = (TextView) findViewById(R.id.textPassword2);
        textView = (TextView) findViewById(R.id.textView);
        predict = (Button) findViewById(R.id.predict);

        if (((Extended) getApplication()).getLang().equals("ukr")) {
            label.setText("Підказки у реальному часі");
            fromLabel.setText("Кут повернення");
            toLabel.setText("Швидкість X");
            textPassword1.setText("Швидкість Y");
            textPassword2.setText("Швидкість Z");
            textView.setText("Сила натиску на точки сноуборду");
            predict.setText("Отримати");
        } else {
            label.setText("Real time advice");
            fromLabel.setText("Body angle");
            toLabel.setText("Speed X");
            textPassword1.setText("Speed Y");
            textPassword2.setText("Speed Z");
            textView.setText("Pressure force on snowboard points");
            predict.setText("Receive advice");
        }
    }

    public void getAdvice(View view) {

        forceLeftFront = (TextView) findViewById(R.id.leftF);
        forceRightFront = (TextView) findViewById(R.id.rightF);
        forceLeftBack = (TextView) findViewById(R.id.leftB);
        forceRightBack = (TextView) findViewById(R.id.rightB);

        iotData = new IotData(speedX.getText().toString(), speedY.getText().toString(),
                speedZ.getText().toString(), bodyAngle.getText().toString(),
                forceLeftFront.getText().toString(), forceLeftBack.getText().toString(),
                forceRightFront.getText().toString(), forceRightBack.getText().toString());

        Api api = new Api(OnlineSession.this);
        api.getAdvice(iotData, ((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(OnlineSession.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SessionData sessionData) {

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
                result.setText(jsonObject.toString());
            }
        });
    }
}