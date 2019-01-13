package com.yumo.android.test.web.browser;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yumo.android.common.YumoConfig;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;

/**
 * Created by yumodev on 17/10/15.
 * WebView的离线缓存
 */

public class WebViewOfflineTestView extends YmTestFragment {
    private WebView mWebView = null;

    /**
     * 测试保存离线网页
     */
    public void testSaveWebArchive(){
        mWebView = new WebView(getContext());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //return super.shouldOverrideUrlLoading(view, request);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.saveWebArchive(getMhtPath());
                Log.i(YumoConfig.LOG_TAG, "onPageFinished:"+getMhtPath());
            }
        });
        mWebView.loadUrl("https://m.hao123.com");
        showTestView(mWebView);
    }

    public void testLoadSaveWebArchive(){
        mWebView = new WebView(getContext());
        WebSettings sets = mWebView.getSettings();
        sets.setAllowFileAccess(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //return super.shouldOverrideUrlLoading(view, request);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //view.saveWebArchive(getMhtPath());
            }
        });
        mWebView.loadUrl("file://"+getMhtPath());
        showTestView(mWebView);
    }

    private String getMhtPath(){
        return YumoConfig.getFileDirectory()+ File.separator+"demo.mht";
    }
}
