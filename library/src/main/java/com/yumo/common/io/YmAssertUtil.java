package com.yumo.common.io;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yumodev on 17/2/24.
 * 操作Assert相关的工具类
 */

public class YmAssertUtil {

    /**
     * 从Assets 读取一个文件到String中
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getAssertFileToString(Context context, String fileName) throws IOException{
        return YmIoUtil.getStringFromInput(context.getAssets().open(fileName));
    }

    /**
     * 从Assets 读取一个文件到String中
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] getAssertFileToBytes(Context context, String fileName) throws IOException{
        return YmIoUtil.getBytesFromInput(context.getAssets().open(fileName));
    }

    /**
     * 复制一个Assert文件
     * @param context
     * @param assertFile
     * @param newFile
     * @return
     */
    public static boolean copyAssertFile(Context context, String assertFile, String newFile){
        try {
            InputStream inputStream = context.getAssets().open(assertFile);
            return YmFileUtil.saveFile(newFile, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
