package com.yumodev.flurry;

import android.content.Context;
import android.os.SystemClock;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yumo on 2018/4/9.
 */

public class FlurryUtil {

    public static void onStartSession(Context context) {
        try {
            FlurryAgent.setLogEvents(true);
            FlurryAgent.onStartSession(context);
        } catch (Throwable t) {
        }

    }

    public static void onEndSession(Context context) {
        try {
            FlurryAgent.onEndSession(context);
        } catch (Throwable t) {
        }

    }

    public static void onEvent(String eventId) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            FlurryAgent.logEvent(eventId, params);
        } catch (Throwable t) {
        }

    }


    public static void onEventUseMap(String eventId, Map<String, String> map) {
        try {
            FlurryAgent.logEvent(eventId, map);
        } catch (Throwable t) {
        }

    }


    public static void onEvent(String eventId, String paramValue) {
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(eventId, paramValue);
            FlurryAgent.logEvent(eventId, params);
        } catch (Throwable t) {
        }

    }

    public static void onEvent(String eventId, String paramKey, String paramValue) {
        long currentTime = SystemClock.elapsedRealtime();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put(paramKey, paramValue);
            FlurryAgent.logEvent(eventId, params);
        } catch (Throwable t) {
        }

    }

}