package com.yumo.android.test.api;

import android.util.Log;

import com.yumo.android.test.api.showapi.ShowApiUtils;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 16/11/15.
 */

public class TestShowApiFragment extends YmTestFragment {
    private final String LOG_TAG = "TestShowApiFragment";
    private final String mSecret = "7f9ac31eb9ee420dbb6a73e360df08e4";
    private final String mAppid = "27235";

    public void testGetPhoneLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ShowApiUtils.getPhoneLocation("18001390124");
                Log.d(LOG_TAG, result);
                showToastMessageOnUiThread(result);
            }
        }).start();
    }

    public void testGetHistoryToday(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ShowApiUtils.getHistoryToday("1115");
                Log.d(LOG_TAG, result);
                showToastMessageOnUiThread(result);
            }
        }).start();
    }
}
