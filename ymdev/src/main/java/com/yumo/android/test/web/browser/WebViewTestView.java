package com.yumo.android.test.web.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.android.R;
import com.yumo.android.test.web.browser.utils.FaviconUtils;
import com.yumo.common.debug.RuntimeUtils;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.media.YmImageUtil;
import com.yumo.common.media.YmViewSnapshotUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumo.android.test.web.browser.safejavajs.InjectedChromeClient;
import com.yumo.android.test.web.browser.safejavajs.JsCallUtils;

import java.io.IOException;

/**
 * Created by yumo on 1/4/16.
 */
public class WebViewTestView extends YmTestFragment {
    private final String LOG_TAG = "WebViewTestView";

    private WebView mWebView = null;

    public void WebViewTestView(){
//        String path = YmAdFileUtil.getAppDataCacheDir(getContext())+"/icons/";
//        YmFileUtil.createDirectory(path);
//        WebIconDatabase.getInstance().open(path);
    }

    /**
     * 使用当前应用打开一个网址。
     */
    public void testWebView(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.loadUrl("https://msms.aligo.in/login.html");


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return false;
            }
        });

        showTestView(mWebView);
    }

    public void testWebViewAssertHtml(){
        WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl("file:///android_asset/test_js.html");
        webView.loadUrl("http://opentest.icongtai.com/h5/product/oncereport/index.html?channel=SDK-WST&adKey=xj_ddl&key=dea5fb458218a570_20180626102642&sign=FBgAkmXVYvmU%2BxynZLovnhnFmf0%3D&_z_init=http%3A%2F%2Fopentest.icongtai.com%2Fsdk%2Fregister%2Fregist4SDK%3Fuid%3D863455039064045%26appId%3DSDK-WST%26sign%3D2UuiL4aP6d2hjXHajfB%252FGoSS6P0%253D#/track");

        showTestView(webView);
    }

    /**
     * 使用系统默认浏览器打开一个网址
     */
    public void testOpenUrl(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    /**
     * 使用系统默认浏览器打开一个HTTPS网址
     */
    public void testOpenUrlHttps(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.v2ex.com"));
        startActivity(intent);
    }

    /**
     * 使用测试后退当前应用打开一个网址。
     */
    public void testWebViewClient(){
        TestWebView mWebView = new TestWebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.ifeng.com");

        mWebView.setWebViewClient(new TestWebViewClient());
        mWebView.setWebChromeClient(new TestWebChromeClient());

        showTestView(mWebView);
    }

    /**
     * 设置TextView的字体大小
     */
    public void testWebViewTextSize(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        //mWebView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);

        float scaleDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        int testSize = (int)(50 * scaleDensity + 0.5f);
        mWebView.getSettings().setDefaultFontSize(48);
        mWebView.getSettings().setDefaultFixedFontSize(48);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    /**
     * 设置TextView的字体的缩放
     */
    public void testWebViewTextZoom(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        //mWebView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);

        WebSettings settings = mWebView.getSettings();
        settings.setTextZoom(settings.getTextZoom() + 50);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    /**
     * 设置TextView的字符编码
     */
    public void testWebViewTextEncoding(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.getSettings().setDefaultTextEncodingName("Latin-1");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    /**
     * 设置无图模式
     */
    public void testWebViewBlockNetworkImage(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.getSettings().setBlockNetworkImage(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    /**
     * 显示进度条的浏览器
     */
    public void testWebViewProgressBar(){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);

        //创建一个水平进度条，并添加到富容器中。
        final ProgressBar progressBar = new ProgressBar(getContext(),null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
        rootView.addView(progressBar);

        //新建一个WebView,加入父容器中，并打开http://www.baidu.com.
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");
        rootView.addView(webView, 1);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                RuntimeUtils.beginRecordTimeByTag("webview");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("webview", RuntimeUtils.endRecordTimeByTag("webview")+"");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);

                }
            }
        });


        showTestView(rootView);
    }

    /**
     * 显示错误的界面
     */
    public void testWebViewErrorPage(){
        RelativeLayout rootView = new RelativeLayout(getContext());

        final TextView errorView = new TextView(getContext());
        errorView.setVisibility(View.GONE);
        rootView.addView(errorView);


        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        //一个错误的网址
        webView.loadUrl("http://www.222baidu1234.com");

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                webView.setVisibility(View.GONE);
//                errorView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                //super.onReceivedError(view, errorCode, description, failingUrl);
//                webView.setVisibility(View.GONE);
//                errorView.setVisibility(View.VISIBLE);
//            }
//        });

        webView.setWebViewClient(new TestWebViewClient());
        rootView.addView(webView,1);
        showTestView(rootView);
    }

    //js
    @SuppressLint("JavascriptInterface")
    public void testWebViewCallJs(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test_js.html");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "testWebViewCallJs: onPageFinished: url:" + url);
                String callJs = "javascript:concat(\'a\', \'b\')";
                //String callJs = "javascript:hello()";
                view.loadUrl(callJs);
            }
        });


        webView.addJavascriptInterface(new CallByJs(), "actionJava");
        showTestView(webView);
    }

    @SuppressLint("JavascriptInterface")
    public void testWebVieWEvaluateJavascript(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test_js.html");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.evaluateJavascript("javascript:getWindowInnerWidth()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "evaluateJavascript:" + value);
                    }
                });
            }
        });


        showTestView(webView);
    }

    public class CallByJs{
        @JavascriptInterface
        public void showAlert(String message){
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    public void testSafetyJsCallJava(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/test_safe_js_call_java.html");

        webView.setWebChromeClient(new InjectedChromeClient("android", JsCallUtils.class) {
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });


        showTestView(webView);
    }

    /**
     * 截取屏幕，包括 当前屏幕和整个网页的截图
     */
    public void testWebViewFullPage(){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);

        //新建一个WebView,加入父容器中，并打开http://www.baidu.com.
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");
        rootView.addView(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String savePath = null;
                        Bitmap bitmap = YmViewSnapshotUtil.getWebViewShopshat(webView);

                        if (bitmap != null) {
                            savePath = createImageName(getContext(), "webview");
                            try {
                                YmImageUtil.saveBitmapToFile(bitmap, savePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (!TextUtils.isEmpty(savePath)) {
                            YmImageUtil.showImageInView(getContext(), savePath);
                        }
                    }
                }, 5000);

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });


        showTestView(rootView);
    }

    private String createImageName(Context context, String prefix) {
        long time = System.currentTimeMillis();
        return YmAdFileUtil.getFileCache(context, "snapshot") + prefix+"_" + time +".jpg";
    }


    public void testGetFaviconWebView(){
        //https://m.baidu.com/favicon.ico
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
       // mWebView.loadUrl("https://m.baidu.com");
        mWebView.loadUrl("http://www.baidu.com");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                Log.d(LOG_TAG, "onReceivedIcon:"+(icon != null));
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
                Log.d(LOG_TAG, "onReceivedTouchIconUrl"+url);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(LOG_TAG, "onReceivedTitle:"+title);
            }
        });

        showTestView(mWebView);
    }

    public void testGetFavicon(){
        String url = "https://m.baidu.com";
        FaviconUtils.getFaviconByUrl(url);
    }

    public void testWebViewSelectHtml(){
        WebView webView = new WebView(getContext());
        webView.setBackgroundColor(getResources().getColor(R.color.common_page_bg));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test_html/test_select.html");
        showTestView(webView);
    }

    /**
     * 使用当前应用打开一个网址。
     */
    public void testStuff(){
        WebView mWebView = new WebView(getContext());
        mWebView.loadUrl("http://i.stuff.co.nz");
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
        //在使用知乎页面后台打开网页时，不能正常后台打开，设置为true，会调用onCreateWindow回调，设置为false不会进行回调。
        set.setSupportMultipleWindows(true);
        // Turn off file access
        set.setAllowFileAccess(true);
        set.setPluginState( WebSettings.PluginState.ON_DEMAND);
        set.setDatabaseEnabled(true);
        set.setDomStorageEnabled(true);
        set.setGeolocationEnabled(true);//
        set.setAppCacheEnabled(true);//
        set.setAppCacheMaxSize(0);
       // set.setAppCachePath(b.appCachePath);
        set.setCacheMode(WebSettings.LOAD_DEFAULT);
        //set.setDatabasePath(b.databasePath);
        set.setLoadWithOverviewMode(true);
        //set.setGeolocationDatabasePath(b.geolocationDatabasePath);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(LOG_TAG, " shouldOverrideUrlLoading "+url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(LOG_TAG, " onPageStarted "+url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(LOG_TAG, " onPageFinished "+url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(LOG_TAG, "newProgress:"+newProgress);
            }
        });

        showTestView(mWebView);
    }

    /**
     * 使用当前应用打开一个网址。
     */
    public void testStuff1(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://i.stuff.co.nz");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    public void test1(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.skyhide.net/flexibleh5/us_welfarecard/");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }


            @Override public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.i(LOG_TAG, "onReceivedError:"+errorCode+" "+description);

            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                super.onConsoleMessage(message, lineNumber, sourceID);
                Log.i(LOG_TAG, "onConsoleMessage:"+message+" "+lineNumber+" "+sourceID);
            }
        });



        showTestView(mWebView);
    }

}
