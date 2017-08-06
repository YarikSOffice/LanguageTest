package com.yariksoffice.languagetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class TestService extends Service {

    private final String TAG = "TestService";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        Locale locale = LocaleManager.getLocale(getResources());
        String message = locale.getLanguage() + " " + Utility.hexString(getResources());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}