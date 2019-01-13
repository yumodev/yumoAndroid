package com.yumo.android.test.web.browser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 16/11/17.
 */

public class WebViewSettingsTestView extends YmTestFragment {

    private WebView mWebView = null;
    private String mUrl = "https://www.hao123.com/";

    private WebView getWebView(String url){
        WebView wv = new WebView(getContext());
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        return wv;
    }

    /**
     * 使用当前应用打开一个网址。
     */
    public void testSupportZoom(){
        mWebView = getWebView(mUrl);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        showTestView(mWebView);
    }

    /**
     *
     */
    public void testAllowContentAccess(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowContentAccess(false);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        showTestView(mWebView);
    }


}
