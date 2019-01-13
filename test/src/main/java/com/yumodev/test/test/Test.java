package com.yumodev.test.test;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by yumodev on 17/6/26.
 */

public class Test {
    public static final String LOG_TAG = "Test";
    public Test(){
        Log.i(LOG_TAG, "log print by uninstall apk");
    }
    public String getTestStr(){
       return "return test from uninstall apk";
    }
}
