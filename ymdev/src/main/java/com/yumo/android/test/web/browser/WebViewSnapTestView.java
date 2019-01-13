package com.yumo.android.test.web.browser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.media.*;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by yumodev on 5/9/16.
 * http://www.jianshu.com/p/d0ef41470586 Webview截屏三种方式
 * http://www.jianshu.com/p/862257172b71 WebView及ScrollView长截图
 * http://blog.csdn.net/lucifervsme/article/details/78048693 WebView 生成长图，截图
 * https://youzanmobile.github.io/2017/05/19/android-screenshot-and-webview/ Android截屏与WebView长图分享经验总结
 */
public class WebViewSnapTestView extends YmTestFragment {
    private final String LOG_TAG = "WebViewSnapTestView";

    private WebView mWebView = null;
    private String mPageUrl = "";

    /**
     * 测试凤凰网
     */
    public void testSnapShotWebView(){
        String url = "";
        //url = "http://news.sina.cn/gn/2016-05-09/detail-ifxryhhi8536395.d.html?vt=4&wm=9212_0001&sid=172021";
        //url = "http://sina.com";
        url = "http://nav.maxthon.cn/android/";
        createWebView(url);
    }

    /**
     * 自动截图
     */
    public void testAutoSnapShotWebView(){
        String url = "";
        url = "http://news.sina.cn/gn/2016-05-09/detail-ifxryhhi8536395.d.html?vt=4&wm=9212_0001&sid=172021";
        //url = "http://sina.com";
        createWebView1(url);
    }

    /**
     * 新建一个WebView
     * @param url
     */
    private void createWebView(String url){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(url);
        showWebView(mWebView);
    }

    /**
     * 加载完成的时候，自动截屏
     * @param url
     */
    private void createWebView1(String url){
        mWebView = new WebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                snapshotWebView(view);
            }
        });
        mWebView.loadUrl(url);
        //mWebView.buildDrawingCache();
        showWebView(mWebView);
    }

    /**
     * 显示 显示一个WebView
     */
    private void showWebView(final WebView webView){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);

        rootView.addView(createCommnadContainer(), 0);

        rootView.addView(webView, 1);
        showTestView(rootView);
    }

    private HorizontalScrollView createCommnadContainer(){
        HorizontalScrollView btnScrollView = new HorizontalScrollView(getContext());
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        btnScrollView.addView(layout);

        layout.addView(createCommanBtn("截屏", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewDisplayBmp(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("截屏1", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewDisplayBmp1(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("截屏2", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap =getWebViewDisplayBmp2(getActivity());
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("截屏3", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewDisplayBmp3(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("整屏", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewContentBitmap(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("整屏1", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewContentBitmap1(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        layout.addView(createCommanBtn("整屏2", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getWebViewContentBitmap2(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
                }
            }
        }));


        layout.addView(createCommanBtn("滚动截屏", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getScrollBitmap(mWebView);
                if (bitmap != null){
                    showImage(bitmap);
                }else{
                    Toast.makeText(getContext(), "滚动截屏", Toast.LENGTH_SHORT).show();
                }
            }
        }));
        return btnScrollView;
    }

    private Button createCommanBtn(String text, View.OnClickListener listener){
        Button button = new Button(getContext());
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setText(text);
        button.setOnClickListener(listener);
        return button;
    }

    private void showImage(Bitmap bitmap){
        String savePath = null;
        if (bitmap != null) {
            savePath = YmAdFileUtil.getFileCache(getContext(), "snapshot") + UUID.randomUUID().toString() +".jpg";
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

    private void snapshotWebView(WebView webView){
        //Bitmap bitmap = getWebViewShotshatScale(webView);
        Bitmap bitmap = getWebViewContentBitmap1(webView);
        showImage(bitmap);
    }

    /**
     * 获取整个网页快照
     * @param webView
     * @return
     */
    public static Bitmap getWebViewContentBitmap(WebView webView){
        float scale = webView.getScale();
        //webview内容的高度
        int webViewHeight = (int) (webView.getContentHeight() * scale);
        try {
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(),webViewHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            return bitmap;
        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getWebViewContentBitmap1(WebView webView){
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(),
                webView.getMeasuredHeight());

        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();

        try {
            Bitmap bmp = Bitmap.createBitmap(webView.getMeasuredWidth(),
                    webView.getMeasuredHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(bmp);
            Paint paint = new Paint();
            canvas.drawBitmap(bmp, 0, bmp.getHeight(), paint);
            webView.draw(canvas);
            return bmp;
        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }

        return  null;
    }

    public static Bitmap getWebViewContentBitmap2(WebView webView){
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        Bitmap bw = null;
        if (width > 0 && height > 0) {
            bw = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bw);
            //picture.draw(canvas);
            canvas.drawPicture(picture);
        }
        return bw;
    }



    private void currentDisplayBmp(WebView webView){
        Bitmap bitmap = getWebViewDisplayBmp(webView);
        if (bitmap != null){
            showImage(bitmap);
        }else{
            Toast.makeText(getContext(), "截图失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 截取当前屏幕
     * @param webView
     */
    private Bitmap getWebViewDisplayBmp(WebView webView){
        Picture picture = webView.capturePicture();
        try {
            Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(), webView.getContentHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPicture(picture);
            return bitmap;
        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }


        return null;
    }


    public Bitmap getWebViewDisplayBmp1(WebView webView){
        try {
            Bitmap bmp = Bitmap.createBitmap(webView.getMeasuredWidth(),
                    webView.getMeasuredHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(bmp);
            Paint paint = new Paint();
            canvas.drawBitmap(bmp, 0, bmp.getHeight(), paint);
            webView.draw(canvas);
            return bmp;
        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }

        return  null;
    }

    /**
     * 手机屏幕的快照
     * @param context
     * @return
     */
    private Bitmap getWebViewDisplayBmp2(Activity context) {
        View cv = context.getWindow().getDecorView();
        try {
            Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bmp);
            cv.draw(canvas);
            return bmp;
        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * android为了提高滚动等各方面的绘制速度，可以为每一个view建立一个缓存，
     * 使用 View.buildDrawingCache为自己的view建立相应的缓存，
     * 然后使用getDrawingCache()获取缓存，webView.destroyDrawingCache();
     * 当进行多次截图时需要清除前面的截图：setDrawingCacheEnabled(false)
     * @param webView
     * @return
     */
    public Bitmap getWebViewDisplayBmp3(WebView webView){
        webView.destroyDrawingCache();
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = webView.getDrawingCache();
        return bitmap;
    }


    /**
     *
     * @return
     */
    public Bitmap getScrollBitmap(WebView webView){
        //webView.scrollTo(webView.getScrollX(), 0);
//        webView.pageUp(true);
//        while (webView.pageDown(false)){
//        }

        webView.pageDown(false);
        return null;
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            boolean scrolled = mWebView.pageUp(false);
//            Log.i(LOG_TAG, "Volume PangUp  is "+scrolled);
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            boolean scrolled = mWebView.pageDown(false);
//            Log.i(LOG_TAG, "Volume PangDown  is "+scrolled);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
