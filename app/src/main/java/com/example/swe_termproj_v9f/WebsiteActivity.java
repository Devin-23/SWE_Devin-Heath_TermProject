package com.example.swe_termproj_v9f;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebsiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        WebView webView = findViewById(R.id.webView2);

        webView.getSettings().setJavaScriptEnabled(true);

            //actually loading the URL
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.cna.nl.ca/");
    }
}
