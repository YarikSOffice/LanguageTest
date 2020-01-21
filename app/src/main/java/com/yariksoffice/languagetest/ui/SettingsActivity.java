package com.yariksoffice.languagetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yariksoffice.languagetest.App;
import com.yariksoffice.languagetest.R;

import androidx.annotation.Nullable;

import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_ENGLISH;
import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_RUSSIAN;
import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_UKRAINIAN;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.en).setOnClickListener(v -> setNewLocale(LANGUAGE_ENGLISH, false));
        findViewById(R.id.en).setOnLongClickListener(v -> setNewLocale(LANGUAGE_ENGLISH, true));
        findViewById(R.id.ukr).setOnClickListener(v -> setNewLocale(LANGUAGE_UKRAINIAN, false));
        findViewById(R.id.ukr).setOnLongClickListener(v -> setNewLocale(LANGUAGE_UKRAINIAN, true));
        findViewById(R.id.ru).setOnClickListener(v -> setNewLocale(LANGUAGE_RUSSIAN, false));
        findViewById(R.id.ru).setOnLongClickListener(v -> setNewLocale(LANGUAGE_RUSSIAN, true));
    }

    private boolean setNewLocale(String language, boolean restartProcess) {
        App.localeManager.setNewLocale(this, language);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
            Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}