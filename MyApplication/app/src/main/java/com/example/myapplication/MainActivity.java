package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageButton button1;
    RequestQueue requestQueue;
    ListView listView;
    ImageButton sessionButton;
    ImageButton tasksButton;
    ImageButton lessonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RequestQueue For Handle Network Request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        button1 = (ImageButton) findViewById(R.id.profile);
        textView = (TextView) findViewById(R.id.instructorName);
        getTasks();

        sessionButton = (ImageButton) findViewById(R.id.sessions);
        lessonButton = (ImageButton) findViewById(R.id.learn);
        tasksButton = (ImageButton) findViewById(R.id.tasks);

        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchLesson.class);
                startActivity(intent);
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OnlineSession.class);
                startActivity(intent);
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void getHelloFromServer(View view) {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }

    public void getTasks() {

        Api api = new Api(MainActivity.this);
        api.getTasks(((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Something went wrong.",
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
                ArrayList<Task> tasks = new ArrayList<Task>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    try {
                        JSONObject taskJson = jsonArray.getJSONObject(i);
                        Task task = new Task();
                        task.taskId = taskJson.getString("_id");
                        task.name = taskJson.getString("name");
                        task.level = new LevelsData(taskJson.getJSONObject("level").getString("_id"),
                                taskJson.getJSONObject("level").getString("name"));
                        task.link = taskJson.getString("link");
                        task.text = taskJson.getString("text");
                        task.recId = "";
                        task.userId = taskJson.getString("user_id");
                        tasks.add(task);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView productList = findViewById(R.id.middleBar);
                TaskAdapter adapter = new TaskAdapter(MainActivity.this, R.layout.list_tasks, tasks, ((Extended) getApplication()).getToken());
                productList.setAdapter(adapter);


                listView = findViewById(R.id.middleBar);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = ((TextView) view.findViewById(R.id.instructorName)).getText().toString();
                        //Toast.makeText(getApplicationContext(), tasks.get(i).name, Toast.LENGTH_SHORT).show();
                        ((Extended) getApplication()).setSelectedTask(tasks.get(i));
                        Intent intent = new Intent(MainActivity.this, TaskInfo.class);
                        startActivity(intent);
                    }
                });

                registerForContextMenu(listView);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

    }

}