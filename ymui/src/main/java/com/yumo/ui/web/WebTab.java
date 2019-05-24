package com.yumo.ui.web;

import androidx.annotation.NonNull;

import com.yumo.common.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 16/11/24.
 * 网页窗口
 */

public class WebTab {
    private final String LOG_TAG = "WebTab "+ Log.LIB_TAG;
    private String mTabId = "";
    private List<IBrowserView> mTabList = null;
    private int mCurrentIndex = 0;

    WebTab(){
        mTabList = new ArrayList<>();
    }


    WebTab addTabView(IBrowserView tabView){
        mTabList.add(tabView);
        return this;
    }

    WebTab addTabView(IBrowserView tabView, int pos){
        mTabList.add(pos, tabView);
        return this;
    }

    WebTab setTabId(@NonNull String tabId){
        mTabId = tabId;
        return this;
    }

    String getTabId(){
        return mTabId;
    }

    int getCurrentIndex(){
        return mCurrentIndex;
    }

    void setCurrentView(IBrowserView tabView){
        mCurrentIndex = mTabList.indexOf(tabView);
    }

    IBrowserView getCurrentView(){
        return mTabList.get(mCurrentIndex);
    }

    public String getTitle(){
        IBrowserView browserView = mTabList.get(mCurrentIndex);
        return browserView.getTitle();
    }

    String getUrl(){
        IBrowserView browserView = mTabList.get(mCurrentIndex);
        return browserView.getUrl();
    }

    byte[] getIcon(){
        IBrowserView browserView = mTabList.get(mCurrentIndex);
        return browserView.getIcon();
    }

    boolean canBack(){
        Log.d(LOG_TAG, "canBack");
        if (getCurrentView().canBack() || mCurrentIndex > 0){
            return true;
        }
        return false;
    }

    boolean canPrev(){
        if (getCurrentView().canPrev() || mCurrentIndex < mTabList.size() - 1){
            return true;
        }
        return false;
    }

    IBrowserView getBrowserView(int index){
        return mTabList.get(index);
    }

    public void destory(){
        for (int i = 0; i < mTabList.size(); i++){
            mTabList.get(i).destroy();
        }
    }

    /**
     * 当前窗口内页面的数量
     * @return
     */
    int getCount(){
        return mTabList.size();
    }
}
