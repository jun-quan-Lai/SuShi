package com.ljq.sushi.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        WebSettings settings = contentWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int mDensity = dm.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            settings.setDefaultZoom(ZoomDensity.FAR);
        } else if (mDensity == 160) {
            settings.setDefaultZoom(ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            settings.setDefaultZoom(ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            settings.setDefaultZoom(ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            settings.setDefaultZoom(ZoomDensity.FAR);
        }else{
            settings.setDefaultZoom(ZoomDensity.MEDIUM);
        }
        contentWebView.setWebViewClient(new MyWebViewClient());
        contentWebView.loadUrl(contentUrl);
    }
}

class MyWebViewClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
         view.loadUrl(url);
        return true;
    }
}



