package com.ljq.sushi.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ljq.sushi.R;

public class ContentActivity extends AppCompatActivity {

    String contentUrl;
    WebView contentWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contentUrl = getIntent().getStringExtra("contentUrl");
        contentWebView= (WebView) findViewById(R.id.content_webview);
        contentWebView.loadUrl(contentUrl);
    }
}



