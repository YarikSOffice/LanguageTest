package com.yariksoffice.languagetest;

import android.content.res.Resources;

import java.lang.reflect.Field;

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
}