package com.yumodev.ymdev1;

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
        list.add(new YmPackageInfo("provider", "com.yumodev.ymdev1.provider"));
        list.add(new YmPackageInfo("web", "com.yumodev.ymdev1.web"));
        list.add(new YmPackageInfo("net", "com.yumodev.ymdev1.net"));
        return list;
    }
}
