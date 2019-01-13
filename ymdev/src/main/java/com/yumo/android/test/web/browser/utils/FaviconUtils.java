package com.yumo.android.test.web.browser.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebIconDatabase;

/**
 * Created by wks on 5/20/16.
 */
public class FaviconUtils{
    private static final String LOG_TAG = "FaviconUtils";

    private static WebIconDatabase.IconListener mIconReceiver = new WebIconDatabase.IconListener(){

        @Override
        public void onReceivedIcon(String url, Bitmap icon) {
            Log.d(LOG_TAG, "onReceivedIcon: url"+url + "  icon:"+(icon != null));
        }
    };

    public static void getFaviconByUrl(String url){
        final WebIconDatabase iconDb = WebIconDatabase.getInstance();
        iconDb.requestIconForPageUrl(url, mIconReceiver);
    }




}
