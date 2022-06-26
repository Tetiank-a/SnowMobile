package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchLesson extends AppCompatActivity {

    private DatePickerDialog datePickerDialog1, datePickerDialog2;
    private Button dateFrom;
    private Button dateTo;
    private Spinner spinner;
    private String locationID;
    private String filterFrom, filterTo;
    Integer y1, y2, m1, m2, d1, d2, hh1 = 0, hh2 = 0, mm1 = 0, mm2 = 0;
    private ArrayList<Location> locationsData;
    ArrayList<FilteredSession> sessionsData;
    NumberPicker pickHour1;
    NumberPicker pickHour2;
    NumberPicker pickMinute1;
    NumberPicker pickMinute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lesson);

        initDatePicker1();
        dateFrom = (Button) findViewById(R.id.dateFrom);
        dateFrom.setText(getTodaysDate());

        initDatePicker2();
        dateTo = (Button) findViewById(R.id.dateTo);
        dateTo.setText(getTodaysDate());

        spinner = (Spinner) findViewById(R.id.locationSelected);

        pickHour1 = (NumberPicker) findViewById(R.id.timeFrom1);
        pickHour2 = (NumberPicker) findViewById(R.id.timeTo1);

        pickMinute1 = (NumberPicker) findViewById(R.id.timeFrom2);
        pickMinute2 = (NumberPicker) findViewById(R.id.timeTo2);

        pickHour1.setMaxValue(23);
        pickHour2.setMaxValue(23);
        pickHour1.setMinValue(0);
        pickHour2.setMinValue(0);

        pickMinute1.setMaxValue(59);
        pickMinute2.setMaxValue(59);
        pickMinute1.setMinValue(0);
        pickMinute2.setMinValue(0);
        getLocations();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        m = m + 1;

        y1 = y;
        y2 = y;
        m1 = m;
        m2 = m;
        d1 = d;
        d2 = d;

        return makeDateString(d, m, y);
    }

    private void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
                y1 = y;
                m1 = m;
                d1 = d;
                String date = makeDateString(d, m, y);
                dateFrom.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog1 = new DatePickerDialog(this, style, dateSetListener, y, m, d);
    }
    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
                y2 = y;
                m2 = m;
                d2 = d;
                String date = makeDateString(d, m, y);
                dateTo.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener, y, m, d);
    }

    private String makeDateString(int d, int m, int y) {
        return getMonthFormat(m) + " " + d + " " + y;
    }

    private String getMonthFormat(int m) {

        if (m == 1)
            return "JAN";
        if (m == 2)
            return "FEB";
        if (m == 3)
            return "MAR";
        if (m == 4)
            return "APR";
        if (m == 5)
            return "MAY";
        if (m == 6)
            return "JUN";
        if (m == 7)
            return "JUL";
        if (m == 8)
            return "AUG";
        if (m == 9)
            return "SEP";
        if (m == 10)
            return "OCT";
        if (m == 11)
            return "NOV";
        if (m == 12)
            return "DEC";

        return "DEC";
    }

    public void openDatePicker1(View view) {
        datePickerDialog1.show();
    }
    public void openDatePicker2(View view) {
        datePickerDialog2.show();
    }

    public void getLocations() {

        Api api = new Api(SearchLesson.this);
        api.getLocations(((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SearchLesson.this, "Incorrect email or password.",
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
                locationsData = new ArrayList<Location>();
                try {
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        String locationID = jsonArray.getJSONObject(i).getString("_id");
                        String locationName = jsonArray.getJSONObject(i).getString("name");
                        Location newLocation = new Location(locationID, locationName);
                        locationsData.add(newLocation);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < locationsData.size(); ++i) {
                        arrayList.add(locationsData.get(i).name);
                }

                locationID = locationsData.get(0).id;

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchLesson.this,
                        android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        String selectedLocation = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < locationsData.size(); ++i) {
                            if (locationsData.get(i).name.equals(selectedLocation)) {
                                locationID = locationsData.get(i).id;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });

    }

    public void applyFilter(View view) {

        LocalDateTime filter1 = LocalDateTime.of(y1, m1, d1, pickHour1.getValue(), pickMinute1.getValue());
        LocalDateTime filter2 = LocalDateTime.of(y2, m2, d2, pickHour2.getValue(), pickMinute2.getValue());

        Api api = new Api(SearchLesson.this);
        api.getSessions(((Extended) getApplication()).getToken(), new Api.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SearchLesson.this, "Incorrect email or password.",
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
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                ArrayList<FilteredSession> sessionsFiltered = new ArrayList<>();
                for (int i = 0; i < sessionsData.size(); ++i) {
                    LocalDateTime dFrom = LocalDateTime.parse(sessionsData.get(i).dtStart, pattern);
                    LocalDateTime dTo = LocalDateTime.parse(sessionsData.get(i).dtFinish, pattern);
                    if (sessionsData.get(i).location.id.equals(locationID) &&
                            filter1.isBefore(dFrom.plusMinutes(1)) &&
                            filter2.isAfter(dFrom.minusMinutes(1)) && sessionsData.get(i).student.name.equals("-")) {
                        sessionsFiltered.add(sessionsData.get(i));
                    }
                }
                ((Extended) getApplication()).setFilteredSessions(sessionsFiltered);

                Intent intent = new Intent(SearchLesson.this, SessionsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }
}