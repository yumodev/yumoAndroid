package com.yumodev.ui.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * Created by yumodev on 5/13/16.
 */
public class YmViewUtils {

    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static int dip2px(Context cx, float dpValue) {
        return (int) (dpValue * getDensity(cx) + 0.5f);
    }

    public static int px2dip(Context cx, float pxValue) {
        return (int) (pxValue / getDensity(cx) + 0.5f);
    }

    public static int px2sp(Context cx,float pxValue) {
        float fontScale = cx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context cx,float spValue) {
        float fontScale = cx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 按屏幕大小渲染一个view
     * @param view
     */
    public static void layoutViewAllDisplay(View view){
        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        layoutView(view, metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 重新布局一个WebView
     * @param v view
     * @param width 宽
     * @param height 高
     */
    public static void layoutView(View v, int width, int height) {
        // validate view.width and view.height
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        // validate view.measurewidth and view.measureheight
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }


    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    // 判断设备是否有返回键、菜单键来确定是否有 NavigationBar
    public static boolean hasNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager manage = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = manage.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }

    // 获取 NavigationBar 的高度
    public static int getNavigationBarHeight(Context activity) {
        if (!hasNavigationBar(activity)){
            return 0;
        }

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);

    }



    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        Point screenSize = new Point();
        if (Build.VERSION.SDK_INT < 17){
            try {
                screenSize.x = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                screenSize.y = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        }else{
            windowManager.getDefaultDisplay().getRealSize(screenSize);
        }
        return screenSize;
    }


}
