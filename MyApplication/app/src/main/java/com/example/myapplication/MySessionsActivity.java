package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

public class MySessionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton sessionButton;
    ImageButton tasksButton;
    ImageButton lessonButton;
    ImageButton mySessionsButton;

    ArrayList<FilteredSession> sessionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sessions);

        applyFilter();

        sessionButton = (ImageButton) findViewById(R.id.sessions);
        lessonButton = (ImageButton) findViewById(R.id.learn);
        tasksButton = (ImageButton) findViewById(R.id.tasks);
        mySessionsButton = (ImageButton) findViewById(R.id.mySessions);

        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySessionsActivity.this, SearchLesson.class);
                startActivity(intent);
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySessionsActivity.this, OnlineSession.class);
                startActivity(intent);
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySessionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mySessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySessionsActivity.this, MySessionsActivity.class);
                startActivity(intent);
            }
        });
    }


    public void getHelloFromServer(View view) {
        Intent intent = new Intent(MySessionsActivity.this, Profile.class);
        startActivity(intent);
    }

    public void changeLocEng(View view) {
        ((Extended) getApplication()).setLang("eng");
    }

    public void changeLocUkr(View view) {
        ((Extended) getApplication()).setLang("ukr");
    }

    public void applyFilter() {

        Api api = new Api(MySessionsActivity.this);
        api.getSessions(((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MySessionsActivity.this, "Incorrect email or password.",
                        Toast.LENGTH_SHORT).show();
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
                sessionsData = new ArrayList<FilteredSession>();
                try {
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        String sessionID = jsonArray.getJSONObject(i).getString("_id");
                        Location loc = new Location(jsonArray.getJSONObject(i).getJSONObject("location").getString("_id"),
                                jsonArray.getJSONObject(i).getJSONObject("location").getString("name"));
                        String dtstart = jsonArray.getJSONObject(i).getString("dtstart");
                        String dtfinish = jsonArray.getJSONObject(i).getString("dtfinish");
                        User student = new User(jsonArray.getJSONObject(i).getJSONObject("user").getString("_id"),
                                jsonArray.getJSONObject(i).getJSONObject("user").getString("username"));
                        User teacher = new User(jsonArray.getJSONObject(i).getJSONObject("instructor").getString("_id"),
                                jsonArray.getJSONObject(i).getJSONObject("instructor").getString("username"));
                        FilteredSession newSession = new FilteredSession(sessionID, teacher, student, dtstart, dtfinish, loc);
                        sessionsData.add(newSession);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // FILTERS !!!!!!!
                ArrayList<FilteredSession> sessionsFiltered = new ArrayList<>();
                for (int i = 0; i < sessionsData.size(); ++i) {
                    if (sessionsData.get(i).student.id.equals(((Extended) getApplication()).getUserId())) {
                        sessionsFiltered.add(sessionsData.get(i));
                    }
                }
                ((Extended) getApplication()).setFilteredSessions(sessionsFiltered);

                recyclerView = findViewById(R.id.sessionsList);
                MySessionRecAdapter adapter = new MySessionRecAdapter(MySessionsActivity.this,
                        sessionsFiltered, ((Extended) getApplication()).getToken());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MySessionsActivity.this));
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }
}