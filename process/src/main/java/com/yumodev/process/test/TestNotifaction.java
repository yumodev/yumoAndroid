package com.yumodev.process.test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.R;
import com.yumodev.process.util.DialogFacory;
import com.yumodev.process.util.MediaClientService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yumo on 2018/4/24.
 * 测试包括，播放无声音乐和前台服务
 */

public class TestNotifaction extends YmTestFragment {
    private static final String LOG_TAG = "TestNotifaction";
    private Notification mNotificaton = null;
    private Timer mTimer = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificaton = createForegroudService26(getContext(), getActivity());
        mTimer = new Timer();
    }

    public void testA(){

    }

    public void testShowForedService(){
        Intent intent = new Intent(this.getContext(), TestForegroundService.class);
        //intent.putExtra("notification", createForegroudService(getContext(), getActivity()));
        intent.putExtra("notification", mNotificaton);

        intent.putExtra("id", 1002);
        intent.putExtra("command", "start");
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
//            getContext().startService(intent);
//        }else{
//            getContext().startForegroundService(intent);
//        }

        getContext().startService(intent);
    }

    public void testStopService(){
        Intent intent = new Intent(this.getContext(), TestForegroundService.class);
        intent.putExtra("command", "stop");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            getContext().startService(intent);
        }else{
            getContext().startForegroundService(intent);
        }
    }


    public Notification createForegroudService(Context context, Activity activity){
        Notification.Builder builder = new Notification.Builder (context);
        //获取一个Notification构造器

        Intent nfIntent = new Intent(context, activity.getClass());
        builder.setContentIntent(PendingIntent.getActivity(activity, 0, nfIntent, 0)) // 设置PendingIntent
                .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("后台定位通知") // 设置上下文内容
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            notification = builder.build();
        }else{
            notification = builder.getNotification();
        }

        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        return notification;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification createForegroudService26(Context context, Activity activity){
        Notification.Builder builder = new Notification.Builder (context);
        //获取一个Notification构造器

        Intent nfIntent = new Intent(context, activity.getClass());
        builder.setContentIntent(PendingIntent.getActivity(activity, 0, nfIntent, 0)) // 设置PendingIntent
                .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("后台定位通知") // 设置上下文内容
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        Notification notification;



        String channelID = "1";

        String channelName = "channel_name";

        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.createNotificationChannel(channel);


        //创建通知时指定channelID

        builder.setChannelId(channelID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            notification = builder.build();
        }else{
            notification = builder.getNotification();
        }

        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

        return notification;
    }

    public void testStartPlay(){
        MediaClientService.getInstance().startDrvingPlayer();
    }

    public void testDimingPlay(){
        MediaClientService.getInstance().timingPlayer(1000 * 20);
    }

    public void testStopPlay(){
        MediaClientService.getInstance().stopPlayer();
    }

    public void testCancelTiming(){
        MediaClientService.getInstance().cancelTimingPlayer();
    }

    public void testEnableNotificationService(){
        String apps = Settings.Secure.getString(getContext().getContentResolver(), "enabled_notification_listeners");
        showToastMessage(apps);
        //return !TextUtils.isEmpty(apps) && apps.contains(ServiceConstant.NOTIFICATION_SERVICE_CLASS_NAME);
    }

    public void testOpenNotificationSetting(){
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public void testStartTimer(){
        MediaClientService.getInstance().startDrvingPlayer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                showToastMessage("Task cancel");
                MediaClientService.getInstance().stopPlayer();
                mTimer.cancel();
                mTimer = null;
            }
        };

        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(task, 1000 * 20);
    }

    public void testRepeatStartTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                mTimer.cancel();

                showToastMessage("Task cancel");
            }
        };

        mTimer.schedule(task, 1000 * 5);
    }


    public void testFlurry(){
        Intent intent = new Intent("com.yumodev.process.flurry");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(32);
        getContext().sendBroadcast(intent);
    }

    public void testShowSystemDialog(){
        DialogFacory.showDialog(getContext(), "系统对话框");
    }

    public static class TestForegroundService extends Service{

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //return super.onStartCommand(intent, flags, startId);
            Log.i(LOG_TAG, "onStartCommand");
            String command = intent.getStringExtra("command");
            if (command.equals("start")){
                Notification notification = intent.getParcelableExtra("notification");
                int id = intent.getIntExtra("id", 1);
                startForeground(id, notification);
            }else{
                stopForeground(true);
            }

            return START_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
