package com.yumodev.ymdev1.web;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ymdev1.web.x5.X5WebView;

/**
 * Created by yumodev on 17/9/11.
 */

public class X5TestView extends YmTestFragment {
    public void testShowMessage(){
        showToastMessage("test");
    }

    public void testShowX5(){
        X5WebView mWebView = new X5WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }
}
