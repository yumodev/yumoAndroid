package com.yumodev.process.power;

import android.app.Service;
import android.os.PowerManager;
import android.view.WindowManager;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/4/7.
 * <uses-permission android:name="android.permission.WAKE_LOCK" />
 <uses-permission android:name="android.permission.DEVICE_POWER"/>

 */

public class PowerTest extends YmTestFragment {

    public void testAAA(){
        showToastMessage("AAA");
    }

    /**
     * 设置屏幕常亮
     */
    public void testKeepScreenOn(){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 取消屏幕常量
     */
    public void testCancelScreenOn(){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void testWakeLock(){
        //获取PowerManager对象。
        PowerManager powerManager = (PowerManager) getContext().getSystemService(Service.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "newWake");

        //获取唤醒锁。
        wakeLock.acquire();

        //执行自定义操作。

        //释放唤醒锁
        wakeLock.release();

    }
}
