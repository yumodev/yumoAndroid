package com.yumodev.process.local;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.yumo.common.log.Log;
import com.yumodev.process.Define;

/**
 * Created by yumodev on 18/3/22.
 * 创建前台服务
 */

public class ForegroundService extends Service {

    public static final int ID = 1000;
    public final String LOG_TAG = Define.mDebugLog+" ForegroundService ";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.mipmap.ic_launcher);
//            builder.setContentTitle("YmdevProcess");
//            builder.setContentText("text");
            startForeground(ID, builder.build());

            startService(new Intent(this, RemoveForegroundService.class));

        }else {
            startForeground(ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");

        //移除服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(ID);
        }

        //重启自己
        Intent intent = new Intent(getApplicationContext(), ForegroundService.class);
        startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
