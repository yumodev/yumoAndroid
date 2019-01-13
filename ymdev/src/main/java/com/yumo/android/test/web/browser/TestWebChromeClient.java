package com.yumo.android.test.web.browser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by wks on 2/5/16.
 */
public class TestWebChromeClient extends WebChromeClient {
    private final String LOG_TAG = "TestWebChromeClient";

    public TestWebChromeClient() {
        super();
    }

    /**
     * 加载进度
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.d(LOG_TAG, "newProgress:"+newProgress);
    }

    /**
     * 获取网站标题
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Log.d(LOG_TAG, "onReceivedTitle");
    }

    /**
     * 获取网站logo
     * @param view
     * @param icon
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        if (icon != null){
            Log.d(LOG_TAG, "onReceivedIcon: not null");
        }else{
            Log.d(LOG_TAG, "onReceivedIcon: is null");
        }
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        super.onReceivedTouchIconUrl(view, url, precomposed);
        Log.d(LOG_TAG, "onReceivedTouchIconUrl:");
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        Log.d(LOG_TAG, "onShowCustomView");
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        Log.d(LOG_TAG, "onHideCustomView");
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        Log.d(LOG_TAG, "onCreateWindow");
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
        Log.d(LOG_TAG, "onRequestFocus");
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        Log.d(LOG_TAG, "onCloseWindow");
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.d(LOG_TAG, "onJsConfirm: url:"+url + " message:"+message);
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.d(LOG_TAG, "onJsPrompt: url:"+url+ " message:"+message + " defaultValue:"+defaultValue);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Log.d(LOG_TAG, "onJsBeforeUnload:"+url + " message:"+message);
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        Log.d(LOG_TAG, "onGeolocationPermissionsShowPrompt");
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
        Log.d(LOG_TAG, "onGeolocationPermissionsHidePrompt");
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
        Log.d(LOG_TAG, "onPermissionRequest");
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(LOG_TAG, "onConsoleMessage");
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        Log.d(LOG_TAG, "getDefaultVidwoPoster");
        return super.getDefaultVideoPoster();
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
        Log.d(LOG_TAG, "onPermissionRequestCanceled");
    }

    @Override
    public View getVideoLoadingProgressView() {
        Log.d(LOG_TAG, "getVideoLoadingProgressView");
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        super.getVisitedHistory(callback);
        Log.d(LOG_TAG, "getVisitedHistory");
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        Log.d(LOG_TAG, "onShowFileChooser");
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}

