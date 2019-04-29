package com.yumodev.ui

import com.yumo.demo.entry.YmPackageInfo
import com.yumo.demo.listener.IGetPackageData

import java.util.LinkedList

/**
 * Created by yumodev on 17/9/11.
 */

class TestPackageData : IGetPackageData {
    override fun getPackageList(): List<YmPackageInfo> {
        val list = LinkedList<YmPackageInfo>()
        list.add(YmPackageInfo("recyclerview", "com.yumodev.ui.recyclerview"))
        return list
    }
}
