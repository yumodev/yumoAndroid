package com.yumo.android.test.net.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.yumo.demo.view.YmTestFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yumodev on 17/9/19.
 */

public class WifiTestView extends YmTestFragment {

    public void testIsWifiApOpen(){
        boolean isOpen = isWifiApOpen(getContext());
        if (isOpen){
            showToastMessage("Wifi便携式热点已打开");
        }else{
            showToastMessage("Wifi便携式热点已关闭");
        }
    }

    public void testGetAppWifiConfiguration(){
        WifiConfiguration configuration = getApWifi(getContext());
        if (configuration != null){
            showToastMessage(configuration.toString());
        }else{
            showToastMessage("未获取到Wifi热点");
        }
    }


    private WifiConfiguration getApWifi(Context context){
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //拿到getWifiApConfiguration()方法
            Method method = manager.getClass().getDeclaredMethod("getWifiApConfiguration");
            //调用getWifiApConfiguration()方法，获取到 热点的WifiConfiguration
            WifiConfiguration configuration = (WifiConfiguration) method.invoke(manager);
            return configuration;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isWifiApOpen(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //通过放射获取 getWifiApState()方法
            Method method = manager.getClass().getDeclaredMethod("getWifiApState");
            //调用getWifiApState() ，获取返回值
            int state = (int) method.invoke(manager);
            //通过放射获取 WIFI_AP的开启状态属性
            Field field = manager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            //获取属性值
            int value = (int) field.get(manager);
            //判断是否开启
            if (state == value) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }
}
