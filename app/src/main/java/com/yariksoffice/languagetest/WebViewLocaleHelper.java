package com.yariksoffice.languagetest;

import android.content.Context;
import android.webkit.WebView;

/**
 * WebViewLocaleHelper implements a workaround that fixes the unwanted side effect while
 * using a WebView introduced in Android N.
 *
 * For unknown reasons, the very first creation of a WebView (either programmatically
 * or via inflation) resets an application locale to the device default.
 * More on that: https://issuetracker.google.com/issues/37113860
 */
public class WebViewLocaleHelper {

    private boolean requireWorkaround = true;

    public void implementWorkaround(Context context) {
        if (requireWorkaround) {
            requireWorkaround = false;
            new WebView(context).destroy();
            App.localeManager.setLocale(context);
        }
    }
}
