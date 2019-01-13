package com.yumodev.process.test;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.util.TestAppUtil;

/**
 * Created by yumo on 2018/6/8.
 * com.vivo.abe/com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity
 */

public class TestVivo extends YmTestFragment {

    public void testPowerSavingManagerActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.iqoo.powersaving/.PowerSavingManagerActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testPowerSavingManagerActivity1(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.iqoo.powersaving", "PowerSavingManagerActivity");
        getContext().startActivity(intent);
    }

    public void testExcessivePowerManagerActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.vivo.abe/com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testExcessivePowerManagerActivity2(){
        Intent intent = TestAppUtil.findApp(getContext(), "com.vivo.abe", "ExcessivePowerManagerActivity");
        getContext().startActivity(intent);
    }
    public void testExcessivePowerManagerActivity1(){
        //Vivo 后台高耗电
        Intent vivoGodIntent = new Intent();
        vivoGodIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        vivoGodIntent.setComponent(new ComponentName("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));

       // Intent intent = TestAppUtil.findApp(getContext(), "com.vivo.abe", "ExcessivePowerManagerActivity");
        getContext().startActivity(vivoGodIntent);
    }

    public void testBgStartUpManagerActivity(){
        String className = "com.vivo.permissionmanager/.activity.BgStartUpManagerActivity";
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString(className);
        intent.setComponent(componentName);
        //intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
       // TestAppUtil.openActivity(getContext(), "com.vivo.permissionmanager/.activity.BgStartUpManagerActivity");
    }

    public void testBgStartUpManagerActivity1(){
        TestAppUtil.openActivity(getContext(), "com.vivo.permissionmanager", "BgStartUpManagerActivity");
    }

    public void testPurviewTabActivity(){
        TestAppUtil.openActivity(getContext(), "com.vivo.permissionmanager/.activity.PurviewTabActivity");
    }

    public void testPurviewTabActivity1(){
        TestAppUtil.openActivity(getContext(), "com.vivo.permissionmanager", "PurviewTabActivity");
    }

    //com.vivo.permissionmanager/.activity.SoftPermissionDetailActivity

    public void testSoftPermissionDetailActivity(){
        String className = "com.vivo.permissionmanager/.activity.SoftPermissionDetailActivity";
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString(className);
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
        // TestAppUtil.openActivity(getContext(), "com.vivo.permissionmanager/.activity.BgStartUpManagerActivity");
    }
}
