package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class SearchLesson extends AppCompatActivity {

    private DatePickerDialog datePickerDialog1, datePickerDialog2;
    private Button dateFrom;
    private Button dateTo;

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
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        m = m + 1;

        return makeDateString(d, m, y);
    }

    private void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
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
}