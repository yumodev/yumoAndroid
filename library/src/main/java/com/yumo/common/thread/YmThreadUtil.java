package com.yumo.common.thread;

import android.os.Looper;

/**
 * Created by yumodev on 18/3/
 * 线程相关
 */

public class YmThreadUtil {
    /**
     * 当前是否是主线程
     * @return
     */
    public static boolean isMainThread(){
        if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()){
            return false;
        }
        return true;
    }
}
