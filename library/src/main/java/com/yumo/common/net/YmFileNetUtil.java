package com.yumo.common.net;

import android.text.TextUtils;

import com.yumo.common.io.YmCloseUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yumodev on 4/26/16.
 * 与网络相关的文件类，下载文件
 * 网络部分使用OkHttp实现
 */
public class YmFileNetUtil {

    /**
     * 下载一个文件，并保存到给定的文件中
     * @param url
     * @param fileName
     * @return
     */
    public static boolean downFile(String url, String fileName) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        boolean result = false;

        InputStream is = null;
        FileOutputStream fileStream = null;
        BufferedInputStream bis = null;
        try {
            is = YmOkHttpUtil.getBodyInputStream(url);

            File file = new File(fileName);
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();

            fileStream = new FileOutputStream(file);
            bis = new BufferedInputStream(is);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = bis.read(buffer)) != -1) {
                fileStream.write(buffer, 0, len);
            }

            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            YmCloseUtil.close(is);
            YmCloseUtil.close(fileStream);
            YmCloseUtil.close(bis);
        }

        return result;
    }

}
