package com.yumo.android.test.web.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;

/**
 * Created by yumo on 2/5/16.
 */
public class TestWebView extends WebView {

    private final String LOG_TAG = "TestWebView";

    public TestWebView(Context context) {
        super(context);
    }

    public TestWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()){
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
