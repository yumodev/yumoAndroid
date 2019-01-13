package com.yumo.android.test.web.browser;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebIconDatabase;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.net.YmFileNetUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yumo on 4/25/16.
 */
public class WebViewImageTestView extends YmTestFragment {
    private final String LOG_TAG = "WebViewImageTestView";

    private WebView mWebView = null;
    private String mExera = "";
    private int mHitType = WebView.HitTestResult.IMAGE_TYPE;
    private String mLocalFileName = "/storage/emulated/0/Android/data/com.yumo.android/cache/image/testlocaimg";

    public void testImageContext(){
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View arg1,
                                            ContextMenu.ContextMenuInfo arg2) {
                MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        // do the menu action
                        switch (item.getItemId()) {
                            case 1:
                                Log.d("you click", "分享");
                                break;
                            case 2:
                                Log.i("you click", "下载");
                                break;
                        }
                        return true;
                    }
                };

                WebView.HitTestResult result = ((WebView) arg1).getHitTestResult();
                int resultType = result.getType();
                if ((resultType == WebView.HitTestResult.IMAGE_TYPE)) {
                    menu.setHeaderTitle(result.getExtra());

                    Intent i = new Intent();
                    MenuItem item = menu.add(0, 1, 0, "分享")
                            .setOnMenuItemClickListener(handler);
                    item.setIntent(i);

                    item = menu.add(0, 2, 0, "下载").setOnMenuItemClickListener(
                            handler);
                    item.setIntent(i);
                }
            }
        });


        showTestView(mWebView);
    }

    public void testImageResContext(){
        WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");
        webView.getSettings().setAllowFileAccess(true);

        registerForContextMenu(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

//        webView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//
//            public void onCreateContextMenu(ContextMenu menu, View arg1,
//                                            ContextMenu.ContextMenuInfo arg2) {
//                MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        // do the menu action
//                        switch (item.getItemId()) {
//                            case 1:
//                                Log.d("you click", "分享");
//                                break;
//                            case 2:
//                                Log.i("you click", "下载");
//                                break;
//                        }
//                        return true;
//                    }
//                };
//
//
//            }
//        });


        showTestView(webView);
    }

    MenuItem.OnMenuItemClickListener MenuClick = new MenuItem.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    // do the menu action
                    switch (item.getItemId()) {
                        case 1:
                            Log.d(LOG_TAG, "分享");
                            break;
                        case 2:
                            Log.d(LOG_TAG, "查看");
                            showImage();
                            break;
                        case 3:
                            downloadImage();
                            Log.d(LOG_TAG, "下载");
                    }
                    return true;
                }
    };


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        WebView.HitTestResult result = ((WebView) v).getHitTestResult();
        int resultType = result.getType();
        if ((resultType == WebView.HitTestResult.IMAGE_TYPE)) {
            menu.setHeaderTitle(result.getExtra());
            mExera = result.getExtra();

            menu.addSubMenu(0,1,0,"share");
            menu.addSubMenu(0,2,0,"view");
            menu.addSubMenu(0,3,0,"download");
        }

        for (int i = 0; i < menu.size(); i++){
            menu.getItem(i).setOnMenuItemClickListener(MenuClick);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void shareImage(){

    }

    private void showImage(){
        if (mHitType == WebView.HitTestResult.IMAGE_TYPE){
//            Uri uri = Uri.parse(mExera);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
        }
    }

    private void downloadImage(){
        (new DownloadAsyncTask()).execute(mExera);
    }

    class DownloadAsyncTask extends AsyncTask<String, Integer, String> {
        boolean mDownload = false;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            Log.d(LOG_TAG, "imageAsyncTask:url"+url);
            String urlName = YmFileUtil.getFileNameFromPath(url);
            String fileName = YmAdFileUtil.getFileCache(getContext(),"image")+"/"+urlName;
            if (YmFileNetUtil.downFile(url, fileName)){
                mDownload = true;
            }
            return fileName;
        }

        @Override
        protected void onPostExecute(String fileName) {
            super.onPostExecute(fileName);
            Log.d(LOG_TAG, "onPostExecute:fileName"+fileName);
            YmFileUtil.rename(fileName, mLocalFileName, true);
            if (mDownload){
                Toast.makeText(getContext(), "success:"+fileName, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "failed:"+fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void testLoadLoaclImage() {
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");

        mWebView.setWebViewClient(new TestWebViewClient());
        showTestView(mWebView);
    }

    public void testLoadLoaclNote() {
        WebView mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/test_note.html");

        mWebView.setWebViewClient(new TestWebViewClient());
        showTestView(mWebView);
    }

    public void testLoadAssertImage() {
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.loadUrl("file:///android_asset/test_image.html");


        mWebView.setWebViewClient(new TestWebViewClient());
        showTestView(mWebView);
    }

    ///storage/emulated/0/Android/data/com.yumo.android/cache/image/plus_logo.png
    public void testLoadSdcardImage(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.loadUrl("file:///android_asset/test_image.html");
        //mWebView.getSettings().setAllowFileAccess(false);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.equals("https://m.baidu.com/static/index/plus/plus_logo123.png")){
                    WebResourceResponse response = null;
                    try {
                        String fileName = mLocalFileName;
                        File file = new File(fileName);
                        InputStream localIn = new FileInputStream(file);
                        response = new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }
                return super.shouldInterceptRequest(view, url);
            }
        });

        showTestView(mWebView);
    }


    ///storage/emulated/0/Android/data/com.yumo.android/cache/image/plus_logo.png
    public void testBaseLoadSdcardImage(){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        //mWebView.loadUrl("http://www.baidu.com");
       // String html = "<img src=\"file:///storage/emulated/0/Android/data/com.yumo.android/cache/image/plus_logo.png\"></img>";
        String html = "<img src=\"https://m.baidu.com/static/index/plus/plus_logo.png\"></img>";
        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        //mWebView.getSettings().setAllowFileAccess(false);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d(LOG_TAG, "shouldInterceptRequest:"+url);
                if (url.equals("https://m.baidu.com/static/index/plus/plus_logo.png")){
                    WebResourceResponse response = null;
                    try {
                        String fileName = mLocalFileName;
                        File file = new File(fileName);
                        InputStream localIn = new FileInputStream(file);
                        response = new WebResourceResponse("image/png", "UTF-8", localIn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }
                return super.shouldInterceptRequest(view, url);
            }
        });

        showTestView(mWebView);
    }

    public void testCleanCache() {
        WebIconDatabase.getInstance().removeAllIcons();
        WebView webView = new WebView(getContext());
        //会用应用内所有的webView生效,已有的webview需要刷新
        webView.clearCache(true);
        webView.destroy();
    }

}
