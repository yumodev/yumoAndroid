package com.yumo.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import com.yumo.demo.config.Config;
import com.yumo.demo.view.YmTestActivity;
import com.yumo.demo.view.YmTestClassFragment;
import com.yumo.demo.view.YmTestPackageFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by yumodev on 17/7/10.
 */
public class YmUIDemoManager {
    private static String mAppPackage = "";


    private YmUIDemoManager(){

    }

    public static YmUIDemoManager getInstance(){
        return SingletonHolder.mInstance;
    }

    /**
     * 设置测试类的包名。
     * @param packageName
     */
    public void setAppPackageName(String packageName){
        mAppPackage = packageName;
    }

    /**
     * 返回测试类所在的包名，默认为App的包名。
      * @param context
     * @return
     */
    public String getAppPackageName(Context context){
        if (TextUtils.isEmpty(mAppPackage)){
            return context.getPackageName();
        }
        return mAppPackage;
    }

    /**
     * 是否打印日志
     * @param debug
     */
    public void setDebugLog(boolean debug){
        Log.setIsDebug(debug);
    }


    /**
     * 打开测试首页
     * @param activity
     * @param fragmentId
     */
    public void openAllUiTestPage(FragmentActivity activity, int fragmentId) {
      openUiAllTestPage(activity, fragmentId, getAppPackageName(activity));
    }

    /**
     * 打开测试首页
     * @param activity
     * @param fragmentId
     * @param packageName
     */
    public void openUiAllTestPage(FragmentActivity activity, int fragmentId, String packageName) {
        openUiAllTestPage(activity, fragmentId, packageName, true);
    }

    /**
     * 打开测试首页
     * @param activity
     * @param fragmentId
     * @param packageName
     * @param showToolbar
     */
    public void openUiAllTestPage(FragmentActivity activity, int fragmentId, String packageName, boolean showToolbar) {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageName);
        if (!showToolbar){
            bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, View.GONE);
        }

        Fragment classFragment = new YmTestClassFragment();
        classFragment.setArguments(bundle);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(fragmentId, classFragment).commit();
    }

    public void openUiTestPackagePage(FragmentActivity activity, int fragmentId, String packageName, boolean showToolbar) {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageName);
        if (!showToolbar){
            bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, View.GONE);
        }

        Fragment classFragment = new YmTestPackageFragment();
        classFragment.setArguments(bundle);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(fragmentId, classFragment).commit();
    }

    /**
     * 打开测试首页
     * @param context
     */
    public void openTestActivity(Context context){
        Intent intent = new Intent(context, YmTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static class SingletonHolder{
        private static YmUIDemoManager mInstance = new YmUIDemoManager();
    }
}
