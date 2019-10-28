package com.yariksoffice.languagetest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.os.Build.VERSION_CODES.P;
import static com.yariksoffice.languagetest.App.TAG;

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

    public static void bypassHiddenApiRestrictions() {
        // http://weishu.me/2019/03/16/another-free-reflection-above-android-p/
        if (!isAtLeastVersion(P)) return;
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod",
                    String.class, Class[].class);

            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime",
                    null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass,
                    "setHiddenApiExemptions", new Class[]{ String[].class });
            Object sVmRuntime = getRuntime.invoke(null);

            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{ new String[]{ "L" } });
        } catch (Throwable e) {
            Log.e(TAG, "Reflect bootstrap failed:", e);
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
        try {
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
        } catch (Exception e) {
            // https://developer.android.com/about/versions/pie/restrictions-non-sdk-interfaces
            return "Can't access title cache";
        }
    }

    public static Resources getTopLevelResources(Activity a) {
        try {
            return a.getPackageManager().getResourcesForApplication(a.getApplicationInfo());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}