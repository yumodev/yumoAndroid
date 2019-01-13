package com.yumodev.test;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.io.YmAssertUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmIoUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.test.util.PackageInstall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Created by yumodev on 2018/7/10.
 */

public class InstallTestView extends YmTestFragment {
    private final String mApkUrl = "https://pro-app-qn.fir.im/ab3c2490e41ea2a82fbeb49d62fa017bd6190b6c.apk?attname=test-debug-system_2.0.apk_2.0.apk&e=1532532220&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:USeNDgg1qqsY2bnclO7ZweQYepw=";
    private final String mApkUrl2 = "https://pro-app-qn.fir.im/894394eaf3e3d8a82c54c99953c8f448c9cb0e58.apk?attname=test-debug-system_3.0.apk_3.0.apk&e=1532532245&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:H83IxCNDjsOWW0XBZbzIEjm3tsM=";

    private boolean mUserPackageUtils = false;
    //private final String mApkUrl = "https://pro-app-qn.fir.im/a66bd9df9234c8248f16d7f1053844e95dc64fba.apk?attname=app-release.apk_2.0.1.apk&e=1530673736&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:3nKvzVc9YW62bYs6vIA9dTldjRc=";

    private PackageInstall mPackageInstall = null;
    //private final String mApkUrl = "https://pro-app-qn.fir.im/376679dd19ace40f044b00fe6cf2c8927bc4b163.apk?attname=zebradrive_sdk_demo-release.apk_2.0.5.apk&e=1531883677&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:qF1Nf2wsYZpOppw7TZZqkXeJXqo=";
    public void testInstallSilentWithRoot(){
        installSilentWithRoot(getApkPath());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPackageInstall = new PackageInstall();
        try {
            mPackageInstall.init(getActivity().getApplicationContext());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void testUsesPackageUtils(){
        mUserPackageUtils = !mUserPackageUtils;
        showToastMessage(mUserPackageUtils+"");
    }

    public void testInstallCool(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "com.coolapk.market-8.4.2-1806281.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
            return;
        }
        installSilent(filePath);
    }


    public void testInstallSlientSystem(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug-system_2.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
            return;
        }
        installSilent(filePath);
    }


    public void testInstallSlientSystem2(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug-system_3.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
            return;
        }
        installSilent(filePath);
    }

    public void testInstallSlientSystemTest1(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug_2.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        if (mUserPackageUtils){
            int code = PackageUtils.installSilent(getContext(), filePath);
            showToastMessage(code+"");
        }else{
            installSilent(filePath);
        }

    }


    public void testInstallSlientSystemTest2(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug_3.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        installSilent(filePath);
    }

    public void testMoveTest1(){
        String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug_2.0.apk";
        YmFileUtil.deleteFile(fileName);
        YmAssertUtil.copyAssertFile(getContext(), "test-debug_2.0.apk", fileName);
    }

    public void testMoveSystemTest1(){
        String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug-system_2.0.apk";
        YmFileUtil.deleteFile(fileName);
        YmAssertUtil.copyAssertFile(getContext(), "test-debug-system_2.0.apk", fileName);
    }

    public void testMoveTest2(){
        String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug_3.0.apk";
        YmFileUtil.deleteFile(fileName);
        YmAssertUtil.copyAssertFile(getContext(), "test-debug_3.0.apk", fileName);
    }

    public void testMoveSystemTest2(){
        String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug-system_3.0.apk";
        YmFileUtil.deleteFile(fileName);
        YmAssertUtil.copyAssertFile(getContext(), "test-debug-system_3.0.apk", fileName);
    }

    public void testInstallTest1(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug_2.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        YmAppUtil.installAPK(getContext(), filePath);
    }

    public void testInstallTest2(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug_3.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        YmAppUtil.installAPK(getContext(), filePath);
    }

    public void testInstallSystemTest1(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug-system_2.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        YmAppUtil.installAPK(getContext(), filePath);
    }

    public void testInstallSystemTest2(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug-system_3.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
        }
        YmAppUtil.installAPK(getContext(), filePath);
    }

    public void testInstallYmDev(){
        String file =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug.apk";
        YmAppUtil.installAPK(getContext(), file);
    }

    public void testMoveApk(){
        String file =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test-debug.apk";
        String newFile = "/system/app/"+ "test-debug.apk";
        boolean result = YmFileUtil.rename(file, newFile, false);
        if (result){
            showToastMessage("移动成功");
        }else{
            showToastMessage("移动失败");
        }
    }

