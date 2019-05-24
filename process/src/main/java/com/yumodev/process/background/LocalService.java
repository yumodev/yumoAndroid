package com.yumodev.process.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmDateUtil;
import com.yumodev.process.Define;
import com.yumodev.process.util.LocationUtil;
import com.yumodev.process.util.MediaClientService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yumodev on 18/3/21.""；
 * 和App运行在同一个进程中。
 */
public class LocalService extends Service {
    public static final int ID = 1001;
    private final String LOG_TAG = getClass().getSimpleName()+Define.mDebugLog;
    private int num = 0;
    private MediaClientService mMediaClientService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "onCreate");
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("id","name", NotificationManager.IMPORTANCE_LOW);
//
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            manager.createNotificationChannel(channel);
//            Notification notification = new Notification.Builder(this,"id").build();
//
//            startForeground(ID, notification);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
//            Notification.Builder builder = new Notification.Builder(this);
////            builder.setSmallIcon(R.mipmap.ic_launcher);
////            builder.setContentTitle("YmdevProcess");
////            builder.setContentText("text");
//            startForeground(ID, builder.build());
//        }else {
//            startForeground(ID, new Notification());
//        }

        //mMediaClientService = new MediaClientService(getApplicationContext());
        MediaClientService.getInstance().init(getApplicationContext());
       // MediaClientService.getInstance().startDrvingPlayer();

        registerReceiver(new YmBootReceiver(), new IntentFilter(Intent.ACTION_BOOT_COMPLETED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLog.i(Define.mDebugLog, "LocalService: onStartCommand");
        //startSyncTimer();
        startSync();
        BackgroudAlarm.startReatAlarm(getApplicationContext());
        BackgroudAlarm.startAlarm(getApplicationContext());
        //startLoaction();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startSync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                Looper.prepare();
                //startLoaction();
                while (true){
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "ZebraLocation");
                    wakeLock.acquire();
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    num++;
//                    Vibrator vibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
//                    vibrator.vibrate(100);
                    Location location = LocationUtil.getLocation(getApplicationContext());
                    startLoaction();
                    String locationStr = "no get GPS";
                    if (location != null) locationStr = location.getAltitude() + " "+ location.getLatitude()+ location.toString();
                    //XLog.i(num+"  "+YmDateUtil.getStrTime()+" LocalService gps: "+locationStr);
                    wakeLock.release();

//                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getApplicationContext().startActivity(intent1);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaClientService.getInstance().stopPlayer();
    }

    private void startSyncTimer(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Vibrator vibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                XLog.i(num+"  "+YmDateUtil.getStrTime()+" LocalService");
                num++;
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 5  * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    private void startLoaction(){
        LocationUtil.register(getApplicationContext(), 5000, 0, new LocationUtil.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                Log.i(LOG_TAG, "getLastKnownLocation"+location.toString());
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged"+location.toString());
                XLog.i(LOG_TAG+"onLocationChanged"+location.toString());
                Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStatusChanged "+ provider + " status");

            }
        });
    }
}
