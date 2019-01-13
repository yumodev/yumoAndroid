package com.yumodev.process.local;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.yumo.common.log.Log;
import com.yumodev.process.Define;
import com.yumodev.process.R;

/**
 * Created by yumodev on 18/3/22.
 * 移除前台服务
 * 在大于版本18以上的机器上使用。
 */

public class RemoveForegroundService extends Service {
    public final String LOG_TAG = Define.mDebugLog+" RemoveForegroundService ";
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommnad");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("YmdevProcess");
            builder.setContentText("text");
            startForeground(ForegroundService.ID, builder.build());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    stopForeground(true);
                    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(ForegroundService.ID);
                    stopSelf();
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
