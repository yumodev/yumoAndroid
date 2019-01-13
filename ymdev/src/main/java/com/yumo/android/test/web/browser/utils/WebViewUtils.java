package com.yumo.android.test.web.browser.utils;

import android.os.Build;
import android.webkit.WebView;

/**
 * Created by yumo on 7/24/16.
 */
public class WebViewUtils{

        public static void enableWebContentDebugMode(WebView webView) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
}
