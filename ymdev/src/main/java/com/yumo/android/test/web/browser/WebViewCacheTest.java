package com.yumo.android.test.web.browser;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yumo.android.common.YumoConfig;
import com.yumo.common.log.Log;
import com.yumo.common.net.YmNetUtils;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;

/**
 * Created by yumodev on 17/10/19.
 * 测试网页缓存的使用。
 *
 * LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
 LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
 LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
 LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 */

public class WebViewCacheTest extends YmTestFragment {

    private WebView mWebView = null;
    /**
     * 使用当前应用打开一个网址。
     */
    public void testWebView(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        cacheSettings(mWebView);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    private void cacheSettings(WebView webView){
        if (YmNetUtils.isNetworkAvailable(getContext())){
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + File.separator+ "ymwebcache";
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i(YumoConfig.LOG_TAG, "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        webView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能we
        webView.getSettings().setAppCacheEnabled(true);
    }

    public void testWebView1(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");

        if (YmNetUtils.isNetworkAvailable(getContext())){
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }
}
