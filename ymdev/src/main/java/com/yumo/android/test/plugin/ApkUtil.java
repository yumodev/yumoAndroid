package com.yumo.android.test.plugin;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmAssertUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmIoUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * Created by yumodev on 17/6/26.
 * 解析APK相关信息
 */

public class ApkUtil {
    public static final String LOG_TAG = "ApkUtil";

    public static Map<String, DexClassLoader> mMapDex = new HashMap<>();

    public static void loadApkFromAsserts(){
        try {
            String[] files = YmContext.getAppContext().getAssets().list("apk");
            for (String fileName:files) {
                Log.i(LOG_TAG, "PrintApk:"+fileName);
                String cacheFileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + fileName;
                File apkParent = new File(cacheFileName).getParentFile();
                File tempSoDir = new File(apkParent, "temp");
                File libDir = new File(apkParent, "lib");
                if (YmFileUtil.isExistFile(cacheFileName)){
                    YmFileUtil.deleteFile(cacheFileName);
                    YmFileUtil.deleteDir(tempSoDir.getAbsoluteFile());
                    YmFileUtil.deleteDir(libDir.getAbsoluteFile());
                }
                String assertFileName = "apk" + File.separator + fileName;
                boolean result = YmAssertUtil.copyAssertFile(YmContext.getAppContext(), assertFileName, cacheFileName);
                if (result){

                    Set<String> soList = unZipSo(cacheFileName, tempSoDir);
                    if (soList != null) {
                        for (String soName : soList) {
                            copySo(tempSoDir, soName, libDir.getAbsolutePath());
                        }
                        YmFileUtil.deleteDir(tempSoDir);
                        YmFileUtil.printDir(libDir.getAbsolutePath());
                    }
                   printApkInfo(cacheFileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入一个APK的路径，打印其方法。
     * @param apkPath
     */
    public static void printApkClass(String apkPath){
        try {
            Log.i(LOG_TAG, apkPath);
            DexFile df = new DexFile(apkPath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
                String s = iter.nextElement();
                Log.i(LOG_TAG, "" + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printPackageInfo(PackageInfo info){
        Log.i(LOG_TAG, info.packageName);
        if (info.sharedUserId != null){
            Log.i(LOG_TAG, info.sharedUserId);
        }

        Log.i(LOG_TAG, info.versionName);
        Log.i(LOG_TAG, info.versionCode+"");
        Log.i(LOG_TAG, Arrays.asList(info.activities).toString());
        Log.i(LOG_TAG, info.firstInstallTime+"");
        if (info.configPreferences != null){
            Log.i(LOG_TAG, Arrays.asList(info.configPreferences).toString());
        }
    }

    public static void printApplicationInfo(ApplicationInfo info){
        Log.i(LOG_TAG, "classname"+info.className);
        Log.i(LOG_TAG, "sourceDir"+info.sourceDir);
        Log.i(LOG_TAG, "processName"+info.processName);
        Log.i(LOG_TAG, "dataDir"+info.dataDir);
        Log.i(LOG_TAG, "backupAgentName"+info.backupAgentName);
        //Log.i(LOG_TAG, "classname"+info.deviceProtectedDataDir);
        Log.i(LOG_TAG, "managerSpaceActivityName"+info.manageSpaceActivityName);
        //Log.i(LOG_TAG, "nativeLibraryDir"+info.nativeLibraryDir);
        Log.i(LOG_TAG, "publicSourceDir"+info.publicSourceDir);
        Log.i(LOG_TAG, "taskAffinity"+info.taskAffinity);
        Log.i(LOG_TAG, "name"+info.name);
        Log.i(LOG_TAG, "packageName"+info.packageName);
        //Log.i(LOG_TAG, "minSdkVersion"+info.minSdkVersion);
    }

    public static void printApkInfo(String apkPath){
        PackageManager pm = YmContext.getAppContext().getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        printPackageInfo(packageInfo);
        printApplicationInfo(packageInfo.applicationInfo);
        //Log.i(LOG_TAG, "appName:"+pm.getApplicationLabel(packageInfo.applicationInfo));
    }

    public static Class<?> getClassFromUnInstallApk(String apkPath, String className){
        DexClassLoader classLoader = mMapDex.get(apkPath);
        if (classLoader == null){
            String path = YmAdFileUtil.getFileCache(YmContext.getAppContext(), "dex");
            String lib = YmAdFileUtil.getFileCache(YmContext.getAppContext(), "lib");
            classLoader = new DexClassLoader(apkPath, path,
                    lib, YmContext.getAppContext().getClassLoader());
            mMapDex.put(apkPath, classLoader);
        }


        try {
            Class destClass = classLoader.loadClass(className);
            return destClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }  catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void testClass(Class testClass){
        Constructor constructor = null;
        try {
            constructor = testClass.getConstructor(new Class[] {});
            Object test = constructor.newInstance(new Object[] {});

            Method getTestMethod = testClass.getMethod("getTestStr", new Class[0]);
            getTestMethod.setAccessible(true);
            Object str = getTestMethod.invoke(test, new Object[0]);
            Log.i(LOG_TAG, str.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static void testJni() {
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "ndk-debug.apk";
        Class testClass = ApkUtil.getClassFromUnInstallApk(fileName, "com.yumodev.ndk.TestJni");

        try {
            Method method = testClass.getMethod("stringFromJNI");
            Log.i(LOG_TAG, "begin testJni:"+method.getName());
            Object object = method.invoke(null);
            Log.i(LOG_TAG, "StringFromJni: "+object.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取so列表
     * @param apkFile
     * @param tempDir
     * @return
     */
    public static Set<String> unZipSo(String apkFile, File tempDir) {
        HashSet<String> result = null;

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        Log.i(LOG_TAG, "开始so文件"+ tempDir.getAbsolutePath());

        ZipFile zfile = null;
        boolean isSuccess = false;
        BufferedOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            zfile = new ZipFile(apkFile);
            ZipEntry ze = null;
            Enumeration zList = zfile.entries();
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                String relativePath = ze.getName();

                if (!relativePath.startsWith("lib" + File.separator)) {
                    Log.i(LOG_TAG, "不是lib目录，跳过" + relativePath);
                    continue;
                }

                if (ze.isDirectory()) {
                    File folder = new File(tempDir, relativePath);
                    Log.i(LOG_TAG, "正在创建目录"+folder.getAbsolutePath());
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                } else {

                    if (result == null) {
                        result = new HashSet<String>(4);
                    }

                    File targetFile = new File(tempDir, relativePath);
                    Log.i(LOG_TAG, "正在解压so文件"+targetFile.getAbsolutePath());
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();

                    fos = new BufferedOutputStream(new FileOutputStream(targetFile));
                    bis = new BufferedInputStream(zfile.getInputStream(ze));
                    byte[] buffer = new byte[2048];
                    int count = -1;
                    while ((count = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, count);
                        fos.flush();
                    }
                    fos.close();
                    fos = null;
                    bis.close();
                    bis = null;

                    result.add(relativePath.substring(relativePath.lastIndexOf(File.separator) +1));
                }
            }
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            YmIoUtil.close(fos);
            YmIoUtil.close(bis);

            if (zfile != null) {
                try {
                    zfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i(LOG_TAG, "解压so文件结束:"+isSuccess);
        return result;
    }

    /**
     * 复制so到目标文件
     * @param sourceDir
     * @param so
     * @param dest
     * @return
     */
    public static boolean copySo(File sourceDir, String so, String dest) {
        try {
            boolean isSuccess = false;

            if (Build.VERSION.SDK_INT >= 21) {
                String[] abis = Build.SUPPORTED_ABIS;
                if (abis != null) {
                    for (String abi: abis) {
                        Log.i(LOG_TAG, "try supported abi:"+ abi);
                        String name = "lib" + File.separator + abi + File.separator + so;
                        File sourceFile = new File(sourceDir, name);
                        if (sourceFile.exists()) {
                            isSuccess = YmFileUtil.copyFile(sourceFile.getAbsolutePath(), dest + File.separator + so);
                            //api21 64位系统的目录可能有些不同
                            //copyFile(sourceFile.getAbsolutePath(), dest + File.separator +  name);
                            break;
                        }
                    }
                }
            } else {
                Log.i(LOG_TAG, "supported api:"+Build.CPU_ABI+" "+Build.CPU_ABI2);

                String name = Build.CPU_ABI + File.separator + so;
                File sourceFile = new File(sourceDir, name);

                if (!sourceFile.exists() && Build.CPU_ABI2 != null) {
                    name = Build.CPU_ABI2 + File.separator + so;
                    sourceFile = new File(sourceDir, name);

                    if (!sourceFile.exists()) {
                        name = "armeabi" + File.separator + so;
                        sourceFile = new File(sourceDir, name);
                    }
                }
                if (sourceFile.exists()) {
                    isSuccess = YmFileUtil.copyFile(sourceFile.getAbsolutePath(), dest + File.separator + "lib" + File.separator + so);
                }
            }

            if (!isSuccess) {
                Log.e(LOG_TAG, "安装 " + so + " 失败: NO_MATCHING_ABIS");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
