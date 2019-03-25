package com.yumo.android.common.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yumo.common.io.ConvertObjectToString;
import com.yumo.common.log.Log;

public abstract class BaseService extends Service {

    protected abstract String getTag();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(getTag(), "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(getTag(), "onStartCommand:"+ ConvertObjectToString.toString(intent)+" flags:"+flags+" startId"+startId);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(getTag(), "onBind:"+ConvertObjectToString.toString(intent));
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(getTag(), "onUnbind:"+ConvertObjectToString.toString(intent));
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(getTag(), "onRebind:"+ConvertObjectToString.toString(intent));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getTag(), "onDestroy:");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(getTag(), "onTrimMemory");
    }


}
