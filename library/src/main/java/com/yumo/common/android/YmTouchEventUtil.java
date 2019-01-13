/**
 * YmTouchEventUtil.java
 * yumo
 * 2014-12-2
 */
package com.yumo.common.android;

import android.view.MotionEvent;

import java.util.Locale;

/**
 * yumo
 */
public class YmTouchEventUtil {

    public static String getTouchAction(int actionId) {
        String actionName = "Unknow:id=" + actionId;
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                actionName = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                actionName = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                actionName = "ACTION_UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                actionName = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_OUTSIDE:
                actionName = "ACTION_OUTSIDE";
                break;
        }
        return actionName;
    }

    public static String formatTouchPoint(MotionEvent mv) {
        if (mv == null) {
            return "";
        }
        return String.format(Locale.ENGLISH, "x:%f, y:%f rawX:%f rawY: %f", mv.getX(), mv.getY(), mv.getRawX(), mv.getRawY());
    }

}