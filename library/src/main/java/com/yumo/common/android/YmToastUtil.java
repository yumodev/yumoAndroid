package com.yumo.common.android;

import android.widget.Toast;

/**
 * Created by yumo on 2018/6/14.
 */

public class YmToastUtil {

    public static void showMessage(String message){
        Toast.makeText(YmContext.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }
}
