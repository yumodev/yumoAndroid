package com.yumo.android.test.api.showapi;

import android.util.Log;

import com.yumo.common.net.YmOkHttpUtil;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by trunx on 16/11/15.
 */

public class ShowApiUtils {
    private static final String LOG_TAG = "ShowApiUtils";
    private static final String mSecret = "7f9ac31eb9ee420dbb6a73e360df08e4";
    private static final String mAppid = "27235";

    public static String getPhoneLocation(String num){
        String url = String.format(Locale.US, "http://route.showapi.com/6-1?showapi_appid=%s&num=%s&showapi_sign=%s", mAppid, num, mSecret);
        Log.i(LOG_TAG, "getPhoneLocation:"+url);
        String result = null;
        try {
            result = YmOkHttpUtil.getBodyString(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getHistoryToday(String date){
        String url = String.format(Locale.US, "http://route.showapi.com/119-42?showapi_appid=%s&date=%s&showapi_sign=%s", mAppid, date, mSecret);
        Log.i(LOG_TAG, "getHistory:"+url);
        String result = null;
        try {
            result = YmOkHttpUtil.getBodyString(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
