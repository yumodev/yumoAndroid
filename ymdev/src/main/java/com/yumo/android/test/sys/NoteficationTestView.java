package com.yumo.android.test.sys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestActivity;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 4/2/16.
 * 测试通知栏消息功能。
 * * [Android实现3种Notification(状态栏通知)](http://www.jianshu.com/p/a84ddaf530ec)
 */
public class NoteficationTestView extends YmTestFragment {

    private int mNotificationId = 1;

    public void testSendNotification() {
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        notification.tickerText = "test notifaciton";
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        Intent intent = new Intent();
        intent.setClass(getActivity(), YmTestActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        notification.contentIntent = pendingIntent;
        getNotificationManager().notify(mNotificationId, notification);
    }

    /**
     * 发送通知
     */
    public void testSendNotification1(){
        Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        builder.setAutoCancel(true);
        //builder.setContentTitle(getActivity().getString(R.string.app_name));
        builder.setContentText("测试信息");
        getNotificationManager().notify(mNotificationId, builder.getNotification());
    }


    /**
     * 发送带视图的广播
     */
    public void testSendNotificationView(){
        Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        builder.setAutoCancel(true);
        //builder.setContentTitle(getActivity().getString(R.string.app_name));
        builder.setContentText("测试信息");
        getNotificationManager().notify(mNotificationId, builder.getNotification());
    }

    private void testCancelNotification() {
        getNotificationManager().cancel(mNotificationId);
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }


    /**
     * 发送通知
     */
    public void testSendNotificationBroadCast(){
        Intent intent = new Intent("com.yumodev.ymdev_test_receiver");
        intent.putExtra("message","Message Test" );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        builder.setAutoCancel(true);
        //builder.setContentTitle(getActivity().getString(R.string.app_name));
        builder.setContentText("测试信息");
        getNotificationManager().notify(mNotificationId, builder.getNotification());
    }

}
