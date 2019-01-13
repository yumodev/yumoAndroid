package com.yumo.common.debug;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yumodev on 2/1/16.
 * record time
 */
public class RuntimeUtils {

    private static final String LOG_TAG = "RuntimeUtils";

    private static Map<String, Long> mTagMaps = new HashMap<>();

    /**
     *
     * @param key
     */
    public static void beginRecordTimeByTag(String key) {
        long time = System.currentTimeMillis();
        mTagMaps.put(key, time);
    }

    /**
     *
     * @param key
     * @return
     */
    public static long endRecordTimeByTag(String key) {
        if (mTagMaps.containsKey(key)){
            long time = mTagMaps.get(key);
            mTagMaps.remove(key);
            long now = System.currentTimeMillis();
            return now - time;
        }
        return 0L;
    }
}
