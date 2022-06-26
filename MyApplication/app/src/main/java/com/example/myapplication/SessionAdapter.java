package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;
import java.util.ArrayList;

class SessionAdapter extends ArrayAdapter<FilteredSession> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<FilteredSession> noteList;
    private String token;

    SessionAdapter(Context context, int resource, ArrayList<FilteredSession> sessions, String token) {
        super(context, resource, sessions);
        this.noteList = sessions;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.token = token;
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
        final FilteredSession session = noteList.get(position);

        viewHolder.location.setText(session.location.name);
        viewHolder.instructor.setText(session.instructor.name);

        viewHolder.from.setText(session.dtStart);
        viewHolder.to.setText(session.dtFinish);

        return convertView;
    }
    private class ViewHolder {
        final TextView location;
        final TextView instructor;
        final TextView from;
        final TextView to;
        ViewHolder(View view){
            location = view.findViewById(R.id.locationName);
            instructor = view.findViewById(R.id.instructorName);
            from = view.findViewById(R.id.from);
            to = view.findViewById(R.id.to);
        }
    }

}