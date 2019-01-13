package com.yumodev.process.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.BatteryManager;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.yumo.common.android.YmAppUtil;
import com.yumo.common.android.YmIntentUtil;
import com.yumo.common.android.YmToastUtil;
import com.yumo.common.log.Log;
import com.yumo.common.thread.YmProcessUtil;
import com.yumodev.process.Define;
import com.yumodev.process.background.BackgroudAlarm;

/**
 * Created by yumodev on 18/3/22.
 */

public class ProcessReceiver extends BroadcastReceiver {
    private final String LOG_TAG = Define.mDebugLog +" ProcessReceiver " + YmProcessUtil.getCurrentProcessName();
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append(YmIntentUtil.toString(intent));
        Log.i(LOG_TAG, sb.toString(), true);

        switch (intent.getAction()){
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
            case ConnectivityManager.CONNECTIVITY_ACTION:{
                break;
            }
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:{
                YmToastUtil.showMessage("是否为飞行模式："+ YmAppUtil.isAirplane(context));
                break;
            }
            case "com.icongtai.zebra.collision":{
                if (intent != null && intent.hasExtra("COLLISION_ID")){
                    String collisionId = intent.getStringExtra("COLLISION_ID");
                }

            }
            case Intent.ACTION_BATTERY_CHANGED:{
                battery(intent);
            }
        }

        YmToastUtil.showMessage(sb.toString());

        BackgroudAlarm.startAlarm(context);
    }

    public void register(Context app){
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.icongtai.zebra.collision");

//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_USER_PRESENT);//解锁
//        filter.addAction(Intent.ACTION_USER_UNLOCKED);
//        //关闭系统对话框
//        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//
//
//
//
//        //网络变化
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//
//        //日期时间发生变化
//        filter.addAction(Intent.ACTION_TIME_TICK);
//        filter.addAction(Intent.ACTION_TIME_CHANGED);
//        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
//        filter.addAction(Intent.ACTION_DATE_CHANGED);
//
//        filter.addAction(Intent.ACTION_PACKAGE_ADDED);//system
//        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);//system
//        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
//        filter.addAction(Intent.ACTION_PACKAGE_RESTARTED);//system
//        filter.addAction(Intent.ACTION_MANAGE_PACKAGE_STORAGE);
//        filter.addAction(Intent.ACTION_MY_PACKAGE_REPLACED);//system
//        filter.addAction(Intent.ACTION_PACKAGES_SUSPENDED);//system
//        filter.addAction(Intent.ACTION_PACKAGES_UNSUSPENDED);//system
//        filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);//system
//        filter.addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH);//system
//        filter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);//system
//        filter.addAction(Intent.ACTION_PACKAGE_NEEDS_VERIFICATION);//system
//        filter.addAction(Intent.ACTION_PACKAGE_VERIFIED);//system
//
//
//
//
//
//        //充电和断电
//        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//        filter.addAction(Intent.ACTION_POWER_CONNECTED);
//        filter.addAction(Intent.ACTION_POWER_USAGE_SUMMARY);
//
        //电池变化 只能系统接收
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
//
//
//        //开机启动 Manifest.permission.RECEIVE_BOOT_COMPLETED
//        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
//        filter.addAction(Intent.ACTION_REBOOT);
//        filter.addAction(Intent.ACTION_SHUTDOWN);
//
//        //飞行模式
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//
//        //外置存储变化
//        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
//        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
//        filter.addAction(Intent.ACTION_MEDIA_SHARED);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
//        filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTABLE);
//
//        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
//        filter.addAction(Intent.ACTION_MEDIA_EJECT);
//        filter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
//        filter.addAction(Intent.ACTION_MEDIA_CHECKING);
//        filter.addAction(Intent.ACTION_MEDIA_NOFS);
//        filter.addAction(Intent.ACTION_DEVICE_STORAGE_OK);
//        filter.addAction(Intent.ACTION_DEVICE_STORAGE_LOW);
//        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);//system
//        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);//system
//
//
//
//        filter.addAction(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED);
//
//        filter.addAction(Intent.ACTION_CAMERA_BUTTON);
//
//        //只能系统接收
//        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
//        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
//        filter.addAction(Intent.ACTION_LOCKED_BOOT_COMPLETED);
//
//
//        filter.addAction(Intent.ACTION_DOCK_EVENT);
//
//        filter.addAction(Intent.ACTION_DREAMING_STARTED);
//        filter.addAction(Intent.ACTION_DREAMING_STOPPED);
//
//        filter.addAction(Intent.ACTION_GET_RESTRICTION_ENTRIES);
//
//        filter.addAction(Intent.ACTION_GTALK_SERVICE_CONNECTED);
//        filter.addAction(Intent.ACTION_GTALK_SERVICE_DISCONNECTED);
//
//        filter.addAction(Intent.ACTION_HEADSET_PLUG);
//
//        filter.addAction(Intent.ACTION_INPUT_METHOD_CHANGED);
//
//        filter.addAction(Intent.ACTION_MANAGED_PROFILE_ADDED);
//        filter.addAction(Intent.ACTION_MANAGED_PROFILE_REMOVED);
//        filter.addAction(Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE);
//        filter.addAction(Intent.ACTION_MANAGED_PROFILE_UNLOCKED);
//
//        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);//system
//
//        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
//
//        filter.addAction(Intent.ACTION_UID_REMOVED);
//
//
//        /**
//         * 高德地图
//         */
//        filter.addAction("AUTONAVI_STANDARD_BROADCAST_SEND");
//        filter.addAction("AUTONAVI_STANDARD_BROADCAST_RECV");

        app.registerReceiver(this, filter);
    }


//      public static final String EXTRA_STATUS = "status";
//
//   public static final String EXTRA_HEALTH = "health";
//
//   public static final String EXTRA_PRESENT = "present";//电池是否存在
//
//   public static final String EXTRA_LEVEL = "level";//电量等级 从 0 到  #EXTRA_SCALE
//
//   public static final String EXTRA_SCALE = "scale";//最大电量等级
//
//   public static final String EXTRA_ICON_SMALL = "icon-small";//？？
//
//    //是否插入电源，0表示电池供电，其它值表示不同类型的电源
//
//           public static final String EXTRA_PLUGGED = "plugged";
//
//   public static final String EXTRA_VOLTAGE = "voltage";
//
//   public static final String EXTRA_TEMPERATURE = "temperature";
//
//   public static final String EXTRA_TECHNOLOGY = "technology";//电池技术,比如是锂电池还是其他类型
//
//    //如果不支持的充电器插入该域会设置成非零值
//
//           public static final String EXTRA_INVALID_CHARGER ="invalid_charger";

    private void battery(Intent intent){

        int voltage = intent.getIntExtra("voltage", 0) / 1000;

        int temperature = intent.getIntExtra("temperature",0) / 10;

        int status = intent.getIntExtra("status",BatteryManager.BATTERY_STATUS_UNKNOWN);

        int health = intent.getIntExtra("health",
                BatteryManager.BATTERY_HEALTH_UNKNOWN);

        StringBuilder sb = new StringBuilder();
        sb.append("voltage:"+voltage);
        sb.append("temperature:"+temperature);
        sb.append("status:"+status);
        sb.append("health:"+health);
        Log.i(LOG_TAG, sb.toString());
    }
}
