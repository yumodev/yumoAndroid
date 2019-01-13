package com.yumodev.process.background;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.elvishew.xlog.XLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.yumo.common.android.YmAppUtil;
import com.yumo.common.log.Log;
import com.yumodev.process.Define;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by yumo on 2018/4/10.
 */

public class ProcessWakefulBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Define.mDebugLog, "wakefulBroadcastReceiver: on receive "+ LocalService.class.getName());

        Vibrator vibrator = (Vibrator)context.getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(1000);

        Intent service = new Intent(context, WakefulService.class);
        try {
            startWakefulService(context, service);
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }

        if (!YmAppUtil.isServiceRunning(context, LocalService.class.getName())){
            // 获取电源锁
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "YmProcess");
            wakeLock.acquire();
            // END_INCLUDE(alarm_onreceive)

            // MonitorThreadPool.checkDrivingMonitorHealthy();
        }else{
            Log.i(Define.mDebugLog, "wakefulBroadcastReceiver service is Runnning:"+LocalService.class.getName());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BackgroudAlarm.startAlarm(context);
        }
    }
}
