package com.yariksoffice.languagetest;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class App extends Application {

    private final String TAG = "App";

    // for the sake of simplicity. use DI in real apps instead
    public static LocaleManager localeManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);
        registerActivityLifecycleCallbacks(new LocaleActivityCallbacks());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}