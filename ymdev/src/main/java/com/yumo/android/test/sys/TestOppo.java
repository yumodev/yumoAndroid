package com.yumo.android.test.sys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yumo.android.common.utils.TestAppUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/5.
 * {"type":"BUTLER_SETTING","systemPkg":"com.coloros.oppoguardelf","activity":"PowerConsumptionActivity"}
 * {"type":"BUTLER_SETTING","systemPkg":"com.color.safecenter","activity":"PermissionTopActivity"}
 * {"type":"BUTLER_SETTING","systemPkg":"com.color.safecenter","activity":"PermissionManagerActivity"}
 * {"type":"ROM_SETTING","subType":"act","act":"android.settings.LOCATION_SOURCE_SETTINGS","category":""}
 * com.coloros.oppoguardelf name : 电池
 * com.coloros.notificationmanager name : 通知管理
 * com.coloros.securitypermission name : 手机管家权限管理
 */

public class TestOppo extends YmTestFragment{

    private final String TAG = "TestOppo";
    public void testBackground(){
        //{"type":"BUTLER_SETTING","systemPkg":"com.coloros.oppoguardelf","activity":"PowerConsumptionActivity"}
       // selfStartManagerSettingIntent(getContext());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName("com.coloros.powermanager", "com.coloros.powermanager.newrequest.SecurityScanActivity");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.MAIN");
        getContext().startActivity(intent);
    }

    public void testPermissionTopActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }


    public void testPermissionTopActivity1(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.safecenter", "PermissionTopActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public void testPermissionTabActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.securitypermission/.permission.singlepage.PermissionTabActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    public void testPermissionTabActivity1(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.securitypermission", "PermissionTabActivity");
        getContext().startActivity(intent);
    }

    public void testSelfStartup(){

    }


    public void testSecurityScanActivity(){
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity");
//        intent.setComponent(componentName);

        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.phonemanager", "com.coloros.phonemanager.newrequest.SecurityScanActivity");
        getContext().startActivity(intent);
    }

    public void testSelfStartup1(){
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity");
//        intent.setComponent(componentName);

        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.phonemanager", "StartupAppListActivity");
        getContext().startActivity(intent);
    }

    public void testPowerConsumptionActivity(){
        //{"type":"BUTLER_SETTING","systemPkg":"com.coloros.oppoguardelf","activity":"PowerConsumptionActivity"}
        // selfStartManagerSettingIntent(getContext());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerConsumptionActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    public void testPowerConsumptionActivity1(){
        //{"type":"BUTLER_SETTING","systemPkg":"com.coloros.oppoguardelf","activity":"PowerConsumptionActivity"}
        // selfStartManagerSettingIntent(getContext());
        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.oppoguardelf", "PowerConsumptionActivity");
        getContext().startActivity(intent);
    }

    /**
     * 打开通知
     */
    public void testNotification(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.notificationmanager/.NotificationCenterActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    public void testOppo1(){
        try {
            Intent intent = new Intent();
            intent.setClassName("com.coloros.safecenter",
                    "com.coloros.safecenter.permission.startup.StartupAppListActivity");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent();
                intent.setClassName("com.oppo.safe",
                        "com.oppo.safe.permission.startup.StartupAppListActivity");
                startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.coloros.safecenter",
                            "com.coloros.safecenter.startupapp.StartupAppListActivity");
                    startActivity(intent);
                } catch (Exception exx) {
                    exx.printStackTrace();
                }
            }
        }
    }

    public void testOpenSettings(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName("com.android.settings", "com.oppo.settings.SettingsActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    public void testLocationPermissionDetailActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.securitypermission/.permission.PermissionSinglePermissionDetailActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    public void testPowerAppsBgSetting(){
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerAppsBgSetting
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerAppsBgSetting");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testPowerAppsBgSetting1(){
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerAppsBgSetting
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity
        Intent intent = new Intent("com.coloros.powermanager.fuelgaue.PowerAppsBgSetting");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testPowerUsageModelActivity(){
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerAppsBgSetting
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testPowerUsageModelActivity1(){
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerAppsBgSetting
        //com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity
        Intent intent = TestAppUtil.findApp(getContext(), "com.coloros.oppoguardelf", "PowerUsageModelActivity");
        getContext().startActivity(intent);
    }

    private void selfStartManagerSettingIntent(Context context) {
        String mtype = android.os.Build.MODEL; // 手机型号
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = null;
        if (mtype.startsWith("Redmi")||mtype.startsWith("MI")) {
            componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
        } else if (mtype.startsWith("HUAWEI")) {
            componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } else if (mtype.startsWith("vivo")) {
            Log.e(TAG, "selfStartManagerSettingIntent: vivo");
            componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity");
        } else if (mtype.startsWith("ZTE")) {
//            /.autorun.AppAutoRunManager
            componentName = new ComponentName("com.zte.heartyservice", "com.zte.heartyservice.autorun.AppAutoRunManager");
        } else if (mtype.startsWith("F")) {
            Log.e(TAG, "selfStartManagerSettingIntent: F");
            componentName = new ComponentName("com.gionee.softmanager", "com.gionee.softmanager.oneclean.AutoStartMrgActivity");
        } else if (mtype.startsWith("oppo")) {
            componentName = new ComponentName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
        }
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
//            intent = new Intent(Settings.ACTION_SETTINGS);
//            context.startActivity(intent);
        }

    }
}
