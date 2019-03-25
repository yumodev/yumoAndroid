package com.yumodev.google.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yumo on 2018/5/11.
 *
 * 位置服务和地理围栏
 */
public class LocationService extends Service {

    private GeoFenceController mGeoFenceController;

    @Override
    public void onCreate() {
        super.onCreate();
        mGeoFenceController = new GeoFenceController();
        mGeoFenceController.init(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
