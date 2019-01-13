package com.yumo.android.common.utils;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.util.YmSecureUtil;
import com.yumo.common.net.YmFileNetUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by wks on 7/24/16.
 */
public class DownImageUtils {

    /**
     * 获取存放图片的目录
     * @return
     */
    public static String getImageDir(){
        String dir = YmAdFileUtil.getAppDataFilesDir(YmContext.getInstance().getAppContext()) + "/yumo/image";

        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        return dir;
    }

    /**
     * 根据文件哈希值获取文件的路径
     * @param hash
     * @return
     */
    public static String getImagePath(String hash){
        return getImageDir() + "/"+ hash;
    }

    /**
     * 获取file///格式的文件路径
     * @param hash
     * @return
     */
    public static String getImageFilePath(String hash){
        return String.format("file//%s", getImagePath(hash));
    }

    /**
     * 返回hash值
     * @param url
     * @return
     */
    public static String downloadImage(String url){
        String tempUid = UUID.randomUUID().toString();
        String tempFilePath = getImagePath(tempUid);
        if (YmFileNetUtil.downFile(url, tempFilePath)){
            String hash = "";
            try {
                hash = YmSecureUtil.getFileMD5(tempFilePath);
                YmFileUtil.rename(tempFilePath, getImagePath(hash), false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return hash;
        }

        return "";
    }
}
