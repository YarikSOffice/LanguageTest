package com.yariksoffice.languagetest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class App extends Application {

    private final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}