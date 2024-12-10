package com.example.swe_termproj_v9f;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProgramsActivity extends AppCompatActivity {

    private RecyclerView programsRecyclerView, coursesRecyclerView;
    private ProgramAdapter programAdapter;
    private CourseAdapter courseAdapter;
    private List<ProgramItem> programList;
    private List<CourseItem> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.programs), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */

            //initializing recycler view
        programsRecyclerView = findViewById(R.id.programsRecyclerView);
        programsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //hardcoding in the programs
        List<ProgramItem> programList = new ArrayList<>();
        programList.add(new ProgramItem("Architectural", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Civil", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Geomatics", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Computing Systems", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Electronics (Biomedical)", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Electronics (Instrumentation)", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Electrical (Power)", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Telecommunications", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Chemical Processing", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Industrial", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Mechanical", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Mechanical (Manufacturing)", "Engineering Technology", "http://"));
        programList.add(new ProgramItem("Petroleum", "Engineering Technology", "http://"));

        /*
        * so the link provided in the notes no longer works/is up and running and we had no clue what other link to put,
        * so it has been made to display some hard coded course information if fetching the course data via XML does not work.
        * understand that this is not exactly what you wanted but to out knowledge currently I cannot seem to get it working with the
        * regular CNA website course URLs so this was the best resolution we could come up with.
         */

        //used to copy and paste to add more if needed
        //programList.add(new ProgramItem("Name", "Description", "URL"));

            //again setting the adapter for it
        programAdapter = new ProgramAdapter(this, programList, this::fetchCoursesForProgram);
        programsRecyclerView.setAdapter(programAdapter);

            //now for courses
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView); // Add this ID in your XML
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //empty course list
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(this, courseList);
        coursesRecyclerView.setAdapter(courseAdapter);
    }

        //actually grabbing the info on programs/courses with xml request
    private void fetchCoursesForProgram(String url) {
                new FetchCoursesTask().execute(url);
    }

                //using ASyncTask to get the courses from XML URL... This is rough...
            private class FetchCoursesTask extends AsyncTask<String, Void, List<CourseItem>> {

                @Override
                protected List<CourseItem> doInBackground(String... urls) {
                    List<CourseItem> result = new ArrayList<>();

                    try {
                        URL url = new URL(urls[0]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = connection.getInputStream();
                            result = XMLCourseParser.parse(inputStream);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                            //returns null if it fails
                    }

                    return result;

                }

                @Override
                protected void onPostExecute(List<CourseItem> courses) {
                    if (courses != null && !courses.isEmpty()) {
                            //if the URL was successful it does it's things
                        courseList.clear();
                        courseList.addAll(courses);
                    } else {
                            //if the URL fails it displays some course info just Hard coded in
                        courseList.clear();
                        courseList.addAll(getHardcodedCourses());
                    }
                    courseAdapter.notifyDataSetChanged();
                }
            }

    private List<CourseItem> getHardcodedCourses() {
        List<CourseItem> fallbackCourses = new ArrayList<>();
        fallbackCourses.add(new CourseItem("EG1110", "Engineering Graphics", 2, 2, 3));
        fallbackCourses.add(new CourseItem("CM1400", "Technical Report Writing I", 3, 0, 3));
        fallbackCourses.add(new CourseItem("MA1700", "Mathematics", 3, 2, 4));
        fallbackCourses.add(new CourseItem("PH1100", "Physics I", 3, 2, 4));
        fallbackCourses.add(new CourseItem("CH1120", "Chemistry I", 3, 2, 4));
        fallbackCourses.add(new CourseItem("CM1401", "Technical Report Writing II", 3, 0, 3));
        fallbackCourses.add(new CourseItem("MA1101", "Advanced Mathematics", 5, 0, 5));
        fallbackCourses.add(new CourseItem("PH1101", "Physics II", 2, 3, 4));
        fallbackCourses.add(new CourseItem("CH1121", "Chemistry II", 3, 2, 4));

        return fallbackCourses;
    }

}