package com.yumo.android.test.sys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yumo.demo.view.YmTestFragment;
import com.yumo.ui.util.ViewUtils;

public class ScreenTest extends YmTestFragment {

    /**
     * 获取屏幕宽度
     */
    public void testScrrenWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;


        showToastMessage(width+"");
    }

    /**
     * 获取屏幕高度
     */
    public void testScrrenHeight(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        showToastMessage(height+"");
    }

    /**
     * 获取状态栏高度
     */
    public void testStatusHeight(){
        showToastMessage(ViewUtils.getStatusBarHeight(getContext())+"");
    }

    public void testRealScreen(){
        Point point = ViewUtils.getRealScreenSize(getActivity().getApplicationContext());
        showToastMessage(point.x+" "+point.y);
    }


    /**
     * 虚拟按键的高度
     */
    public void testGetNavigationBarHeight() {
        int navigationBarHeight = -1;
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }

        showToastMessage(navigationBarHeight+"");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void testScreenSize(){
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x/ dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        showToastMessage(screenInches+"");
    }

    public void testScreenSize1(){
        Point point = ViewUtils.getRealScreenSize(getActivity().getApplicationContext());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x/ dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        showToastMessage(screenInches+"");
    }

}
