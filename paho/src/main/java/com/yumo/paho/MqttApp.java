package com.yumo.paho;

import android.app.Application;

import com.yumo.common.android.YmPrefManager;

public class MqttApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YmPrefManager.getInstance().initialize(getApplicationContext());
    }
}
