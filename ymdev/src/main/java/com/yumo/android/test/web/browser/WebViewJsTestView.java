package com.yumo.android.test.web.browser;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yumo.android.test.web.browser.utils.TestJsCall;
import com.yumo.common.io.YmAssertUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

/**
 * Created by yumo on 4/6/16.
 */
public class WebViewJsTestView extends YmTestFragment {
    public final String LOG_TAG = "WebViewJsTestView";

    @JavascriptInterface
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

    public class CallByJs{
        @JavascriptInterface
        public void showAlert(String message){
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    public void testGetBody(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "testGetBody(): onPageFinished: url:" + url);
                String callJs = String.format("javascript:window.%s.getBodyHtml(document.body.innerHTML);", TestJsCall.JSCALL);
                webView.loadUrl(callJs);
            }
        });


        webView.addJavascriptInterface(new TestJsCall(), TestJsCall.JSCALL);
        showTestView(webView);
    }

    public void testTitle(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "testTitle: onPageFinished: url:" + url);
                webView.loadUrl(TestJsCall.getTitle());
            }
        });


        webView.addJavascriptInterface(new TestJsCall(), TestJsCall.JSCALL);
       ////// showTestView(webView);
    }

    public void testURL(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "testURL: onPageFinished: url:" + url);
                webView.loadUrl(TestJsCall.getURL());
            }
        });


        webView.addJavascriptInterface(new TestJsCall(), TestJsCall.JSCALL);
        //showTestView(webView);
    }

    public void testDescription(){
        final WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.cnblogs.com/");

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "testURL: onPageFinished: url:" + url);
                String js = null;
                try {
                    js = YmAssertUtil.getAssertFileToString(getActivity(), "js/description.js");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                webView.loadUrl("javascript:"+js);
            }
        });


        webView.addJavascriptInterface(new TestJsCall(), TestJsCall.JSCALL);
        //showTestView(webView);
    }


}
