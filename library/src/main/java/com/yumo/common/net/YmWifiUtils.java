/**
 * WifiManager.java
 * yumo
 * 2014-12-30
 * 一个wifi管理类。实现了wifi的各种操作。
 */
package com.yumo.common.net;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

/**
 * yumodev
 */
public class YmWifiUtils {
    /**
     * wifi 管理类
     */
    private WifiManager mWifiManager = null;

    /**
     * 上下文
     */
    private Context mContext = null;

    /**
     *
     */
    public YmWifiUtils(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 启用或者关闭一个wifi
     * yumode
     *
     * @param bOpen
     * @return boolean
     * 2014-12-30
     */
    public boolean setEnableWifi(boolean bOpen) {
        boolean bReturn = true;
        if (bOpen && !mWifiManager.isWifiEnabled()) {
            if (mWifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
                bReturn = mWifiManager.setWifiEnabled(bOpen);
        } else if (!bOpen && mWifiManager.isWifiEnabled()) {
            bReturn = mWifiManager.setWifiEnabled(bOpen);
        }
        return bReturn;
    }

    /**
     * 显示wifi的状态。
     * yumo
     *
     * @return int
     * 2014-12-30
     */
    public int getWifiState() {
        return mWifiManager.getWifiState();
    }

    /**
     * 判断wifi是否打开状态。
     * yumodev
     * @return boolean
     * 2014-12-30
     */
    public boolean isWifiEnabled() {
        return mWifiManager.isWifiEnabled();
    }

    public List<ScanResult> getWifiResult() {
        return mWifiManager.getScanResults();
    }

    public WifiManager getWifiManager() {
        return mWifiManager;
    }

    /**
     * 获取信号强度
     * yumodev
     * @param result
     * @return int
     * 2014-12-30
     */
    public int getWifiLevel(ScanResult result) {
        if (result.level >= Integer.MAX_VALUE) {
            result.level = -1;
        }
        return mWifiManager.calculateSignalLevel(result.level, 4);
    }
}
