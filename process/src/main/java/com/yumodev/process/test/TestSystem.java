package com.yumodev.process.test;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yumo on 2018/6/5.
 *
 * public static final String ACTION_ACCESSIBILITY_SETTINGS = "android.settings.ACCESSIBILITY_SETTINGS";
 public static final String ACTION_ADD_ACCOUNT = "android.settings.ADD_ACCOUNT_SETTINGS";
 public static final String ACTION_AIRPLANE_MODE_SETTINGS = "android.settings.AIRPLANE_MODE_SETTINGS";
 public static final String ACTION_APN_SETTINGS = "android.settings.APN_SETTINGS";
 public static final String ACTION_APPLICATION_DETAILS_SETTINGS = "android.settings.APPLICATION_DETAILS_SETTINGS";
 public static final String ACTION_APPLICATION_DEVELOPMENT_SETTINGS = "android.settings.APPLICATION_DEVELOPMENT_SETTINGS";
 public static final String ACTION_APPLICATION_SETTINGS = "android.settings.APPLICATION_SETTINGS";
 public static final String ACTION_BATTERY_SAVER_SETTINGS = "android.settings.BATTERY_SAVER_SETTINGS";
 public static final String ACTION_BLUETOOTH_SETTINGS = "android.settings.BLUETOOTH_SETTINGS";
 public static final String ACTION_CAPTIONING_SETTINGS = "android.settings.CAPTIONING_SETTINGS";
 public static final String ACTION_CAST_SETTINGS = "android.settings.CAST_SETTINGS";
 public static final String ACTION_DATA_ROAMING_SETTINGS = "android.settings.DATA_ROAMING_SETTINGS";
 public static final String ACTION_DATE_SETTINGS = "android.settings.DATE_SETTINGS";
 public static final String ACTION_DEVICE_INFO_SETTINGS = "android.settings.DEVICE_INFO_SETTINGS";
 public static final String ACTION_DISPLAY_SETTINGS = "android.settings.DISPLAY_SETTINGS";
 public static final String ACTION_DREAM_SETTINGS = "android.settings.DREAM_SETTINGS";
 public static final String ACTION_HARD_KEYBOARD_SETTINGS = "android.settings.HARD_KEYBOARD_SETTINGS";
 public static final String ACTION_HOME_SETTINGS = "android.settings.HOME_SETTINGS";
 public static final String ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS = "android.settings.IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS";
 public static final String ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS = "android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS";
 public static final String ACTION_INPUT_METHOD_SETTINGS = "android.settings.INPUT_METHOD_SETTINGS";
 public static final String ACTION_INPUT_METHOD_SUBTYPE_SETTINGS = "android.settings.INPUT_METHOD_SUBTYPE_SETTINGS";
 public static final String ACTION_INTERNAL_STORAGE_SETTINGS = "android.settings.INTERNAL_STORAGE_SETTINGS";
 public static final String ACTION_LOCALE_SETTINGS = "android.settings.LOCALE_SETTINGS";
 public static final String ACTION_LOCATION_SOURCE_SETTINGS = "android.settings.LOCATION_SOURCE_SETTINGS";
 public static final String ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS = "android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS";
 public static final String ACTION_MANAGE_APPLICATIONS_SETTINGS = "android.settings.MANAGE_APPLICATIONS_SETTINGS";
 public static final String ACTION_MANAGE_DEFAULT_APPS_SETTINGS = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";
 public static final String ACTION_MANAGE_OVERLAY_PERMISSION = "android.settings.action.MANAGE_OVERLAY_PERMISSION";
 public static final String ACTION_MANAGE_WRITE_SETTINGS = "android.settings.action.MANAGE_WRITE_SETTINGS";
 public static final String ACTION_MEMORY_CARD_SETTINGS = "android.settings.MEMORY_CARD_SETTINGS";
 public static final String ACTION_NETWORK_OPERATOR_SETTINGS = "android.settings.NETWORK_OPERATOR_SETTINGS";
 public static final String ACTION_NFCSHARING_SETTINGS = "android.settings.NFCSHARING_SETTINGS";
 public static final String ACTION_NFC_PAYMENT_SETTINGS = "android.settings.NFC_PAYMENT_SETTINGS";
 public static final String ACTION_NFC_SETTINGS = "android.settings.NFC_SETTINGS";
 public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
 public static final String ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS = "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS";
 public static final String ACTION_PRINT_SETTINGS = "android.settings.ACTION_PRINT_SETTINGS";
 public static final String ACTION_PRIVACY_SETTINGS = "android.settings.PRIVACY_SETTINGS";
 public static final String ACTION_QUICK_LAUNCH_SETTINGS = "android.settings.QUICK_LAUNCH_SETTINGS";
 public static final String ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
 public static final String ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
 public static final String ACTION_SECURITY_SETTINGS = "android.settings.SECURITY_SETTINGS";
 public static final String ACTION_SETTINGS = "android.settings.SETTINGS";
 public static final String ACTION_SHOW_REGULATORY_INFO = "android.settings.SHOW_REGULATORY_INFO";
 public static final String ACTION_SOUND_SETTINGS = "android.settings.SOUND_SETTINGS";
 public static final String ACTION_SYNC_SETTINGS = "android.settings.SYNC_SETTINGS";
 public static final String ACTION_USAGE_ACCESS_SETTINGS = "android.settings.USAGE_ACCESS_SETTINGS";
 public static final String ACTION_USER_DICTIONARY_SETTINGS = "android.settings.USER_DICTIONARY_SETTINGS";
 public static final String ACTION_VOICE_CONTROL_AIRPLANE_MODE = "android.settings.VOICE_CONTROL_AIRPLANE_MODE";
 public static final String ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE = "android.settings.VOICE_CONTROL_BATTERY_SAVER_MODE";
 public static final String ACTION_VOICE_CONTROL_DO_NOT_DISTURB_MODE = "android.settings.VOICE_CONTROL_DO_NOT_DISTURB_MODE";
 public static final String ACTION_VOICE_INPUT_SETTINGS = "android.settings.VOICE_INPUT_SETTINGS";
 public static final String ACTION_VPN_SETTINGS = "android.settings.VPN_SETTINGS";
 public static final String ACTION_VR_LISTENER_SETTINGS = "android.settings.VR_LISTENER_SETTINGS";
 public static final String ACTION_WEBVIEW_SETTINGS = "android.settings.WEBVIEW_SETTINGS";
 public static final String ACTION_WIFI_IP_SETTINGS = "android.settings.WIFI_IP_SETTINGS";
 public static final String ACTION_WIFI_SETTINGS = "android.settings.WIFI_SETTINGS";
 public static final String ACTION_WIRELESS_SETTINGS = "android.settings.WIRELESS_SETTINGS";
 public static final String AUTHORITY = "settings";
 public static final String EXTRA_ACCOUNT_TYPES = "account_types";
 public static final String EXTRA_AIRPLANE_MODE_ENABLED = "airplane_mode_enabled";
 public static final String EXTRA_AUTHORITIES = "authorities";
 public static final String EXTRA_BATTERY_SAVER_MODE_ENABLED = "android.settings.extra.battery_saver_mode_enabled";
 public static final String EXTRA_DO_NOT_DISTURB_MODE_ENABLED = "android.settings.extra.do_not_disturb_mode_enabled";
 public static final String EXTRA_DO_NOT_DISTURB_MODE_MINUTES = "android.settings.extra.do_not_disturb_mode_minutes";
 public static final String EXTRA_INPUT_METHOD_ID = "input_method_id";
 public static final String INTENT_CATEGORY_USAGE_ACCESS_CONFIG = "android.intent.category.USAGE_ACCESS_CONFIG";
 public static final String METADATA_USAGE_ACCESS_REASON = "android.settings.metadata.USAGE_ACCESS_REASON";
 */