//    public void testDownloadApk(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               boolean result =  YmFileNetUtil.downFile(mApkUrl, getApkPath());
//               if (result){
//                   showToastMessageOnUiThread("下载完成");
//               }else{
//                   showToastMessageOnUiThread("下载失败");
//
//               }
//            }
//        }).start();
//    }
//

    /**
     * 开启辅助功能
     */
    public void testOpenAccessiblitySettings(){
        Intent intent=new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }



    public void testDownloadApk1(){
        download(getContext(), mApkUrl, Environment.DIRECTORY_DOWNLOADS, Uri.parse(mApkUrl).getLastPathSegment());
    }

    public void testDownloadApk2(){
        download(getContext(), mApkUrl2, Environment.DIRECTORY_DOWNLOADS, Uri.parse(mApkUrl).getLastPathSegment());
    }

    public void testInsallTrinea(){
        //PackageUtils.install(getContext(), getApkPath());
    }



    private void download(Context context, String url, String dirName, String fileName){
        //获取DownloadManager对象
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        //创建下载Request请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置下载后保持的文件路径和名称，必须设置
        request.setDestinationInExternalPublicDir(dirName, fileName);
        //开启下载文件，返回一个id，可以通过该Id插件下载的数据和信息
        downloadManager.enqueue(request);
    }

    private String getApkPath(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator+ Uri.parse(mApkUrl).getLastPathSegment();
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * @param apkPath
     *          要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     *
     * command = "su -c pm install -r " + TempFilePath + "\n";
    process = Runtime.getRuntime().exec(command, envp);

     */
    private boolean installSilentWithRoot(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            YmIoUtil.close(dataOutputStream);
            YmIoUtil.close(errorStream);
        }
        return result;
    }


    public void testPakageInstall(){
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  File.separator + "test-debug-system_2.0.apk";
        if (!YmFileUtil.isExistFile(filePath)){
            showToastMessage("不存在"+filePath);
            return;
        }
        try {
            mPackageInstall.installPackage(filePath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * install slient
     *
     * @param filePath
     * @return 0 means normal, 1 means file not exist, 2 means other exception error
     */
    public  int installSilent(String filePath) {
        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0 || file == null || file.length() <= 0 || !file.exists() || !file.isFile()) {
            return 1;
        }

        String[] args = { "pm", "install", "-r", filePath };
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        int result;
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMessage(Log.getStackTraceString(e));
        } catch (Exception e) {
            e.printStackTrace();
            showToastMessage(Log.getStackTraceString(e));
        } catch (Error error){
            showToastMessage(Log.getStackTraceString(error));
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        // TODO should add memory is not enough here
        if (successMsg.toString().contains("Success") || successMsg.toString().contains("success")) {
            result = 0;
        } else {
            result = 2;
        }
        Log.d("test-test", "successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
        showToastMessage("successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
        return result;
    }

    public void testSystemApp(){
        //PackageManager packageManager = getContext().getApplicationContext().getPackageManager();
        ApplicationInfo info = getContext().getApplicationInfo();
        boolean isSystemApp = isSystemApp(info);
        if (isSystemApp){
            showToastMessage("是系统App");
        }else{
            showToastMessage("不是系统App");
        }
    }

    public void testSystemApp1(){
        boolean isSystemApp = PackageUtils.isSystemApplication(getActivity());
        if (isSystemApp){
            showToastMessage("是系统App");
        }else{
            showToastMessage("不是系统App");
        }
    }



    private boolean isSystemApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return false;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return false;
        }
        return true;
    }

    public void testHasIsntallPermission(){
        PackageManager pm = getActivity().getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.INSTALL_PACKAGES", getActivity().getPackageName()));
        if (permission) {
            showToastMessage("有这个权限");
        }else {
            showToastMessage("木有这个权限");
        }
    }

    public void testForceInstallPermission(){
        try {
            getContext().enforceCallingOrSelfPermission(android.Manifest.permission.INSTALL_PACKAGES, "require permission.INSTALL_PACKAGES");
        }catch (Exception e){
            e.printStackTrace();
            showToastMessage(e.getMessage());
        }
    }

    public void testShowSystemVersionCode(){
        showToastMessage(YmAppUtil.getAppVersion(getContext())+"");
    }
}
