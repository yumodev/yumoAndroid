package com.yumo.android.test.sys;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import com.yumo.demo.view.YmTestFragment;

public class TestLetv extends YmTestFragment {

    //com.letv.android.letvsafe/.AutobootManageActivity
    //com.letv.android.letvsafe/.BackgroundAppManageActivity
    //com.letv.android.supermanager/.activity.SuperManagerActivity
    public void testBgStartUpManagerActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.letv.android.letvsafe/.AutobootManageActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }

    public void testBackgroundAppManagerActivity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = ComponentName.unflattenFromString("com.letv.android.letvsafe/.BackgroundAppManageActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        getContext().startActivity(intent);
    }
}
