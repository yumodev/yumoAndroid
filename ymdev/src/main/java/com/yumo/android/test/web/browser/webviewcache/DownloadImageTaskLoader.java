package com.yumo.android.test.web.browser.webviewcache;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import com.yumo.android.common.utils.DownImageUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumo on 7/24/16.
 *
 */
public class DownloadImageTaskLoader {

    private final String LOG_TAG = "DownloadImageTaskLoader";
    private WeakReference<WebView> mWebView = null;
    private WeakReference<Activity> mActivity = null;
    private List<String> mImageUrlList = new ArrayList<>();
    private boolean mIsDownling = false;

    public void startDownload(final Activity activity, final WebView webView, List<String> imageUrlList){
        mActivity = new WeakReference<Activity>(activity);
        mWebView = new WeakReference<WebView>(webView);
        mImageUrlList.addAll(imageUrlList);

        if (!mIsDownling){
            DownloadIamgeTask task = new DownloadIamgeTask();
            task.execute();
        }
    }

    public void init(final Activity activity, final WebView webView){
        mActivity = new WeakReference<Activity>(activity);
        mWebView = new WeakReference<WebView>(webView);
    }

    public void startDownloadUrl(String url){
        mImageUrlList.add(url);
        if (!mIsDownling){
            DownloadIamgeTask task = new DownloadIamgeTask();
            task.execute();
        }
    }

    class DownloadIamgeTask extends AsyncTask<String, Integer , String>{

        @Override
        protected String doInBackground(String... params) {
            mIsDownling = true;
            while (mImageUrlList.size() > 0){
                String url = mImageUrlList.remove(0);
                Log.d(LOG_TAG, "begin download url:"+url);
                String hash = DownImageUtils.downloadImage(url);
                final String filePath = DownImageUtils.getImageFilePath(hash);
                Log.d(LOG_TAG, "filePath:"+filePath);
                final String originUrl = url;
                if (mWebView.get() != null && mActivity.get() != null){

                    mActivity.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String js = getJs(originUrl, filePath);
                            Log.d(LOG_TAG, js);
                            mWebView.get().loadUrl(js);
                        }
                    });
                }
            }
            mIsDownling = false;
            return null;
        }
    }

    public String getJs(String originUrl, String localUrl){
        return String.format("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    var imgSrc = objs[i].getAttribute(\"src\"); "
                + " if(imgSrc == \"%s\"){ "
                + "    objs[i].setAttribute(\"src\", \"%s\");}" +
                "}" +
                "})()", originUrl, localUrl);

    }

}
