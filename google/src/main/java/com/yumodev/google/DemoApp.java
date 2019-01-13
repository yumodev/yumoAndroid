package com.yumodev.google;

import android.app.Application;

import com.yumo.common.android.YmContext;
import com.yumo.common.log.YmCrashHelper;

/**
 * Created by yumo on 2018/5/11.
 */

public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YmContext.setAppContext(this.getApplicationContext());
        YmCrashHelper.getInstance().init(getApplicationContext());
    }
}
