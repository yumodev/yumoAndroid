package com.yumo.common.log;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by yumo on 2018/5/11.
 */

public class LogFileInfo {
    private String tag;
    private String fileName;
    private String fileDir;

    private LogMessageInfo messageInfo;
    private boolean isDelete;

    public LogFileInfo(String tag){
        this.tag = tag;
    }

    public LogFileInfo(String tag, String fileName){
        this.tag = tag;
        this.fileName = fileName;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LogMessageInfo getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(LogMessageInfo messageInfo) {
        this.messageInfo = messageInfo;
    }

    /**
     * 获取文件完整目录路径
     * @param dir
     * @return
     */
    public String getFilePath(String dir){
        String logFileName = tag;
        if (!TextUtils.isEmpty(fileName)){
            logFileName = fileName;
        }

        if (!TextUtils.isEmpty(fileDir)){
            return fileDir + File.separator + logFileName;
        }

        return dir + File.separator + logFileName;
    }
}
