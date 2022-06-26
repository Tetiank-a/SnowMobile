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

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class SessionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton sessionButton;
    ImageButton tasksButton;
    ImageButton lessonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        getSessions();

        sessionButton = (ImageButton) findViewById(R.id.sessions);
        lessonButton = (ImageButton) findViewById(R.id.learn);
        tasksButton = (ImageButton) findViewById(R.id.tasks);

        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionsActivity.this, SearchLesson.class);
                startActivity(intent);
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionsActivity.this, OnlineSession.class);
                startActivity(intent);
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getSessions() {

        ArrayList<FilteredSession> sessions = ((Extended) getApplication()).getFilteredSessions();
        recyclerView = findViewById(R.id.sessionsList);
        SessionRecAdapter adapter = new SessionRecAdapter(SessionsActivity.this, sessions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}