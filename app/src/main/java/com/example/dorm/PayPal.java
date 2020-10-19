package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PayPal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String url = "https://www.paypal.com/us/signin";

        WebView webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.loadUrl(url);
    }
}