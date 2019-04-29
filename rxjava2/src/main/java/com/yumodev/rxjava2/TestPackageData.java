package com.yumodev.rxjava2;

import com.yumo.demo.entry.YmPackageInfo;
import com.yumo.demo.listener.IGetPackageData;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yumodev on 17/9/11.
 */

public class TestPackageData implements IGetPackageData {
    @Override
    public List<YmPackageInfo> getPackageList() {
        List<YmPackageInfo> list = new LinkedList<>();
        list.add(new YmPackageInfo("rxjava2", "com.yumodev.rxjava2"));
        return list;
    }
}
