package com.yumo.android.common;

import com.yumo.android.R;
import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmSdUtil;

import java.io.File;

/**
 * Created by yumodev on 4/11/16.
 */
public class YumoConfig {
    public static final String LOG_TAG = "YmDev";
    private static final String APPDIR = "yumo";

    /**
     * @return
     * @brief 获取该App保存图片的目录，如果目录不存在就创建
     */
    public static String getImageDirectory() {
        String dir = YmSdUtil.getSDPath() + "/" + APPDIR + "/image";

        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        return dir;
    }

    /**
     * @return
     * @brief 获取该App保存文件缓存的目录，如果目录不存在就创建
     */
    public static String getFileDirectory() {
        String dir = YmSdUtil.getSDPath() + "/" + APPDIR + "/file";

        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        return dir;
    }

    public static boolean isTablet(){
        return YmContext.getInstance().getAppContext().getResources().getBoolean(R.bool.isTablet);
    }
}
