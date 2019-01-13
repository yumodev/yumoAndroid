package com.yumo.common.io;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by yumodev on 17/2/24.
 * Sd卡的工具类
 */

public class YmSdUtil {
    /**
     * @return
     * @brief 是否存在外部sd卡
     */
    public static boolean isSdCardExist() {
        return  Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @return
     * @brief 获取外部sd卡的路径，如果存在就返回，如果不存在就返回空的。
     * @author yumodev
     */
    public static String getSDPath() {
        String result = "";
        File sdDir = null;
        if (isSdCardExist()) {
            sdDir = Environment.getExternalStorageDirectory();
        }

        if (sdDir != null) {
            result = sdDir.getPath();
        }
        return result;
    }



    /**
     * @return
     * @brief 获取sd卡的剩余空间
     */
    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(getSDPath());
        double sdFree = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize());
        return (int) sdFree;
    }

    /**
     * @return
     * @brief 获取sd的空间
     * @author yumodeg
     */
    public static long getSdAvailaleSize() {
        if (!isSdCardExist()){
            return 0;
        }

        StatFs stat = new StatFs(getSDPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
}
