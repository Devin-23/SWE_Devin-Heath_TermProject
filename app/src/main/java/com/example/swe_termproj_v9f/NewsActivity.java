package com.example.swe_termproj_v9f;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //list of news items
        List<String> newsList = new ArrayList<>();
        newsList.add("Breaking News: The Earth is Round?? "
            + "massive Shock Across the world now known as 'Globe'"
        );
        newsList.add("Sports: Human Throw Ball."
                + " Human Cheer."
                + " Human Throw Again."
                + " More Cheer."
        );
        newsList.add("Weather: Bad."
                + "stay inside."
        );
        newsList.add("Tech: New Phone Does Crazy Things!!"
                + "It Folds?!?!?!"
        );
        newsList.add("Health: Doctors Recommend People to Stop Getting Sick."
                + " The People Respond 'Thanks Doc, Never Thought of that...'"
                + " More on This Tonight on 'Thanks Doc' on TV @2pm"
        );

            /*The URL stuff just insist working just I'm just making it simple as we don't have time to
             *implement it and just not use it anymore.*/

            //using the news adapter
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);
    }
}
