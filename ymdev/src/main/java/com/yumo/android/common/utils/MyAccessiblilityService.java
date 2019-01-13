package com.yumo.android.common.utils;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yumo on 2018/7/16.
 */

public class MyAccessiblilityService extends AccessibilityService {
    Map<Integer, Boolean> handleMap = new HashMap<>();

    public List mContentList = new ArrayList();

    /**
     * 当窗口有活动时，会回调此方法
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                if (handleMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handleMap.put(event.getWindowId(), true);
                    }
                }
            }
        }

       // {"安装", "完成", "确定","INSTALL", "DONE"}

        mContentList.add("安装");
        mContentList.add("完成");
        mContentList.add("确定");
        mContentList.add("INSTALL");
        mContentList.add("DONE");
    }

    /**
     * 递归处理节点信息
     * 节点名称为Button，内容为 安装，确定，完成的，模拟点击
     * 节点名称是ScrollView，模拟滑动到底部
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeContent = nodeInfo.getText().toString();
                if (mContentList.contains(nodeContent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            } else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }

}
