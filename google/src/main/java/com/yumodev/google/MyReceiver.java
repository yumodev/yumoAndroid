package com.yumodev.google;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.yumo.common.android.YmLocationUtil;
import com.yumo.common.log.Log;
import com.yumodev.google.util.GeoFenceManager;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by yumo on 2018/5/9.
 */

public class MyReceiver extends BroadcastReceiver {
    public static final String GEO_FENCE = "ACTION_GEO_FENCE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals(GEO_FENCE)){
            createNotification(context, intent);
        }
    }

    private void createNotification(Context context, Intent intent) {
        //Log.i(Define.TAG_LOCATION, YmIntentUtil.toString(intent), false);

        Location location = intent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location");
        String message = YmLocationUtil.formatLocation(location);


        int transition = intent.getIntExtra("com.google.android.location.intent.extra.transition", 0);
        String title = "";
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
            title = "进入地址围栏";
        }else if (transition == Geofence.GEOFENCE_TRANSITION_EXIT){
            title = "离开地址围栏";
            GeoFenceManager.getInstance(null, null).addGeoFence(location);
        }


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setContentIntent(null)
                .setTicker("测试通知来啦")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);

        mNotificationManager.notify(10, mBuilder.build());

        Log.i(Define.TAG_LOCATION, title + " "+message, true);
    }
}
