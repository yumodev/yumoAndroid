package com.yumo.android.test.web.browser;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.yumo.demo.view.YmTestFragment;


/**
 * Created by yumodev on 4/6/16.
 */
public class HtmlTestView extends YmTestFragment {

    public void testScheme(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test_html/test_scheme.html");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        //webView.setWebViewClient(new WebViewClient() {
        //    @Override
        //    public void onPageFinished(WebView view, String url) {
        //        super.onPageFinished(view, url);
        //
        //    }
        //});

        showTestView(webView);
    }
}
