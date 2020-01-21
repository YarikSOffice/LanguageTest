package com.yariksoffice.languagetest.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.yariksoffice.languagetest.R;

import androidx.annotation.Nullable;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("https://www.google.com/");
    }
}