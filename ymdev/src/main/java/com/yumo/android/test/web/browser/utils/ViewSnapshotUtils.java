package com.yumo.android.test.web.browser.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.webkit.WebView;


/**
 * Created by wks on 2/23/16.
 * 截图类
 */
public class ViewSnapshotUtils {

    private static final String LOG_TAG = "YmViewSnapshotUtil";

    /**
     * 截图类
     * @param webView
     * @return
     */
    public  static Bitmap getWebViewShopshat(WebView webView){
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Picture snapShot = webView.capturePicture();
        Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        //bitmap.eraseColor(Color.WHITE);

        Canvas c = new Canvas(bitmap);
        int state = c.save();
        webView.draw(c);
        //c.restoreToCount(state);
        c.restore();
        webView.destroyDrawingCache();

        return bitmap;
    }



}
