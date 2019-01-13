package com.yumodev.process.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.yumodev.process.Define;

/**
 * Created by yumodev on 18/3/21.
 */

public class RemoteService extends Service {
    private final String LOG_TAG = "RemoteService"+ Define.mDebugLog;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(LOG_TAG, "onStartCommand");
        testBugly();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    public void testBugly(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //CrashReport.testANRCrash();
                //CrashReport.testJavaCrash();

            }
        }).start();
    }
}
