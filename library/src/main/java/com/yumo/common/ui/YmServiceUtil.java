package com.yumo.common.ui;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by yumodev on 17/2/24.
 */

public class YmServiceUtil {

    /**
     * 判断服务是否已经运行
     *
     * @param context
     * @param service
     * @return boolean
     * 2015-1-26
     */
    public static boolean isServiceRunning(Context context, String service) {
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> serviceList = (ArrayList<ActivityManager.RunningServiceInfo>) actManager.getRunningServices(50);
        for (int n = 0; n < serviceList.size(); n++) {
            if (serviceList.get(n).service.getClassName().toString().equals(service)) {
                return true;
            }
        }

        return false;
    }
}
