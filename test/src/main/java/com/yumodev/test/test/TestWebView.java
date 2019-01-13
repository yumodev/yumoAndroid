package com.yumodev.test.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yumodev on 17/6/26.
 */

public class TestWebView extends WebView {
    private final String LOG_TAG = "TestWebView";
    public TestWebView(Context context) {
        this(context, null);
    }

    public TestWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(LOG_TAG, "test TestWebView");

        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }
}
