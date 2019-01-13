package com.yumo.android.test.web.browser.safejavajs;

import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by yumo on 2/14/16.
 */
public class JsCallUtils {
    public static void toast(WebView webView, String message){
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
