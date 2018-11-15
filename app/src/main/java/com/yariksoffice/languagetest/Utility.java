package com.yariksoffice.languagetest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import static android.content.pm.PackageManager.GET_META_DATA;

public class Utility {

    public static String hexString(Resources res) {
        Object resImpl = getPrivateField("android.content.res.Resources", "mResourcesImpl", res);
        Object o = resImpl != null ? resImpl : res;
        return "@" + Integer.toHexString(o.hashCode());
    }

    public static Object getPrivateField(String className, String fieldName, Object object) {
        try {
            Class c = Class.forName(className);
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(object);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetActivityTitle(Activity a) {
        try {
            ActivityInfo info = a.getPackageManager().getActivityInfo(a.getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                a.setTitle(info.labelRes);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static String getTitleCache() {
        Object o = Utility.getPrivateField("android.app.ApplicationPackageManager", "sStringCache", null);
        Map<?, WeakReference<CharSequence>> cache = (Map<?, WeakReference<CharSequence>>) o;
        if (cache == null) return "";

        StringBuilder builder = new StringBuilder("Cache:").append("\n");
        for (Entry<?, WeakReference<CharSequence>> e : cache.entrySet()) {
            CharSequence title = e.getValue().get();
            if (title != null) {
                builder.append(title).append("\n");
            }
        }
        return builder.toString();
    }

    public static Resources getTopLevelResources(Activity a) {
        try {
            return a.getPackageManager().getResourcesForApplication(a.getApplicationInfo());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}