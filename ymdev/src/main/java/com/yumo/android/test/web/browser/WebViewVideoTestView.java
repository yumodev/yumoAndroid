package com.yumo.android.test.web.browser;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;


import com.yumo.demo.view.YmTestFragment;

import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

/**
 * Created by yumodev on 17/10/9.
 */

public class WebViewVideoTestView extends YmTestFragment {

    private WebView mWebView;
    private View mCustomView;
    private FullscreenHolder mFullscreenContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;

    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    public void testOriginLandscape(){
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void testOriginPortrait(){
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void testShowHaodouVideo(){
        final WebView wv = new WebView(getContext());

        wv.loadUrl("http://m.haodou.com/recipe/877986/");

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        showTestView(wv);
    }

    public void testWebView(){
        String url = "http://m.haodou.com/recipe/877986/";
        mWebView = null;
        mWebView = new WebView(getContext());
        initWebView(url);
        showTestView(mWebView);
    }

    public void testDftt(){
        String url = "http://videoh5.eastday.com/?qid=onlyayoyllq";
        mWebView = null;
        mWebView = new WebView(getContext());
        initWebView(url);
        showTestView(mWebView);
    }

    /** 展示网页界面 **/
    public void initWebView(String webUrl) {
        WebChromeClient wvcc = new WebChromeClient();

        WebSettings set = mWebView.getSettings();
        set.setUseWideViewPort(true);
        set.setJavaScriptEnabled(true);
        set.setUserAgentString(null);
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        set.setLoadsImagesAutomatically(true);
        set.setBuiltInZoomControls(true);
        set.setDisplayZoomControls(false);
        set.setJavaScriptCanOpenWindowsAutomatically(false);
        set.setDefaultTextEncodingName(null);


        set.setMinimumFontSize(8);
        set.setMinimumLogicalFontSize(8);
        set.setDefaultFontSize(16);
        set.setDefaultFixedFontSize(13);
        set.setTextZoom(100);
        set.setLightTouchEnabled(false);
        set.setSaveFormData(true);
        set.setSavePassword(false);
        set.setNeedInitialFocus(false);
        set.setSupportMultipleWindows(true);
        set.setAllowFileAccess(true);
        set.setPluginState( WebSettings.PluginState.ON_DEMAND);
        set.setDatabaseEnabled(true);
        set.setDomStorageEnabled(true);
        set.setGeolocationEnabled(true);//
        set.setAppCacheEnabled(true);//
        set.setAppCacheMaxSize(0);
        set.setCacheMode(WebSettings.LOAD_DEFAULT);
        set.setLoadWithOverviewMode(true);

        mWebView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        };
        mWebView.setWebViewClient(wvc);

        mWebView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(getActivity());
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                onShowCustomView(view,  getContext().getResources().getConfiguration().orientation, callback);
            }

            @Override
            public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
                super.onShowCustomView(view, requestedOrientation, callback);
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });

        // 加载Web地址
        mWebView.loadUrl(webUrl);
    }


    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (mWebView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
        mFullscreenContainer = new FullscreenHolder(getActivity());
        mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
        mCustomView = view;
        setFullscreen(true);
        mCustomViewCallback = callback;
        mOriginalOrientation = getContext().getResources().getConfiguration().orientation;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (mCustomView == null) {
            return;
        }

        setFullscreen(false);
        FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer = null;
        mCustomView = null;
        mCustomViewCallback.onCustomViewHidden();
        mWebView.setVisibility(View.VISIBLE);
        getActivity().setRequestedOrientation(mOriginalOrientation);
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    public void setFullscreen(boolean enabled) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |= bits;
            if (mCustomView != null) {
                mCustomView.setSystemUiVisibility(SYSTEM_UI_FLAG_LOW_PROFILE);
            } else {
                mWebView.setSystemUiVisibility(SYSTEM_UI_FLAG_LOW_PROFILE);
            }

        } else {

            winParams.flags &= ~bits;
            if (mCustomView != null) {
                mCustomView.setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);

            } else {
                mWebView.setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        win.setAttributes(winParams);
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
//                if (mCustomView != null) {
//                    hideCustomView();
//                } else if (mWebView.canGoBack()) {
//                    mWebView.goBack();
//                } else {
//                    getActivity().finish();
//                }
//                return true;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }
}
