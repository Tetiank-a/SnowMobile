package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SessionRecAdapter extends RecyclerView.Adapter<SessionRecAdapter.MyViewHolder> {

    ArrayList<FilteredSession> sessions;
    Context context;

    public SessionRecAdapter(Context ct, ArrayList<FilteredSession> sessions) {
        context = ct;
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_sessions, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.location.setText(sessions.get(position).location.name);
        holder.instructor.setText(sessions.get(position).instructor.name);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        //@SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStart = sessions.get(position).dtStart;
        LocalDateTime filter1 = LocalDateTime.parse(dateStart, pattern);
        holder.from.setText(filter1.format(formatter));

        String dateFinish = sessions.get(position).dtFinish;
        filter1 = LocalDateTime.parse(dateFinish, pattern);
        holder.to.setText(filter1.format(formatter));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location;
        TextView instructor;
        TextView from;
        TextView to;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.locationName);
            instructor = itemView.findViewById(R.id.instructorName);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
        }
    }
}
