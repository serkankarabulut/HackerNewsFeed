package com.example.serkan.hackernewsfeed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebContentActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_content);
        this.webView = findViewById(R.id.newsWebView);
        this.webView.getSettings().setJavaScriptEnabled(true);
        setWebViewContent();
    }

    public void setWebViewContent() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        this.webView.loadUrl(url);
    }

}
