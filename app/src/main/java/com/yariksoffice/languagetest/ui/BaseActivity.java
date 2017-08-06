package com.yariksoffice.languagetest.ui;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.yariksoffice.languagetest.LocaleManager;
import com.yariksoffice.languagetest.R;
import com.yariksoffice.languagetest.Utility;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import static android.content.pm.PackageManager.GET_META_DATA;
import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_ENGLISH;
import static com.yariksoffice.languagetest.LocaleManager.LANGUAGE_UKRAINIAN;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        LocaleManager.setLocale(this);
        resetTitles();
    }

    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
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
        showTitleCache();
    }

    private void showResourcesInfo() {
        Resources topLevelRes = getTopLevelResources();
        updateInfo("Top level  ", findViewById(R.id.tv1), topLevelRes);

        Resources appRes = getApplication().getResources();
        updateInfo("Application  ", findViewById(R.id.tv2), appRes);

        Resources actRes = getResources();
        updateInfo("Activity  ", findViewById(R.id.tv3), actRes);

        TextView tv4 = findViewById(R.id.tv4);
        String defLanguage = Locale.getDefault().getLanguage();
        tv4.setText("Locale.getDefault()  ");
        tv4.setCompoundDrawablesWithIntrinsicBounds(null, null, getLanguageDrawable(defLanguage), null);
    }

    private Resources getTopLevelResources() {
        try {
            return getPackageManager().getResourcesForApplication(getApplicationInfo());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateInfo(String title, TextView tv, Resources res) {
        tv.setText(title + Utility.hexString(res));
        Locale l = LocaleManager.getLocale(res);
        Drawable icon = getLanguageDrawable(l.getLanguage());
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @SuppressWarnings("unchecked")
    private void showTitleCache() {
        Object o = Utility.getPrivateField("android.app.ApplicationPackageManager", "sStringCache", null);
        Map<?, WeakReference<CharSequence>> cache = (Map<?, WeakReference<CharSequence>>) o;
        if (cache == null) return;

        StringBuilder builder = new StringBuilder("Cache:").append("\n");
        for (Entry<?, WeakReference<CharSequence>> e : cache.entrySet()) {
            CharSequence title = e.getValue().get();
            if (title != null) {
                builder.append(title).append("\n");
            }
        }
        TextView tv = findViewById(R.id.cache);
        tv.setText(builder.toString());
    }

    private Drawable getLanguageDrawable(String language) {
        switch (language) {
            case LANGUAGE_ENGLISH:
                return ContextCompat.getDrawable(this, R.drawable.language_en);
            case LANGUAGE_UKRAINIAN:
                return ContextCompat.getDrawable(this, R.drawable.language_uk);
            default:
                throw new IllegalArgumentException("Unsupported language");
        }
    }
}