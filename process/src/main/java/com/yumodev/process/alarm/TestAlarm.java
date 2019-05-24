package com.yumodev.process.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import androidx.annotation.Nullable;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.Define;

/**
 * Created by yumo on 2018/4/4.
 */

public class TestAlarm extends YmTestFragment{

    public void testA(){
        showToastMessage("Hello");
    }


    public void testAlarm(){
        AlarmManager aManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent();
        in.setClass(getContext(), AlarmService.class);
        PendingIntent pi = PendingIntent.getService(getContext(), 0, in, 0);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            aManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5*1000,  pi);
        }else{
            aManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5 * 1000, pi);
        }
    }

    public static class AlarmService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            //vibrator.vibrate(1000);
            Log.i(Define.mDebugLog, "onStartComand:"+vibrator);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                AlarmManager aManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent in = new Intent();
                in.setClass(this, AlarmService.class);
                PendingIntent pi = PendingIntent.getService(this, 0, in, 0);
                aManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+5*1000, 5 * 1000, pi);
            }

            return START_REDELIVER_INTENT;
        }
    }
}
