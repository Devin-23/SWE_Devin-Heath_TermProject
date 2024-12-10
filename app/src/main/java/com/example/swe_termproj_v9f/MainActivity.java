package com.example.swe_termproj_v9f;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            //initializing recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //inserting the buttons & data for the recycler view list
        List<ButtonItem> buttonList = new ArrayList<>();
        buttonList.add(new ButtonItem("Available Programs", ProgramsActivity.class));
            // ^^ Semi-Complete (just add a horizontal bar in activity_programs.xlm
        buttonList.add(new ButtonItem("Class Schedules", ScheduleActivity.class));
            // ^^ Null
        buttonList.add(new ButtonItem("Personal Calendar", CalendarActivity.class));
            // ^^ Null
        buttonList.add(new ButtonItem("Public Transit Info", TransitActivity.class));
            // ^^ Complete
        buttonList.add(new ButtonItem("News", NewsActivity.class));
            // ^^ Null
        buttonList.add(new ButtonItem("Contacts", ContactsActivity.class));
            // ^^ Null
        buttonList.add(new ButtonItem("Application Info", InfoActivity.class));
            // ^^ Null

    //I hate I set the above comments talking about the above but not below but I'm also too lazy to change it :)

            //setting the adapter for it
        ButtonAdapter adapter = new ButtonAdapter(this, buttonList);
        recyclerView.setAdapter(adapter);
    }
}
