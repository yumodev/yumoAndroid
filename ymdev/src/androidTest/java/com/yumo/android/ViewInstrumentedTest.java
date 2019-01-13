package com.yumo.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.yumo.common.util.Reflect;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ViewInstrumentedTest {
    private final String LOG_TAG = "ViewInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.yumo.android", appContext.getPackageName());
    }

    @Test
    public void testDiaplay() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.i(LOG_TAG, "getCurScreenWidth:" + getCurScreenWidth(appContext));
        Log.i(LOG_TAG, "getCurScreenHeight:" + getCurScreenHeight(appContext));

        Point point = getScreenRealResolution(appContext);
        Log.i(LOG_TAG, "getRealScreen:"+point);
        Log.i(LOG_TAG, "Navigation:"+getNavigationBarHeight(appContext));
    }

    public float getCurScreenWidth(Context cx) {
        WindowManager manage = (WindowManager) cx.getSystemService(Context.WINDOW_SERVICE);
        Display display = manage.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getCurScreenHeight(Context cx) {
        WindowManager manage = (WindowManager) cx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manage.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;

    }

    public Point getScreenRealResolution(Context context) {
        Point size = new Point();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = Reflect.on(display).call("getRawWidth").get();
//                        (Integer) Display.class.getMethod("getRawWidth")
//                        .invoke(display);
                heightPixels = Reflect.on(display).call("getRawHeight").get();
//                        (Integer) Display.class
//                        .getMethod("getRawHeight").invoke(display);
                Log.i(LOG_TAG, "widthPixels = " + widthPixels);
                Log.i(LOG_TAG, "heightPixels = " + heightPixels);
            } catch (Exception ignored) {
            }
        }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Reflect.on(display).call("getRealSize", realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
                Log.i(LOG_TAG, "widthPixels = " + widthPixels);
                Log.i(LOG_TAG, "heightPixels = " + heightPixels);
            } catch (Exception ignored) {
            }
        }
        size.x = widthPixels;
        size.y = heightPixels;
        return size;
    }

    /**
     * 获取导航栏的高度
     * @param activity
     * @return
     */
    public  int getNavigationBarHeight(Context activity) {
        if (!hasNavigationBar(activity)){
            return 0;
        }

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 判断是否存在导航栏
     * @param context
     * @return
     */
    public  boolean hasNavigationBar(Context context) {
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
}
