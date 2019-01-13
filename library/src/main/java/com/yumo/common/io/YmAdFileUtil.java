package com.yumo.common.io;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by yumodev on 4/11/16.
 * Android 文件操作相关的类
 */
public class YmAdFileUtil {

    /**
     * 获取/data/data/<包名>/file文件夹
     * @param context
     * @return
     */
    public static String getAppDataFilesDir(Context context) {
        if (context != null) {
            return context.getFilesDir().getPath();
        }

        return "";
    }

    /**
     * 获取系统缓存目录
     * @param context
     * @return
     */
    public static String getAppDataCacheDir(Context context) {
        if (context != null) {
            return context.getCacheDir().getPath();
        }
        return "";
    }

    /**
     * 获取一个文件目录
     * @param context
     * @return
     */
    public static String getFileCachePath(Context context){
        String cachePath = null;
        if (YmSdUtil.isSdCardExist()) {
            cachePath = context.getExternalCacheDir().getPath();
        }

        if(cachePath == null) {
            cachePath = context.getCacheDir().getPath();
        }

        return cachePath;
    }

    /**
     * 获取文件目录下的子目录，如果不存在就创建。
     * @param context
     * @param dir
     * @return
     */
    public static String getFileCache(Context context, String dir){
        String cachePath = getFileCachePath(context);
        String newDir = cachePath + File.separator + dir;
        if (!YmFileUtil.isExistDirectory(newDir)){
            if (!YmFileUtil.createDirectory(newDir)){
                return null;
            }
        }

        return newDir;
    }
}
