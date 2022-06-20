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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RequestQueue For Handle Network Request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        button1 = (ImageButton) findViewById(R.id.profile);
        getTasks();
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

                System.out.println(tasks.get(1).taskId + "---------------->" + tasks.get(2).taskId);
                ListView productList = findViewById(R.id.middleBar);
                TaskAdapter adapter = new TaskAdapter(MainActivity.this, R.layout.list_tasks, tasks);
                productList.setAdapter(adapter);


                listView = findViewById(R.id.middleBar);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = ((TextView) view.findViewById(R.id.taskCreator)).getText().toString();
                        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                        //currentNote = item;
                        return false;
                    }
                });

                registerForContextMenu(listView);
            }
        });

    }
}