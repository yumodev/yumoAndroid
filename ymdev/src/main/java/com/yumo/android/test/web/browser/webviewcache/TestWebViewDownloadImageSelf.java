package com.yumo.android.test.web.browser.webviewcache;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yumo.android.common.utils.DownImageUtils;
import com.yumo.android.test.web.browser.utils.WebViewUtils;
import com.yumo.common.io.YmFileUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumo on 7/22/16.
 * adb logcat | grep -E 'DownloadImageSelf|DownloadImageTaskLoader'
 */
public class TestWebViewDownloadImageSelf extends YmTestFragment {
    private final String LOG_TAG = "DownloadImageSelf";
    private WebView mWebView = null;
    private List<String> mImageUrlList = new ArrayList<>();
    private DownloadImageTaskLoader mDownloadImageTask = null;
    public void testLoadSdcardImage(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
       // final String webUrl = "http://news.sina.cn/2016-07-24/detail-ifxuhuma7606886.d.html?vt=4&pos=3";
        final String webUrl = "file:///android_asset/test_download_image.html";
        mWebView.loadUrl(webUrl);
        //mWebView.getSettings().setAllowFileAccess(false);
        WebViewUtils.enableWebContentDebugMode(mWebView);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.equals(webUrl)){
                    return null;
                }

