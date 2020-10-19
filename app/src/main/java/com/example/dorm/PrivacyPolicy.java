package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        String url = "https://www.websitepolicies.com/policies/view/IIrkYTbr";

        WebView webView =  findViewById(R.id.webView);

        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.loadUrl(url);
    }
}