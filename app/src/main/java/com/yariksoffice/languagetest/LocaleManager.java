package com.yariksoffice.languagetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.preference.PreferenceManager;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

public class LocaleManager {

    public static final  String LANGUAGE_ENGLISH   = "en";
    public static final  String LANGUAGE_UKRAINIAN = "uk";
    public static final  String LANGUAGE_RUSSIAN   = "ru";
    private static final String LANGUAGE_KEY       = "language_key";

    private final SharedPreferences prefs;

    public LocaleManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLocale(Context c) {
        update(c, getLanguage());
    }

    public void setNewLocale(Context c, String language) {
        persistLanguage(language);
        update(c, language);
    }

    private void update(Context c, String language) {
        updateResources(c, language);
        Context appContext = c.getApplicationContext();
        if (c != appContext) {
            updateResources(appContext, language);
        }
    }

    public String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Utility.isAtLeastVersion(N)) {
            setLocaleForApi24(config, locale);
        } else if (Utility.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @RequiresApi(api = N)
    private void setLocaleForApi24(Configuration config, Locale target) {
        Set<Locale> set = new LinkedHashSet<>();
        // bring the target locale to the front of the list
        set.add(target);

        LocaleList all = LocaleList.getDefault();
        for (int i = 0; i < all.size(); i++) {
            // append other locales supported by the user
            set.add(all.get(i));
        }

        Locale[] locales = set.toArray(new Locale[0]);
        config.setLocales(new LocaleList(locales));
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Utility.isAtLeastVersion(N) ? config.getLocales().get(0) : config.locale;
    }
}