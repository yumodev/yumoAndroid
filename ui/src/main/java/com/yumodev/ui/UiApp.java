package com.yumodev.ui;

import android.app.Application;

import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmHandlerThreadUtil;

/**
 * Created by yumodev on 18/1/31.
 */

public class UiApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YmContext.getInstance().setAppContext(this);
        YmHandlerThreadUtil.getInstance().start();
    }
}
