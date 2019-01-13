package com.yumo.android.test.io;

import android.util.Log;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmSdUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

/**
 * Created by yumo on 5/2/16.
 */
public class FileTest extends YmTestFragment {
    private final String LOG_TAG = "FileTest";

    /**
     * 获取data/data/package/files 目录
     */
    public void testGetCacheDir(){
        String filePath = YmAdFileUtil.getAppDataCacheDir(getContext());
        Log.d(LOG_TAG, "testGetCacheDir:"+ filePath);
        showToastMessage(filePath);
    }

    /**
     * 获取data/data/package/files目录
     */
    public void testGetFilesDir(){
        String filePath = YmAdFileUtil.getAppDataFilesDir(getContext());
        Log.d(LOG_TAG, "testGetFilesDir:"+ filePath);
        showToastMessage(filePath);
    }

    public void testSaveFileToDataFilesDir(){
        Random random = new Random();
        int num = random.nextInt(100);
        String fileName = YmAdFileUtil.getAppDataFilesDir(getContext())+File.separator+num+".txt";
        String content = "content";
        boolean result = YmFileUtil.saveFile(fileName, content.getBytes());
        if(result){
            Log.d(LOG_TAG, "testSaveFileToDataFilesDir success");
        }else{
            Log.d(LOG_TAG, "testSaveFileToDataFilesDir failed");
        }

        showToastMessage(fileName);
    }

    /**
     * 打印文件列表
     */
    public void testListFiles(){
        String filePath =  YmAdFileUtil.getAppDataFilesDir(getContext());
        Log.d(LOG_TAG, "testListFiles:"+ YmAdFileUtil.getAppDataFilesDir(getContext()));
        File file = new File(filePath);
        File[] listFiles = file.listFiles();
        for (File f : listFiles){
            Log.d(LOG_TAG, "print fileName:"+f.getName());
        }
    }

    /**
     * 按照后缀名搜索文件
     */
    public void testFilenameFilterWithExtension(){
        String filePath =  YmAdFileUtil.getAppDataFilesDir(getContext());
        Log.d(LOG_TAG, "testListFiles:"+ YmAdFileUtil.getAppDataFilesDir(getContext()));
        File file = new File(filePath);
        File[] listFiles = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        for (File f : listFiles){
            Log.d(LOG_TAG, "print fileName:"+f.getName());
        }
    }

    /**
     * 按照后缀名搜索文件
     */
    public void testFilenameFilterSd(){
        String filePath =  YmSdUtil.getSDPath();
        Log.d(LOG_TAG, "testListFiles:"+ filePath);
        File file = new File(filePath);
        File[] listFiles = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().indexOf("m") >= 0;
            }
        });
        for (File f : listFiles){
            Log.d(LOG_TAG, "print fileName:"+f.getName());
        }
    }
}
