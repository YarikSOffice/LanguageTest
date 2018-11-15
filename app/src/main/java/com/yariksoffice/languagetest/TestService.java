package com.yariksoffice.languagetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class TestService extends Service {

    private final String TAG = "TestService";

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