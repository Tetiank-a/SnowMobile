package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class TaskAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Task> noteList;
    private String token;
    private String lang;

    TaskAdapter(Context context, int resource, ArrayList<Task> tasks, String token, String lang) {
        super(context, resource, tasks);
        this.noteList = tasks;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.token = token;
        this.lang = lang;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Task task = noteList.get(position);

        viewHolder.nameView.setText(task.name);

        if (task.level.levelName.equals("kid")) {
            viewHolder.imageView.setImageResource(R.drawable.kid);
        }
        if (task.level.levelName.equals("elementary")) {
            viewHolder.imageView.setImageResource(R.drawable.elementary);
        }
        if (task.level.levelName.equals("intermediate")) {
            viewHolder.imageView.setImageResource(R.drawable.intermediate);
        }
        if (task.level.levelName.equals("advanced")) {
            viewHolder.imageView.setImageResource(R.drawable.advanced);
        }

        LevelsData levelX = task.level;
        if (lang.equals("ukr")) {
            if (levelX.levelName.equals("kid"))
                levelX.levelName = "дитина";
            if (levelX.levelName.equals("elementary"))
                levelX.levelName = "початковий";
            if (levelX.levelName.equals("intermediate"))
                levelX.levelName = "середній";
            if (levelX.levelName.equals("advanced"))
                levelX.levelName = "професійний";
        }

        viewHolder.dataView.setText(levelX.levelName);

        //viewHolder.imgField.setText(task.userId);

        Api api = new Api(getContext());
        api.getUser(task.userId, token, new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Something went wrong.",
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
                    viewHolder.imgField.setText(jsonArray.getJSONObject(0).getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

        return convertView;
    }
    private class ViewHolder {
        final TextView nameView;
        final ImageView imageView;
        final TextView dataView;
        final TextView imgField;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.locationName);
            imageView = view.findViewById(R.id.to);
            dataView = view.findViewById(R.id.from);
            imgField = view.findViewById(R.id.instructorName);
        }
    }

}