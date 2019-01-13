package com.yumo.common.net;

import com.yumo.common.io.YmFileUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yumodev on 4/26/16.
 */
public class YmUrlUtils {

    /**
     * 从一个网址中获取文件的名字，
     * @param urlName
     * @return
     */
    public static String getFileName(String urlName){
        try {
            URL url = new URL(urlName);
            String filePath = url.getFile();
            return YmFileUtil.getFileNameFromPath(filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