                String extension = YmFileUtil.getFileExtension(url);
                if (TextUtils.isEmpty(extension)){
                    return super.shouldInterceptRequest(view, url);
                }
                Log.d(LOG_TAG, "extension:"+extension);
                url = url.replace("file:///android_asset/","");
                if (url.indexOf("file") == 0){
                    try {
                        //String fileName = mLocalFileName;
                        String hash = "";
                        int nFind = url.lastIndexOf('/');
                        if (nFind > 0) {
                            hash =  url.substring(nFind + 1);
                        }
                        String fileName = DownImageUtils.getImagePath(hash);
                        File file = new File(fileName);
                        InputStream localIn = new FileInputStream(file);
                         return new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


                WebResourceResponse response = null;
                if (extension.equals("png") || extension.equals("jpeg")){
                    mImageUrlList.add(url);
                    try {
                        //String fileName = mLocalFileName;
                        // File file = new File(fileName);
                        InputStream localIn = getActivity().getAssets().open("baby.png");
                        response = new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return response;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "onPageFinished");
                new DownloadImageTaskLoader().startDownload(getActivity(), mWebView, mImageUrlList);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "onPageStarted");
                mImageUrlList.clear();
            }

        });

        showTestView(mWebView);
    }


    public void testLoadSdcardImage1(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        // final String webUrl = "http://news.sina.cn/2016-07-24/detail-ifxuhuma7606886.d.html?vt=4&pos=3";
        final String webUrl = "file:///android_asset/test_download_image.html";
        mWebView.loadUrl(webUrl);
        //mWebView.getSettings().setAllowFileAccess(false);
        WebViewUtils.enableWebContentDebugMode(mWebView);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.indexOf("http") == 0 && url.indexOf("MXRES") < 0){
                    return null;
                }

                String hash = "";
                if (url.indexOf("http") == 0){
                    hash = getStringValueFromImageUrl(url, "MXRES");
                }else if (url.indexOf("file") == 0){
                    int nFind = url.lastIndexOf('/');
                    if (nFind > 0) {
                        hash =  url.substring(nFind + 1);
                    }
                }

                if (TextUtils.isEmpty(hash)){
                    return null;
                }

                try {
                    //String fileName = mLocalFileName;
                    String fileName = DownImageUtils.getImagePath(hash);
                    File file = new File(fileName);
                    InputStream localIn = new FileInputStream(file);
                    return new WebResourceResponse("image/png", "UTF-8", localIn);
                } catch (IOException e) {
                    e.printStackTrace();
                    mImageUrlList.add(url);
                    try {
                        //String fileName = mLocalFileName;
                        // File file = new File(fileName);
                        InputStream localIn = getActivity().getAssets().open("baby.png");
                        return new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "onPageFinished");
                new DownloadImageTaskLoader().startDownload(getActivity(), mWebView, mImageUrlList);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "onPageStarted");
                mImageUrlList.clear();
            }

        });

        showTestView(mWebView);
    }

    /**
     * @param originUrl
     * @return
     */
    public String getStringValueFromImageUrl(String originUrl, String key){
        String regImg= String.format("%s_([0-9A-Z]+)-", key);
        String value = "";
        Pattern pattern = Pattern.compile(regImg);
        Matcher matcher = pattern.matcher(originUrl);
        while (matcher.find()){
            value = matcher.group(1);
        }

        return value;
    }


    public void testLoadSdcardImage2(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        // final String webUrl = "http://news.sina.cn/2016-07-24/detail-ifxuhuma7606886.d.html?vt=4&pos=3";
        final String webUrl = "file:///android_asset/test_download_image.html";
        mWebView.loadUrl(webUrl);
        //mWebView.getSettings().setAllowFileAccess(false);
        WebViewUtils.enableWebContentDebugMode(mWebView);
        mWebView.getSettings().setBlockNetworkImage(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.indexOf("http") == 0 && url.indexOf("MXRES") < 0){
                    return null;
                }

                String hash = "";
                if (url.indexOf("http") == 0){
                    hash = getStringValueFromImageUrl(url, "MXRES");
                }else if (url.indexOf("file") == 0){
                    int nFind = url.lastIndexOf('/');
                    if (nFind > 0) {
                        hash =  url.substring(nFind + 1);
                    }
                }

                if (TextUtils.isEmpty(hash)){
                    return null;
                }

                try {
                    //String fileName = mLocalFileName;
                    hash = DownImageUtils.downloadImage(url);
                    String fileName = DownImageUtils.getImagePath(hash);
                    File file = new File(fileName);
                    InputStream localIn = new FileInputStream(file);
                    return new WebResourceResponse("image/png", "UTF-8", localIn);
                } catch (IOException e) {
                    e.printStackTrace();
                    mImageUrlList.add(url);

                    try {
                        //String fileName = mLocalFileName;
                        // File file = new File(fileName);
                        InputStream localIn = getActivity().getAssets().open("baby.png");
                        return new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "onPageFinished");
                new DownloadImageTaskLoader().startDownload(getActivity(), mWebView, mImageUrlList);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "onPageStarted");
                mImageUrlList.clear();
            }

        });

        showTestView(mWebView);
    }


    public void testLoadSdcardImage3(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        // final String webUrl = "http://news.sina.cn/2016-07-24/detail-ifxuhuma7606886.d.html?vt=4&pos=3";
        final String webUrl = "file:///android_asset/test_download_image.html";
        mWebView.loadUrl(webUrl);
        //mWebView.getSettings().setAllowFileAccess(false);
        WebViewUtils.enableWebContentDebugMode(mWebView);
        mWebView.getSettings().setBlockNetworkImage(true);

        mDownloadImageTask = new DownloadImageTaskLoader();
        mDownloadImageTask.init(getActivity(), mWebView);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.indexOf("http") == 0 && url.indexOf("MXRES") < 0){
                    return null;
                }

                String hash = "";
                if (url.indexOf("http") == 0){
                    hash = getStringValueFromImageUrl(url, "MXRES");
                }else if (url.indexOf("file") == 0){
                    int nFind = url.lastIndexOf('/');
                    if (nFind > 0) {
                        hash =  url.substring(nFind + 1);
                    }
                }

                if (TextUtils.isEmpty(hash)){
                    return null;
                }

                try {
                    //String fileName = mLocalFileName;
                    //hash = DownImageUtils.downloadImage(url);
                    String fileName = DownImageUtils.getImagePath(hash);
                    File file = new File(fileName);
                    InputStream localIn = new FileInputStream(file);
                    return new WebResourceResponse("image/png", "UTF-8", localIn);
                } catch (IOException e) {
                    e.printStackTrace();
                    mImageUrlList.add(url);
                    mDownloadImageTask.startDownloadUrl(url);
                    try {
                        //String fileName = mLocalFileName;
                        // File file = new File(fileName);
                        InputStream localIn = getActivity().getAssets().open("baby.png");
                        return new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "onPageFinished");


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "onPageStarted");
                mImageUrlList.clear();
            }

        });

        showTestView(mWebView);
    }

    public void testLoadSdcardGif(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        // final String webUrl = "http://news.sina.cn/2016-07-24/detail-ifxuhuma7606886.d.html?vt=4&pos=3";
        final String webUrl = "file:///android_asset/test_download_image.html";
        mWebView.loadUrl(webUrl);
        //mWebView.getSettings().setAllowFileAccess(false);
        WebViewUtils.enableWebContentDebugMode(mWebView);
        mWebView.getSettings().setBlockNetworkImage(true);

        mDownloadImageTask = new DownloadImageTaskLoader();
        mDownloadImageTask.init(getActivity(), mWebView);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.indexOf("http") == 0 && url.indexOf("MXRES") < 0){
                    return null;
                }

                try {
                    //String fileName = mLocalFileName;
                    // File file = new File(fileName);
                    InputStream localIn = getActivity().getAssets().open("loading.gif");
                    return new WebResourceResponse("image/gif", "UTF-8", localIn);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }

                return null;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
                Log.d(LOG_TAG, "onPageFinished");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(LOG_TAG, "onPageStarted");
            }

        });

        showTestView(mWebView);
    }
}
