package com.yumo.common.android;

import android.content.Context;

/**
 * Created by yumodev on 17/2/24.
 * 显示相关的工具方法
 */

public class YmDisplayUtil {

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
