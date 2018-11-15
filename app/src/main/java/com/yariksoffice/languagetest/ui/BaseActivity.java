package com.yariksoffice.languagetest.ui;

import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.yariksoffice.languagetest.LocaleManager;
import com.yariksoffice.languagetest.R;
import com.yariksoffice.languagetest.Utility;

import java.util.Locale;

import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_ENGLISH;
import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_UKRAINIAN;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        LocaleManager.setLocale(this);
        Utility.resetActivityTitle(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResourcesInfo();

        TextView tv = findViewById(R.id.cache);
        tv.setText(Utility.getTitleCache());
    }

    private void showResourcesInfo() {
        Resources topLevelRes = Utility.getTopLevelResources(this);
        updateInfo("Top level  ", findViewById(R.id.tv1), topLevelRes);

        Resources appRes = getApplication().getResources();
        updateInfo("Application  ", findViewById(R.id.tv2), appRes);

        Resources actRes = getResources();
        updateInfo("Activity  ", findViewById(R.id.tv3), actRes);

        TextView tv4 = findViewById(R.id.tv4);
        String defLanguage = Locale.getDefault().getLanguage();
        tv4.setText(String.format("Locale.getDefault() - %s", defLanguage));
        tv4.setCompoundDrawablesWithIntrinsicBounds(null, null, getLanguageDrawable(defLanguage), null);
    }
    private void updateInfo(String title, TextView tv, Resources res) {
        Locale l = LocaleManager.getLocale(res);
        tv.setText(title + Utility.hexString(res) + String.format(" - %s", l.getLanguage()));
        Drawable icon = getLanguageDrawable(l.getLanguage());
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    private Drawable getLanguageDrawable(String language) {
        switch (language) {
            case LANGUAGE_ENGLISH:
                return ContextCompat.getDrawable(this, R.drawable.language_en);
            case LANGUAGE_UKRAINIAN:
                return ContextCompat.getDrawable(this, R.drawable.language_uk);
            default:
                Log.w(TAG, "Unsupported language");
                return null;
        }
    }
}