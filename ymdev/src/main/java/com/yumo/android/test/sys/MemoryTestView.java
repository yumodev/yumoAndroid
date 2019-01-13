package com.yumo.android.test.sys;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.text.format.Formatter;

import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yumo on 2/2/16.
 */
public class MemoryTestView extends YmTestFragment {

    private final String LOG_TAG = "MemoryTestView";

    public void testMemory(){
        String str1 = "/proc/meminfo";
        String str2="";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                Log.d(LOG_TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
    }

    public void testAvailMemory(){
        showToastMessage(Formatter.formatFileSize(getContext(), getAvailMemory(getContext())));
    }

    public void testTotalInternalStorgeSize(){
        showToastMessage(Formatter.formatFileSize(getContext(), getTotalInternalStorgeSize()));
    }

    public long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        return mi.availMem;
    }

    /**
     * 获取手机内部空间大小
     * @return
     */
    public static long getTotalInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long totalBlocks = mStatFs.getBlockCount();
        return totalBlocks * blockSize ;
    }
    /**
     * 获取手机内部可用空间大小
     * @return
     */
    public static long getAvailableInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long availableBlocks = mStatFs.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

}

