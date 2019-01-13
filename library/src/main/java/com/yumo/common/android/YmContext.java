package com.yumo.common.android;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

/**
 * Created by yumodev on 16/11/28.
 * 封装的一个单例Context类，维护整个App只有一个Context。
 */

public class YmContext {
    private static YmContext mInstance = null;
    private static Context mAppContext = null;

    private YmContext(){}

    public static YmContext getInstance(){
        if (mInstance == null){
            synchronized (YmContext.class){
                return mInstance = new YmContext();
            }
        }
        return mInstance;
    }

    /**
     * 保存ApplicationContext实例。
     * @param context
     */
    public static void setAppContext(Context context){
        mAppContext = context;
    }

    /**
     * 获取保存的ApplicationContext实例。
     * @return
     */
    public static Context getAppContext(){
        return mAppContext;
    }

    public int getDimensionPixelSize(@DimenRes int resId){
       return getAppContext().getResources().getDimensionPixelSize(resId);
    }

    public String getString(@StringRes int resId){
        return getAppContext().getResources().getString(resId);
    }

    public int getColor(@ColorRes int resId){
        return getAppContext().getResources().getColor(resId);
    }
}
