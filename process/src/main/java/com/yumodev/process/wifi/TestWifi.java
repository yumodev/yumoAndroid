package com.yumodev.process.wifi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.yumo.common.util.YmDateUtil;
import com.yumo.demo.view.YmTestFragment;

import java.util.List;

/**
 * Created by yumo on 2018/4/4.
 */

public class TestWifi extends YmTestFragment{

    WifiReceiver mWifiReciver = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWifiReciver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getContext().registerReceiver(mWifiReciver, intentFilter);
        checkoutPermission();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mWifiReciver);
    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            StringBuilder sb = new StringBuilder();
            sb.append(intent.getAction());
            if (intent.getExtras() != null){
                sb.append(intent.getExtras().toString());
            }

            showToastMessage(sb.toString());
        }
    }

    private boolean checkoutPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_WIFI_STATE"}, 0);
            return false;
        }
        return true;
    }

    public void testA(){
        showToastMessage("A");
    }

    public void testShowAllWifi(){
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResultList = wifiManager.getScanResults();

        for (ScanResult scanResult : scanResultList){
            StringBuilder sb = new StringBuilder();
            sb.append(scanResult.timestamp+" "+ YmDateUtil.getStrFromTime(scanResult.timestamp));
            sb.append(scanResult.SSID+"  ");
            sb.append(scanResult.level+"  ");

            sb.append(scanResult.BSSID);
            showToastMessage(sb.toString());
        }
    }

    public void testStartScan(){
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();

    }

}
