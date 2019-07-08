package com.yumo.android.test.sys;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.text.format.Formatter;

import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmDeviceUtil;
import com.yumo.demo.anno.YmMethodTest;
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

    @YmMethodTest(name = "显示内存相关信息")
    public void testShowMemoryInfo() {
        StringBuilder sb = new StringBuilder();
        //获取程序最大的可分配的内存
        String appMaxMemory = Formatter.formatFileSize(YmContext.getInstance().getAppContext(), YmDeviceUtil.getAppMaxMemory());

        sb.append("\n应用程序最大内存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().maxMemory()) + " ");
        sb.append("\n应用程序总共内存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().totalMemory()) + " ");
        sb.append("\n应用程序空闲存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().freeMemory()) + " ");
        sb.append("\nAPP内存："+ String.format("%02fG", getRam(getActivity().getApplicationContext(), true)));

        showToastMessage(sb.toString());
    }

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


    /**
     * 获取总的RAM内存.单位为KB，如果格式后单位为GB
     * void
     * 2015-7-1
     */
    @SuppressLint("NewApi")
    public float getRam(Context context, boolean format) {
        if (context == null){
            return 0f;
        }

        float totalRam = 0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo ramInfo = new ActivityManager.MemoryInfo();


            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return 0f;
            }

            activityManager.getMemoryInfo(ramInfo);
            totalRam = ramInfo.totalMem / 1024;
            if (format) {
                totalRam = totalRam / (1024 * 1024);
            }
        } else {
            String file_path = "/proc/meminfo";
            String ram_info;
            String[] arrayOfRam;

            try {
                FileReader fr = new FileReader(file_path);
                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
                ram_info = localBufferedReader.readLine();
                arrayOfRam = ram_info.split("\\s+");

                totalRam = Float.valueOf(arrayOfRam[1]).intValue();
                localBufferedReader.close();
                if (format) {
                    totalRam = totalRam / (1024 * 1024);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return totalRam;
    }

}

