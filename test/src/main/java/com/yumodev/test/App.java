package com.yumodev.test;

import android.app.Application;

import com.yumo.common.android.YmContext;
import com.yumo.common.log.YmCrashHelper;

/**
 * Created by yumo on 2018/7/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YmContext.setAppContext(this);
        YmCrashHelper.getInstance().init(getApplicationContext());
    }
}
