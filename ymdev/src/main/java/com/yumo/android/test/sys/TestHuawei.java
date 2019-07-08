package com.yumo.android.test.sys;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.yumo.android.common.utils.TestAppUtil;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/7.
 * "type":"BUTLER_SETTING","systemPkg":"com.huawei.systemmanager","activity":"ProtectActivity"
 * "type":"BUTLER_SETTING","systemPkg":"com.huawei.systemmanager","activity":"MainScreenActivity"
 * "type":"BUTLER_SETTING","systemPkg":"com.huawei.systemmanager","activity":"permissionmanager"
 * "type":"ROM_SETTING","subType":"act","act":"android.settings.LOCATION_SOURCE_SETTINGS","category":""
 *
 * com.android.settings/.applications.InstalledAppDetailsTop
 * com.android.packageinstaller/.permission.ui.ManagePermissionsActivity
 */

public class TestHuawei extends YmTestFragment {

    /**
     * 自启动
     * com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity
     */
    public void testStartStartupAppControlActivity(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.huawei.systemmanager", "StartupAppControlActivity");
        startActivity(intent);
    }

    public void testStartStartupAppControlActivity1(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }


    /**
     * 去掉省电模式
     * com.huawei.systemmanager/.power.ui.HwPowerManagerActivity
     */

    public void testPwoerManagerActivity(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.huawei.systemmanager", "HwPowerManagerActivity");
        startActivity(intent);
    }

    public void testPwoerManagerActivity1(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/.power.ui.HwPowerManagerActivity");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }


    /**
     * Wlan
     */
    public void testSubSttings(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.android.settings/.SubSettings");
        intent.setComponent(componentName);
        getContext().startActivity(intent);
    }

    /**
     * 打开位置服务
     */
    public void testShowLoaction(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getActivity().startActivity(intent);
    }

    /**
     * 忽略电池优化
     */
    public void testRequestIgnoreBatteryOptimizations(){
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        //intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getActivity().startActivity(intent);
    }


    /**
     * 打开权限管理
     * com.android.packageinstaller/.permission.ui.ManagePermissionsActivity
     * com.huawei.systemmanager/com.huawei.permissionmanager.ui.PermissionSettingActivity
     */
    public void testOpenManagerPermissionActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.android.packageinstaller/.permission.ui.ManagePermissionsActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }


    public void testOpenManagerPermissionActivity1(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.android.settings", "ManagePermissionsActivity");
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivity(intent);
    }

    public void testOpenManagerPermissionActivity2(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/com.huawei.permissionmanager.ui.PermissionSettingActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testOpenManagerPermissionActivity3(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.huawei.systemmanager", "PermissionSettingActivity");
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivity(intent);
    }
}