public class TestSystem extends YmTestFragment {


    /**
     * 打开位置信息设置页面
     */
    public void testLocationSourceSettings(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getActivity().startActivity(intent);
    }

    /**
     * 读取应用通知
     */
    public void testNotifationListenerSettings(){
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        getActivity().startActivity(intent);
    }

    /**
     * 勿扰权限
     */
    public void testNotifationPolicyAccessSettings(){
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        getActivity().startActivity(intent);
    }

    /**
     * 是否打开应用通知
     */
    public void testHasEnableNotification(){
        NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if (isOpened){
            showToastMessage("允许弹出通知");
        }else{
            showToastMessage("不允许弹出通知");
        }
    }

    /**
     * 打开应用通知
     */
    public void testOpenAppNotificationSettings(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
            //intent.putExtra(Settings.EXTRA_CHANNEL_ID, getContext().getApplicationInfo().uid);
            startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
        }
    }

    public void testAppNotifationSettings(){
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        getActivity().startActivity(intent);
    }

    public void testChannelNotifationSettings(){
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        getActivity().startActivity(intent);
    }

    public void testApplicationSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());

        getActivity().startActivity(intent);
    }

    public void testManagerAllApplicationSettings(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());

        getActivity().startActivity(intent);
    }

    public void testManagerApplicationSettings(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());

        getActivity().startActivity(intent);
    }
    public void testApplicationDetailSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }

    public void testIgnoreBackgroundDataRestrictionsSettings(){
        Intent intent = new Intent(Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }


    public void testIgnoreBatteryOptimizationSettings(){
        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
       // intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }

    /**
     * 请求忽略电池优化
     */
    public void testRequestIgnoreBatteryOptimizations(){
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }

    /**
     * 在其他应用上层显示
     */
    public void testManagerOverlayPermission(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }

    /**
     * ACTION_SECURITY_SETTINGS
     */
    public void testSecuritySettings(){
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
       // intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }


    public void testPrintAllApp(){
        PackageManager packageManager = getContext().getPackageManager();
        List<ApplicationInfo> appList = getContext().getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Iterator iterator = appList.iterator();

        while(iterator.hasNext()) {
            ApplicationInfo applicationInfo = (ApplicationInfo)iterator.next();
            Log.e("APP", applicationInfo.packageName + " name : " + applicationInfo.loadLabel(packageManager).toString());
        }
    }
}
