package com.yumo.android.test.plugin;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by yumodev on 17/6/26.
 * 测试APK相关的信息
 */
public class ApkUtilTest {
    private static final String LOG_TAG = "ApkUtilTest";
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void loadApkFromAsserts() throws Exception {
        ApkUtil.loadApkFromAsserts();
    }

    @Test
    public void printApkClass() throws Exception {
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "test-debug.apk";
        ApkUtil.printApkClass(fileName);
    }

    @Test
    public void printNdkApkClass() throws Exception {
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "ndk-debug.apk";
        ApkUtil.printApkClass(fileName);
    }

    @Test
    public void printApkInfo() throws Exception {
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "test-debug.apk";
        ApkUtil.printApkInfo(fileName);
    }

    @Test
    public void printGetClassFromUnInstallApk() throws Exception {
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "test-debug.apk";
        ApkUtil.testClass(ApkUtil.getClassFromUnInstallApk(fileName, "com.yumodev.test.test.Test"));
    }

    @Test
    public void printTestJni() throws Exception {
       ApkUtil.testJni();
    }
}