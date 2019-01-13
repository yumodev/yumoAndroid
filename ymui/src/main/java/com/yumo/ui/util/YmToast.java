package com.yumo.ui.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by yumodev on 17/11/27.
 * 自定义的弹出Toast
 */

public class YmToast {
    private static Context mContext = null;

    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    public static void showToast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int resId){
        Toast.makeText(mContext, mContext.getText(resId), Toast.LENGTH_SHORT).show();
    }
}
