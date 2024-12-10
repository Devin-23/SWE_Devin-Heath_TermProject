package com.example.swe_termproj_v9f;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

    //trying my hardest to keep these organized but somewhat losing track of what does what...

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
        //Gave up making comments at some point as it was just taking too much time...

    private CalendarView calendarView;
    private SQLiteDatabase database;
    private HashMap<String, ArrayList<String>> systemEvents;
        // ^^ used to hold XML events (this I do kinda understand)
    private Button addEventButton;
    private String selectedDate;
    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private List<String> currentEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

            //needed UI components
        calendarView = findViewById(R.id.calendarView);
        addEventButton = findViewById(R.id.addEventButton);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);

            //setting up the recyclerView to see the events
        currentEvents = new ArrayList<>();

        eventAdapter = new EventAdapter(currentEvents, new EventAdapter.OnEventActionListener() {
            @Override
            public void onEdit(String event) {
                showEditEventDialog(event); // Handle editing an event
            }

            @Override
            public void onDelete(String event) {
                deleteEventFromDatabase(event); // Handle deleting an event
            }
        });

        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setAdapter(eventAdapter);

            //using SQLite (which i'm still trying to understand)
        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

            //used to load pre-set events from a URL using XML
        systemEvents = new HashMap<>();
        new LoadSystemEventsTask().execute("https://temp.com/LinkYouWouldGetEventsFrom.xml");
                        // ^^ I really don't know what to link this too everything i've tried does not work...

            //this handles the date selection
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            loadEventsForDate(selectedDate);
        });;

            //click listener for the button to actually add your own events
        addEventButton.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(CalendarActivity.this, "Please select a date first.", Toast.LENGTH_SHORT).show();
            } else {
                showAddEventDialog(selectedDate);
            }
        });
    }

    private void loadEventsForDate(String date) {
        currentEvents.clear();

            //adds the system events
        if (systemEvents.containsKey(date)) {
            currentEvents.addAll(systemEvents.get(date));
        }

            //adding personal events from SQLite
        Cursor cursor = database.query("personal_events", new String[]{"event"},
                "date = ?", new String[]{date}, null, null, null);
        while (cursor.moveToNext()) {
            currentEvents.add(cursor.getString(0));
        }
        cursor.close();

            //updates the recyclerView
        eventAdapter.notifyDataSetChanged();
    }

        /*as mentioned on lines 144 -> 148, this has been really difficult to make work and im unsure if this is
        * good/makes sense or not but it's working and im scared to change things and break it... */
    private void showAddEventDialog(String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Event for: " + date);

        final EditText input = new EditText(this);
        input.setHint("Enter event information!");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String eventDescription = input.getText().toString().trim();
            if (!eventDescription.isEmpty()) {
                addPersonalEventToDatabase(date, eventDescription);
                loadEventsForDate(date);
            } else {
                Toast.makeText(this, "Event description cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void showEditEventDialog(String oldEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Event");

        final EditText input = new EditText(this);
        input.setText(oldEvent);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newEvent = input.getText().toString().trim();
            if (!newEvent.isEmpty()) {
                updateEventInDatabase(oldEvent, newEvent);
                loadEventsForDate(selectedDate);
            } else {
                Toast.makeText(this, "Event description cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


    private void addPersonalEventToDatabase(String date, String eventDescription) {
            //making the values to save it to the database
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("event", eventDescription);

            //add the event into the database
        long rowId = database.insert("personal_events", null, values);

            //make sure it worked cause it yelled at me once when it didn't :)
        if (rowId != -1) {
            Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add event. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEventInDatabase(String oldEvent, String newEvent) {
        ContentValues values = new ContentValues();
        values.put("event", newEvent);

        int rowsAffected = database.update("personal_events", values, "event = ?", new String[]{oldEvent});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Event updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update event.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteEventFromDatabase(String event) {
        int rowsDeleted = database.delete("personal_events", "event = ?", new String[]{event});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Event deleted successfully!", Toast.LENGTH_SHORT).show();
            loadEventsForDate(selectedDate); // Refresh the RecyclerView
        } else {
            Toast.makeText(this, "Failed to delete event.", Toast.LENGTH_SHORT).show();
        }
    }

    /* using AsyncTask to get and "parse" XML
        * i really am lost for this "AsyncTask" stuff. It's really only thanks to notes, google,
        * and the IDE auto-filling things that this is somewhat working/making any sense.
        * I hope that's okay because I have no clue what else to do. */
    private class LoadSystemEventsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                String tag = null, date = null, event = null;
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        tag = parser.getName();
                    } else if (parser.getEventType() == XmlPullParser.TEXT) {
                        if ("date".equals(tag)) {
                            date = parser.getText();
                        } else if ("event".equals(tag)) {
                            event = parser.getText();
                        }
                    } else if (parser.getEventType() == XmlPullParser.END_TAG && "event".equals(parser.getName())) {
                        if (date != null && event != null) {
                            systemEvents.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}