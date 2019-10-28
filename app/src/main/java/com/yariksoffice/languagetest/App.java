package com.yariksoffice.languagetest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class App extends Application {

    public static final String TAG = "App";

    // for the sake of simplicity. use DI in real apps instead
    public static LocaleManager localeManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Utility.bypassHiddenApiRestrictions();
    }

    @Override
    protected void attachBaseContext(Context base) {
        localeManager = new LocaleManager(base);
        super.attachBaseContext(localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}