package com.yumodev.process.test;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.util.TestAppUtil;

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
