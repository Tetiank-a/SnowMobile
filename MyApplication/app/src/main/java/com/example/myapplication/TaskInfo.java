package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskInfo extends AppCompatActivity {

    TextView infoTaskName;
    YouTubePlayerView youTubePlayerView;
    TextView infoTaskUser;
    TextView infoTaskLevel;
    TextView infoTaskText;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        infoTaskName = (TextView) findViewById(R.id.infoTaskName);
        infoTaskLevel = (TextView) findViewById(R.id.infoTaskLevel);
        infoTaskText = (TextView) findViewById(R.id.infoTaskText);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.infoTaskVideo);
        infoTaskUser = (TextView) findViewById(R.id.infoTaskUser);

        infoTaskText.setMovementMethod(new ScrollingMovementMethod());

        task = ((Extended) getApplication()).getSelectedTask();

        Api api = new Api(TaskInfo.this);
        api.getUser(task.userId, ((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(TaskInfo.this, "Something went wrong.",
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
                try {
                    infoTaskUser.setText("Created by " + jsonArray.getJSONObject(0).getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

        infoTaskName.setText(task.name);
        infoTaskLevel.setText("Task level : " + task.level.levelName);
        infoTaskText.setText(task.text);

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getVideoId(task.link);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }

    private String getVideoId(String url) {
        StringBuilder videoId = new StringBuilder();
        for (int i = url.length() - 1; i >= 0; i--) {
            Character x = url.charAt(i);
            if (x.equals('/'))
                break;
            videoId.insert(0, url.charAt(i));
        }
        return videoId.toString();
    }
}