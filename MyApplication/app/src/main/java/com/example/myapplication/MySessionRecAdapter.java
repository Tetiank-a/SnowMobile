package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

public class MySessionRecAdapter extends RecyclerView.Adapter<MySessionRecAdapter.MyViewHolder> {

    ArrayList<FilteredSession> sessions = new ArrayList<>();
    Context context;
    String token;

    public MySessionRecAdapter(Context ct, ArrayList<FilteredSession> sessions, String token) {
        context = ct;
        this.sessions = sessions;
        this.token = token;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_my_sessions, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();

                Api api = new Api(context);
                api.UpdateSession(sessions.get(position).id, "-", token, new Api.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(SessionData sessionData) {

                    }

                    @Override
                    public void onResponse(ArrayList<LevelsData> levelsData) {

                    }

                    @Override
                    public void onResponse(String message) {
                        ((Extended) context.getApplicationContext()).updateFilteredSession(holder.getAbsoluteAdapterPosition());
                        Intent intent = new Intent(context, MySessionsActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onResponse(JSONArray jsonArray) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                    }
                });
            }
        });
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
        ImageButton imageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.locationName);
            instructor = itemView.findViewById(R.id.instructorName);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            imageButton = itemView.findViewById(R.id.unregister);
        }
    }
}
