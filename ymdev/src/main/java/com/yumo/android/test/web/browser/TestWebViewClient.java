package com.yumo.android.test.web.browser;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yumo on 2/5/16.
 */
public class TestWebViewClient extends WebViewClient {

    private final String LOG_TAG = "TestWebViewClient";

    /**
     * 页面开始加载
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(LOG_TAG, "onPageStarted:" + url);
        if (favicon != null){
            Log.d(LOG_TAG, "onPageStarted: favicon is not null" );
        }
    }

    /**
     * 页面加载结束
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(LOG_TAG, "onPageFinished:" + url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(LOG_TAG, "shouldOverrideUrlLoading:"+url);
        view.loadUrl(url);
        return true;
        //return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Log.d(LOG_TAG, "onReceivedError:"+error.toString());
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        Log.d(LOG_TAG, "onReceivedHttpError:" + errorResponse.toString());
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
        Log.d(LOG_TAG, "onReceivedLoginRequest");
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        Log.d(LOG_TAG, "oldScale:"+oldScale + "newScale:"+newScale);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.d(LOG_TAG, "onLoadResource:"+url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        Log.d(LOG_TAG, "onPageCommitVisible:"+url);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        Log.d(LOG_TAG, "doUpdateVisitedHistory: isReload:"+isReload + " url:"+url);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        Log.d(LOG_TAG, "shouldOverrideKeyEvent");
        return super.shouldOverrideKeyEvent(view, event);

    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.d(LOG_TAG, "shouldInterceptRequest:"+request.toString());
        //Log.d(LOG_TAG, request.getMethod());

        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.d(LOG_TAG, "shouldInterceptRequest:"+url);
        if (url.equals("https://m.baidu.com/static/index/plus/plus_logo123.png")){
            WebResourceResponse response = null;
            try {
                InputStream localCopy = view.getContext().getAssets().open("baby.png");
                response = new WebResourceResponse("image/png", "UTF-8", localCopy);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        Log.d(LOG_TAG, "onFormResubmission");
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        Log.d(LOG_TAG, "onReceivedSslError:"+error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
        Log.d(LOG_TAG, "onReceivedClientCertRequest:");
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
        Log.d(LOG_TAG, "onReceivedHttpAuthRequest");
    }

    public void onUnhandledInputEvent(WebView view, InputEvent event) {
        Log.d(LOG_TAG, "onUnHandledInputEvent");
    }
}
