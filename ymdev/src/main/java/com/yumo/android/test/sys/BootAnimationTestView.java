package com.yumo.android.test.sys;

import android.os.Environment;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.io.YmAssertUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BootAnimationTestView extends YmTestFragment {

    private String mFileName = "bootanimation.zip";
    private String mSystemFileName = "/system/media/bootanimation.zip";
    private String mSdFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"bootanimation.zip";

    /**
     * 检测bootanimation.zip文件是否存在
     */
    public void testExitAnimationFile(){
        File file = new File(mSystemFileName);
        if (file.exists()){
            showToastMessage(mSystemFileName+" 存在该文件");
        }else{
            showToastMessage(mSystemFileName+" 不存在该文件");
        }
    }

    /**
     * 检测bootanimation.zip文件是否存在
     */
    public void testExistAnimationFileInDataLocal(){
        String filePath = "/data/local/bootanimation.zip";
        File file = new File(filePath);
        if (file.exists()){
            showToastMessage(filePath+" 存在该文件");
        }else{
            showToastMessage(filePath+" 不存在该文件");
        }
    }
    /**
     * 判断sd卡是否存在开机动画。
     */
    public void testExitAnimationFileInSd(){
        File file = new File(mSdFileName);
        if (file.exists()){
            showToastMessage("存在该文件");
        }else{
            showToastMessage("不存在该文件");
        }
    }


    public void testCopyAssertFileToSystem(){
        setPermision();
        boolean result = YmAssertUtil.copyAssertFile(getContext(), "bootanimation.zip", getBootAnimationFilePath());
        if (result){
            showToastMessage("复制成功！");
        }else{
            showToastMessage("复制失败！");
        }
    }

    public void testCopySdcardFileToSystem(){
        setPermision();
        boolean result = YmFileUtil.copyFile(mSdFileName, getBootAnimationFilePath());
        if (result){
            showToastMessage("复制成功！");
        }else{
            showToastMessage("复制失败！");
        }

        //RootCommands.moveCopyRoot(Environment.getExternalStorageDirectory() + "/rommate/temp/bootanimation.zip", "/system/media/");

    }

    public void testCopySdcardFileToDataLocal(){
        setPermision();
        boolean result = YmFileUtil.copyFile(mSdFileName, "/data/local/bootanimation.zip");
        if (result){
            showToastMessage("复制成功！");
        }else{
            showToastMessage("复制失败！");
        }

        //RootCommands.moveCopyRoot(Environment.getExternalStorageDirectory() + "/rommate/temp/bootanimation.zip", "/system/media/");

    }
    /**
     * 以root方式存放开机动画。
     */
    public void testRootCp(){
        String cmd = "mount -o remount,rw /system ; cp /sdcard/bootanimation.zip /system/media/bootanimation.zip; chmod 777 /system/media/bootanimation.zip ; mount -o remount,ro /system";
        String result  = doCommand(cmd);
        showToastMessage(result);
    }

    /**
     * 以root权限删除开机动画权限
     */
    public void testDeleteByRoot(){
        String cmd = "mount -o remount,rw /system ; rm -f /system/media/bootanimation.zip ; mount -o remount,ro /system";
        String result  = doCommand(cmd);
        showToastMessage(result);
    }

    /**
     * 以系统APP的身份，复制开机动画
     */
    public void testSystemCp(){
        showToastMessage("test2");
        //String cmd = "mount -o remount,rw /system ; cp /sdcard/bootanimation.zip /system/media/bootanimation.zip; chmod 777 /system/media/bootanimation.zip ; mount -o remount,ro /system";

        String result ="";
        result  = YmAppUtil.execShell(" mount -o remount,rw /system");
        result  =  YmAppUtil.execShell(" cp /sdcard/bootanimation.zip /system/media/bootanimation.zip");
        result  =  YmAppUtil.execShell(" chmod 777 /system/media/bootanimation.zip");
        result  =  YmAppUtil.execShell( " mount -o remount,ro /system");
        showToastMessage(result);
    }

    /**
     * 以系统APP的身份，复制开机动画
     */
    public void testSystemCp3(){
        showToastMessage("test3");
        String cmd = " mount -o remount,rw /system;cp /sdcard/bootanimation.zip /system/media/bootanimation.zip;chmod 777 /system/media/bootanimation.zip;mount -o remount,ro /system";
        String result = YmAppUtil.execShell(cmd);
//        String result ="";
//        result  = YmAppUtil.execShell("mount -o remount,rw /system");
//        result  =  YmAppUtil.execShell("cp /sdcard/bootanimation.zip /system/media/bootanimation.zip");
//        result  =  YmAppUtil.execShell("chmod 777 /system/media/bootanimation.zip");
//        result  =  YmAppUtil.execShell("mount -o remount,ro /system");
        showToastMessage(result);
    }

    /**
     * 获取权限
     */
    public void testSystemPermission(){
        String result  = YmAppUtil.execShell(" mount ");
        showToastMessage(result);
    }

    public void testSystemPermissionMountSystem(){
        String result  = YmAppUtil.execShell(" mount | grep system ");
        showToastMessage(result);
    }

    public void testSystemPermissionMountRoot(){
        String result  = YmAppUtil.execRootShell(" mount | grep system ");
        showToastMessage(result);
    }

    public void testSystemRw(){
        String result  =  YmAppUtil.execShell(" mount -o rw,remount -t auto /system ; mount ");
        showToastMessage(result);
    }


    public void testSystemRootRw(){
        String result  =  YmAppUtil.execRootShell(" mount -o rw,remount -t auto /system ; mount ");
        showToastMessage(result);
    }

    public void testRootRw(){
        String result  =  YmAppUtil.execRootShell(" mount -o remount,rw /system ");
        showToastMessage(result);
    }

    public void testDeleteBySystem(){
        showToastMessage("test2");
        String cmd = "mount -o remount,rw /system ; cp /sdcard/bootanimation.zip /system/media/bootanimation.zip; chmod 777 /system/media/bootanimation.zip ; mount -o remount,ro /system";

        String result ="";
        result  = YmAppUtil.execShell("mount -o remount,rw /system");
        result  =  YmAppUtil.execShell("rm -f /system/media/bootanimation.zip");
        result  =  YmAppUtil.execShell("mount -o remount,ro /system");
        showToastMessage(result);
    }

    public void testCopyAssertFileToSdcard(){
        boolean result = YmAssertUtil.copyAssertFile(getContext(), "bootanimation.zip", mSdFileName);
        if (result){
            showToastMessage("复制成功！");
        }else{
            showToastMessage("复制失败！");
        }
    }

    private void setPermision(){
        String adbResult = doCommand("mount -o rw,remount /system");
        adbResult = doCommand("chmod 777 /system/media");
        adbResult = doCommand("chmod 777 /system/media");
        adbResult =  doCommand("chmod 777 /system/media/bootanimation.zip");
    }

    public void testShowSystemMediaDir(){
        showToastMessage(Environment.getRootDirectory().getPath()+File.separator+"media");
    }

    private String getBootAnimationFilePath(){
       return Environment.getRootDirectory().getPath()+File.separator+"media/bootanimation.zip";
    }


    public  String doCommand(String cmd) {
        Process p;
        DataOutputStream os = null;
        InputStream is = null;
        //SystemProperties.set("persist.sys.root_access", "2");
        try {
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = bf.readLine()) != null) {
                result.append(line);
            }
            os.flush();
            int ret = p.waitFor();
            if (ret == 0) {
                Log.d("liuwei", "docommand success!");
                return result.toString();
            } else {
                Log.d("liuwei", "docommand fail!");
                return "0";
            }

        } catch (IOException e) {
            Log.e("liuwei", "doCommond Exception= " + e.toString());
        } catch (InterruptedException e) {
            Log.e("liuwei", "doCommond InterruptedException = " + e.toString());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }
}
