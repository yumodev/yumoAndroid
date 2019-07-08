package com.yumo.android.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.yumo.common.android.YmAppUtil;

import java.util.Iterator;
import java.util.List;


/**
 * Created by yumo on 2018/4/19.
 */

public class TestAppUtil {

    /**
     * 打开服务
     * @param context
     * @param className
     * @return
     */
    public static boolean openService(Context context, String className){
        boolean result = true;

        if (YmAppUtil.isServiceRunning(context, className)){
            //XLog.i(YmDateUtil.getStrTime()+ " "+ className +" is Running");
            return true;
        }

        try {
            Intent intent = new Intent().setClassName(context.getPackageName(), className);
            context.startService(intent);
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }



    @TargetApi(Build.VERSION_CODES.M)
    public static void ignoreBatteryOptimization(Activity activity) {

        PowerManager powerManager = (PowerManager) activity.getSystemService(Service.POWER_SERVICE);

        boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
        //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
        if(!hasIgnored) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:"+activity.getPackageName()));
            activity.startActivity(intent);
        }
    }

    public static Intent findApp(Context var0, String var1, String var2) {
        PackageManager var3 = var0.getPackageManager();
        List var4 = var3.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Iterator var5 = var4.iterator();

        label32:
        while(true) {
            if(var5.hasNext()) {
                ApplicationInfo var6 = (ApplicationInfo)var5.next();
                if(!var1.equals(var6.packageName)) {
                    continue;
                }

                Intent var7 = new Intent("android.intent.action.MAIN", (Uri)null);
                var7.setPackage(var6.packageName);
                List var8 = var3.queryIntentActivities(var7, 0);
                if(var8 != null && var8.size() > 0) {
                    Iterator var9 = var8.iterator();

                    ResolveInfo var10;
                    do {
                        if(!var9.hasNext()) {
                            continue label32;
                        }

                        var10 = (ResolveInfo)var9.next();
                    } while(!TextUtils.isEmpty(var2) && var10.activityInfo.name.indexOf(var2) == -1);

                    String var11 = var10.activityInfo.packageName;
                    String var12 = var10.activityInfo.name;
                    Intent var13 = new Intent("android.intent.action.MAIN");
                    var13.addCategory("android.intent.category.LAUNCHER");
                    ComponentName var14 = new ComponentName(var11, var12);
                    var13.putExtra("app_package", "com.icongtai.zebra");
                    var13.setComponent(var14);
                    return var13;
                }
            }

            return null;
        }
    }

    public static boolean openActivity(Context context, String className){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString(className);
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
        return true;
    }

    public static boolean openActivity(Context context, String packageName, String className){
        Intent intent = findApp(context, packageName, className);
        context.startActivity(intent);
        return true;
    }
}
