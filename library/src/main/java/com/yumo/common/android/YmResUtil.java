package com.yumo.common.android;

import android.content.Context;

/**
 * Created by yumodev on 17/2/24.
 * 资源相关的工具类
 */

public class YmResUtil {

    /**
     * dip to px
     *
     * @param context
     * @param dip
     * @return
     */
    public static float dipToPx(Context context, float dip) {
        if (context == null) {
            return dip;
        }

        final float density = context.getResources().getDisplayMetrics().density;
        return dip * density + 0.5f;
    }

    /**
     * px to dip
     *
     * @param context
     * @param px
     * @return
     */
    public static int pxToDip(Context context, float px) {
        if (context == null) {
            return (int) px;
        }
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2sp(Context context, float px) {
        if (context == null) {
            return (int) px;
        }

        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        if (context == null) {
            return (int) sp;
        }

        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

}
