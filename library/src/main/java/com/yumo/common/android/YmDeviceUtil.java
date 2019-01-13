package com.yumo.common.android;

/**
 * yumodev 设备相关的工具类
 */
public class YmDeviceUtil {

    /**
     * 获取应用的最大内存占用字节。
     * yumodev
     *
     * @return long
     * 2015-3-14
     */
    public static long getAppMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
}
