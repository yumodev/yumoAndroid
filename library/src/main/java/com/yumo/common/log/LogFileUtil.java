package com.yumo.common.log;

import android.text.TextUtils;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmSdUtil;
import com.yumo.common.util.YmDateUtil;

import java.io.File;

/**
 * Created by yumo on 2018/5/11.
 * 存放日志的具体实现类
 */

public class LogFileUtil {
    public static String mDir;
    public static String mFileMessageInfo;
    public static void setLogDir(String dir){
        mDir = dir;
    }

    public static void saveMessage(String tag, String message, LogFileInfo info){
        if (message == null){
            message = "";
        }

        if (TextUtils.isEmpty(mDir)){
            mDir = createLogRootDir();
        }

        String fileName = "";
        if (info != null){
            fileName = info.getFilePath(mDir);

        }else{
            fileName = mDir + File.separator + "log.txt";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n************************************\n");
        sb.append(YmDateUtil.getStrTime());
        sb.append("\n");
        sb.append(tag);
        sb.append("   ");
        sb.append(message);
        sb.append("\n************************************\n");

        YmFileUtil.saveFile(fileName, sb.toString().getBytes(), true);
    }

    public static String createLogRootDir(){
        String path = YmSdUtil.getSDPath();
        if (TextUtils.isEmpty(path)){
            path = YmAdFileUtil.getFileCachePath(YmContext.getAppContext());
        }

        return path + File.separator + YmContext.getAppContext().getPackageName()+File.separator+"log";
    }

    public static String getLogFileName(String tag){
        LogFileInfo info = Log.getFileInfo(tag);
        String fileName = "";
        if (info != null){
            fileName = info.getFilePath(mDir);

        }else{
            fileName = mDir + File.separator + "log.txt";
        }
        return fileName;
    }

}
