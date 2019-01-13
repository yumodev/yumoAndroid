package com.yumo.android.test.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/9/18.
 * Hook Context，拦截部分Activity，Service，BroadCast的发送
 */

public class HookContextTestView extends YmTestFragment {


    static class HookContext extends ContextWrapper{

        public HookContext(Context base) {
            super(base);
        }

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
        }


        @Override
        public void startActivity(Intent intent) {
            ComponentName cn = intent.getComponent();
            String targetPackage = cn.getPackageName();
            if (!getPackageName().equals(targetPackage)){
                //return;
            }
            super.startActivity(intent);
        }

        @Override
        public ComponentName startService(Intent service) {
            ComponentName cn = service.getComponent();
            String targetPackage = cn.getPackageName();
            if (!getPackageName().equals(targetPackage)){
               // return cn;
            }
            return super.startService(service);
        }

        @Override
        public void sendBroadcast(Intent intent) {
            ComponentName cn = intent.getComponent();
            String targetPackage = cn.getPackageName();
            if (!getPackageName().equals(targetPackage)){
                //return;
            }
            super.sendBroadcast(intent);
        }
    }

    HookContext mHookContext = null;
    public HookContext getHookContext(Context context){
        if (mHookContext != null){
            return mHookContext;
        }
        mHookContext = new HookContext(context);
        return mHookContext;
    }

    /**
     * 打开外部APP
     */
    public void testStartApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //air.tv.douyu.android/tv.douyu.view.activity.MainActivity
        ComponentName cn = new ComponentName("air.tv.douyu.android","tv.douyu.view.activity.MainActivity");
        intent.setComponent(cn);
        getHookContext(getContext()).startActivity(intent);
    }


    /**
     * 打开外部服务
     */
    public void testStartService(){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName("com.mx.browser.star","com.umeng.message.UmengService");
        intent.setComponent(cn);
        getHookContext(getContext()).startService(intent);
    }

    /**
     * 发送广播
     */
    public void testSendBroadCast(){
        Intent intent = new Intent(Intent.ACTION_SCREEN_ON);
        //air.tv.douyu.android/tv.douyu.view.activity.MainActivity
        ComponentName cn = new ComponentName("com.mx.browser.star",null);
        intent.setComponent(cn);
        getHookContext(getContext()).sendBroadcast(intent);
    }
}
