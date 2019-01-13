package com.yumo.android.test.sys;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.yumo.android.common.YumoConfig;
import com.yumo.demo.view.YmTestFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

/**
 * Created by yumodev on 3/2/16.
 * package and app info view
 */
public class PackageTestView extends YmTestFragment {
    private final String LOG_TAG = YumoConfig.LOG_TAG;

    /**
     * 获取APP列表
     */
    public void testShowAppList(){
        PackageManager packageManager = getContext().getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        for (ApplicationInfo applicationInfo : applicationInfoList){
            StringBuilder sb = new StringBuilder();
            sb.append("Name:"+ applicationInfo.loadLabel(packageManager).toString());
            sb.append(", packageName:"+ applicationInfo.packageName);
            sb.append(", uid:"+ applicationInfo.uid);
            sb.append(", isSystem:"+ isSystemApp(applicationInfo));
            sb.append("\n");
            Log.i(LOG_TAG, sb.toString());
        }
    }

    /**
     * 获取APP列表
     */
    public void testAppList(){
        String appList = getAppList();
        Log.i(LOG_TAG, "appList:"+appList);
    }

    /**
     * 获取包名的icon
     */
    public void testShowAppIcon(){
        try {
            Drawable drawable = getActivity().getPackageManager().getApplicationIcon("com.mx.browser.star");
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawable);
            showTestView(imageView);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取第三方APP的Uid
     */
    public void testGetOtherAppUid(){
        String packageName = "com.mx.browser.star";
        int uid = getUid(packageName);
        showToastMessage(packageName+" uid:"+uid);
    }

    public void testKillApp(){
        /**
         * Caused by: java.lang.SecurityException: Permission Denial: killBackgroundProcesses() from pid=14107, uid=10562 requires android.permission.KILL_BACKGROUND_PROCESSES
         */
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Service.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses("com.mx.browser.star");
    }

    /**
     * 通过一个包名获取其UID
     * @param packageName
     * @return
     */
    private int getUid(String packageName){
        int uid = -1;
        PackageManager packageManager = getContext().getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        for (ApplicationInfo applicationInfo : applicationInfoList){
            if (packageName.equals(applicationInfo.packageName)){
                uid = applicationInfo.uid;
                break;
            }
        }
        return uid;
    }

    private String getAppList(){
        PackageManager packageManager = getContext().getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        StringBuilder sb = new StringBuilder();
        for (ApplicationInfo applicationInfo : applicationInfoList){
            if (!isSystemApp(applicationInfo)){
                sb.append("Name:"+ applicationInfo.loadLabel(packageManager).toString());
                sb.append(", packageName:"+ applicationInfo.packageName);
                sb.append(", uid:"+ applicationInfo.uid);
                sb.append(", isSystem:"+ isSystemApp(applicationInfo));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String getAppListShort(){
        PackageManager packageManager = getContext().getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        StringBuilder sb = new StringBuilder();
        for (ApplicationInfo applicationInfo : applicationInfoList){
            sb.append("\n packageName:"+ applicationInfo.packageName);
            sb.append("\n uid:"+ applicationInfo.uid);
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isSystemApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return false;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return false;
        }
        return true;
    }





}
