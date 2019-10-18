package com.yumo.android.test;

import android.util.Log;

import com.yumo.demo.listener.IGetPackageData;
import com.yumo.demo.entry.YmPackageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 3/18/16.
 */
public class TestClass implements IGetPackageData {
    @Override
    public List<YmPackageInfo> getPackageList() {
        Log.d("TestClass", "getPackageList");
        List<YmPackageInfo> dataList = new ArrayList<>();
        dataList.add(new YmPackageInfo("Kotlin", "com.yumo.android.test.kotlin"));
        dataList.add(new YmPackageInfo("media", "com.yumo.android.test.media"));
        dataList.add(new YmPackageInfo("anim", "com.yumo.android.test.anim"));
        dataList.add(new YmPackageInfo("插件","com.yumo.android.test.plugin"));
        dataList.add(new YmPackageInfo("API","com.yumo.android.test.api"));
        dataList.add(new YmPackageInfo("Activity","com.yumo.android.test.activity"));
        dataList.add(new YmPackageInfo("Fragment","com.yumo.android.fragment"));
        dataList.add(new YmPackageInfo("Service","com.yumo.android.test.service"));
        dataList.add(new YmPackageInfo("ContentProvider","com.yumo.android.test.provider"));
        dataList.add(new YmPackageInfo("View", "com.yumo.android.test.view"));
        dataList.add(new YmPackageInfo("Architecture", "com.yumo.android.test.architecture"));
        dataList.add(new YmPackageInfo("RecyclerView", "com.yumo.android.test.view.recyclerview"));
        dataList.add(new YmPackageInfo("net","com.yumo.android.test.net"));
        dataList.add(new YmPackageInfo("web", "com.yumo.android.test.web"));
        dataList.add(new YmPackageInfo("db", "com.yumo.android.test.database"));
        dataList.add(new YmPackageInfo("system", "com.yumo.android.test.sys"));
        dataList.add(new YmPackageInfo("widget", "com.yumo.android.widgettest"));
        dataList.add(new YmPackageInfo("async", "com.yumo.android.test.async"));
        dataList.add(new YmPackageInfo("utils", "com.yumo.android.common.utils"));
        dataList.add(new YmPackageInfo("io", "com.yumo.android.test.io"));
        dataList.add(new YmPackageInfo("java","com.yumo.android.test.java"));
        dataList.add(new YmPackageInfo("immersiveMode", "com.yumo.android.test.view.ImmersiveMode"));
        dataList.add(new YmPackageInfo("dialog", "com.yumo.android.test.dialog"));
        dataList.add(new YmPackageInfo("二维码", "com.yumo.android.test.qrcode"));

        return dataList;
    }
}