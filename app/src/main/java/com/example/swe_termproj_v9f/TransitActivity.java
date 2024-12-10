package com.example.swe_termproj_v9f;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class TransitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit);

        WebView webView = findViewById(R.id.webView);

        // Enable JavaScript (if needed for the web page)
        webView.getSettings().setJavaScriptEnabled(true);

        // Load the URL
        webView.setWebViewClient(new WebViewClient()); // Ensures the URL opens within the WebView
        webView.loadUrl("https://metrobus.com/home/");
    }
}
