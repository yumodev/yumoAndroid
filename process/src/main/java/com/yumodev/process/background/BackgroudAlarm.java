package com.yumodev.process.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;

import com.elvishew.xlog.XLog;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmDateUtil;
import com.yumodev.process.Define;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by yumo on 2018/4/19.
 */

public class BackgroudAlarm {

    public static void startReatAlarm(Context context){
        Intent intent = new Intent(context, InnerGuardReceiver.class);
        intent.setAction("com.yumodev.process.localreceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1001, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, 5000, pendingIntent);
    }

    public static void startAlarm(Context context){
        Intent intent = new Intent(context, ProcessWakefulBroadcastReceiver.class);
        intent.setAction("com.yumodev.process.localreceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1002, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, 5000, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    pendingIntent);
        } else {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    5000, pendingIntent);
        }


    }


    public static class InnerGuardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String time = YmDateUtil.getStrTime();
            //XLog.i(time + " alarm LocalService ");
            Log.i(Define.mDebugLog, time + " alarm LocalService ");
//            Vibrator vibrator = (Vibrator)context.getSystemService(VIBRATOR_SERVICE);
//            vibrator.vibrate(1000);
            //NotificationUtil.showNotification(context, new Intent(), 106, "alarm休眠测试", format + ":alarm执行");
        }
    }
}
