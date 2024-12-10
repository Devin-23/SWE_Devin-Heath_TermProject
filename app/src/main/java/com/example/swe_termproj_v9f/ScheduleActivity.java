package com.example.swe_termproj_v9f;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private Spinner programSpinner, yearSpinner;
    private RecyclerView recyclerView;
    private ScheduleAdapter ScheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        programSpinner = findViewById(R.id.ProgramSpin);
        yearSpinner = findViewById(R.id.yearSpin);
        recyclerView = findViewById(R.id.recyclerViewTimetable);
        Button getButton = findViewById(R.id.GetButton);

            //I still don't understand why these are called spinners...
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select a Program", "Mechanical", "Computing Systems", "Civil"});
        programSpinner.setAdapter(programAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select a Year", "2022", "2023", "2024", "2025"});
        yearSpinner.setAdapter(yearAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScheduleAdapter = new ScheduleAdapter(new ArrayList<>());
        recyclerView.setAdapter(ScheduleAdapter);

            //gotta have that click listener for the buttons
        getButton.setOnClickListener(view -> {
            String selectedProgram = programSpinner.getSelectedItem().toString();
            String selectedYear = yearSpinner.getSelectedItem().toString();

                //gets data based on the selected year
            List<TimetableEntry> timetable = fetchTimetable(selectedProgram, selectedYear);
            ScheduleAdapter = new ScheduleAdapter(timetable);
            recyclerView.setAdapter(ScheduleAdapter);
        });
    }

    private List<TimetableEntry> fetchTimetable(String program, String year) {
            //can be replaced with data from a Database or grabbed from URL
        List<TimetableEntry> dummyData = new ArrayList<>();
        dummyData.add(new TimetableEntry("MA1101 Adv. Math", "10:00 AM - 11:00 AM", "Room 328"));
        dummyData.add(new TimetableEntry("PH1101 Physics II", "11:00 AM - 12:00 PM", "Room 220"));
        dummyData.add(new TimetableEntry("CH1121 Chemistry II", "1:00 PM - 2:00 PM", "Room 323"));
        return dummyData;
    }
}
