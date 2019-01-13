package com.yumo.common.media;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.webkit.WebView;
/**
 * Created by yumodev on 2/23/16.
 * 截图类
 */
public class YmViewSnapshotUtil {

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

    public static Bitmap getWebViewShotshatByPicture(WebView webView){
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
}
