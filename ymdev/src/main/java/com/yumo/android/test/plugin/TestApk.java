package com.yumo.android.test.plugin;

import android.content.Context;
import android.view.View;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yumodev on 17/6/26.
 */

public class TestApk extends YmTestFragment {
    private final String LOG_TAG = "TestApk";
    /**
     * 打印一个APK文件的信息
     */
    public void testPrintApk(){
        ApkUtil.loadApkFromAsserts();
    }

    public void testWebViewFromUnInstallApk(){
        String fileName = YmAdFileUtil.getFileCachePath(YmContext.getAppContext())+ File.separator + "test-debug.apk";
        Class testWebViewClass = ApkUtil.getClassFromUnInstallApk(fileName, "com.yumodev.test.test.TestWebView");
        //showWebView(testWebViewClass, "https://m.baidu.com");
        showWebView(testWebViewClass, "file:///android_asset/test_html/test.html");
    }


    private void showWebView(Class testClass, String url){
        Constructor constructor = null;
        try {
            constructor = testClass.getConstructor(Context.class);
            Object webView = constructor.newInstance(getActivity());

            Method getTestMethod = testClass.getMethod("loadUrl", String.class);
            getTestMethod.setAccessible(true);
            getTestMethod.invoke(webView, url);
            showTestView((View)webView);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void testJni(){
        ApkUtil.testJni();
    }

}
