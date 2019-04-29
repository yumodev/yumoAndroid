package com.yumodev.gaode;

import android.app.Application;

import com.yumo.common.android.YmContext;

/**
 * Created by yumo on 2018/6/14.
 */

public class GaodeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YmContext.setAppContext(this);
    }
}
