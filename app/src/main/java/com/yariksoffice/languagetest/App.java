package com.yariksoffice.languagetest;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class App extends Application {

    private final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        LocaleManager.setLocale(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}