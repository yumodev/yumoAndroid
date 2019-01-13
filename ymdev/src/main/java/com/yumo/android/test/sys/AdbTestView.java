package com.yumo.android.test.sys;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.config.Config;
import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by yumodev on 17/9/14.
 */

public class AdbTestView extends YmTestFragment {
    final String LOG_TAG = Config.LOG_TAG;

    /**
     * 执行top命令。
     */
    public void testAdbTop(){
        String content = YmAppUtil.execShell("top -m 1");
        showToastMessage(content);
    }

    /**
     * 执行top命令。
     */
    public void testAdbPs(){
        String content = execShell("ps");
        showToastMessage(content);
    }

    /**
     * 打开Activity。
     * 权限错误
     */
    public void testStartActivity(){
        try {
            Runtime.getRuntime().exec("am start -n air.tv.douyu.android/tv.douyu.view.activity.MainActivity");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测是否已经root
     */
    public void testIsRoot(){
        if (isRooted()){
            showToastMessage("已rooted");
        }else{
            showToastMessage("未rooted");
        }
    }

    /**
     * 检测是否已经root。
     */
    private boolean isRooted() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su")
                || canExecuteCommand("busybox which su");
    }


    /**
     * 是否可以执行该命令
     * @param command
     * @return
     */
    private static boolean canExecuteCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String info = in.readLine();
            if (info != null) return true;
            return false;
        } catch (Exception e) {
            //do noting
        } finally {
            if (process != null) process.destroy();
        }
        return false;
    }

    private String execShell(String adb){
        BufferedReader reader = null;
        String content = "";
        try {
            Process process = Runtime.getRuntime().exec(adb);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            content = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
}
