package com.yumodev.glide3;

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
        list.add(new YmPackageInfo("glide", "com.yumodev.glide3"));
        return list;
    }
}
