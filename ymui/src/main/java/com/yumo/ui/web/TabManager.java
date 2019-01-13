package com.yumo.ui.web;

import android.content.Context;
import android.widget.FrameLayout;

import com.yumo.common.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yudev on 16/11/22.
 * 窗口管理器
 */

public class TabManager {
    private final String LOG_TAG = "TabManager "+ Log.LIB_TAG;

    /**
     * 在当前窗口中创建标签的位置
     * 在所有窗口中创建窗口的位置。
     * 在开始和结束位置，
     * 在当前标签页的签名和后面
     */
    public enum PagePos {
        BEGIN,END,BEFORE,NEXT
    }

    private List<WebTab> mTabList = new ArrayList<>();

    private IBrowserFactory mBrowserViewFactory = null;
    private Context mContext = null;
    private IBrowserClientListener mClientListener = null;

    private static TabManager mInstance = null;

    private FrameLayout mTabContainer = null;
    private int mForegroundIndex = 0;

    public static TabManager getInstance(){
        if (mInstance == null){
            synchronized (TabManager.class){
                if (mInstance == null){
                    mInstance = new TabManager();
                }
            }
        }

        return mInstance;
    }

    /**
     * 销毁单例类
     */
    public void release(){
        mInstance = null;
    }

    private TabManager(){}

    /**
     * 初始化
     * @param context
     * @param clientListener
     */
    public void init(Context context, IBrowserClientListener clientListener){
        Log.i(LOG_TAG, "init");
        mContext = context;
        mClientListener = clientListener;
    }

    /**
     * 传入创建标签页工厂
     * @param browserFactory
     */
    public void setBrowserFactory(IBrowserFactory browserFactory){
        mBrowserViewFactory = browserFactory;
    }

    /**
     * 传入窗口的父容器
     * @param frameLayout
     */
    public void setTabContainer(FrameLayout frameLayout){
        mTabContainer = frameLayout;
    }

    /**
     * 新建一个窗口
     * @param url
     * @param background 是否后台创建
     * @return
     */
    public boolean createTab(String url, boolean background){
        Log.d(LOG_TAG, "CreateBrowserView url:"+url+" background:"+background);
        WebTab tab = new WebTab();
        IBrowserView tabView =  mBrowserViewFactory.createPageView(mContext, url);
        tabView.setBrowserClientListener(mClientListener);
        tab.setTabId(UUID.randomUUID().toString()).addTabView(tabView);
        mTabList.add(tab);
        mClientListener.tabChange();
        if (!background){
            showForegroundTab(tab, tabView, mTabList.size()-1);
        }
        return true;
    }

    /**
     * 在窗口中新建一个页面
     * @param url
     * @param pos
     * @return
     */
    public boolean openPage(String url, int pos){
        Log.d(LOG_TAG, "openPage url:"+url+" pos:"+pos);
        WebTab tab = mTabList.get(getForegroundTabIndex());
        if (tab != null){
            IBrowserView tabView = mBrowserViewFactory.createPageView(mContext, url);
            tabView.setBrowserClientListener(mClientListener);
            tabView.setTabId(tab.getTabId());
            tab.addTabView(tabView, pos);
            showForegroundTab(tab, tabView, getForegroundTabIndex());
            return true;
        }
        return true;
    }

    /**
     * 前台显示一个窗口
     * @param index
     */
    public void showForeGroundTab(int index){
        WebTab tab = getTab(index);
        IBrowserView tabView = tab.getCurrentView();
        showForegroundTab(tab, tabView, index);
    }

    private void showForegroundTab(WebTab tab, IBrowserView tabView, int pos){
        Log.d(LOG_TAG, "showForegroundTab:"+tabView.getClass().getSimpleName());
        mTabContainer.removeAllViews();
        mTabContainer.addView(tabView.getView());
        tab.setCurrentView(tabView);
        mForegroundIndex = pos;
    }

    /**
     *  获取前台窗口的索引
     * @return
     */
    public int getForegroundTabIndex(){
        return mForegroundIndex;
    }

    public int getForegroundPageIndex(){
        return getCurrentTab().getCurrentIndex();
    }

    /**
     * 获取窗口的页面
     * @return
     */
    public IBrowserView getCurrentPage(){
        return mTabList.get(mForegroundIndex).getCurrentView();
    }

    /**
     * 获取当前窗口
     * @return
     */
    public WebTab getCurrentTab(){
        return mTabList.get(mForegroundIndex);
    }

    /**
     * 获取窗口数量
     * @return
     */
    public int getTabCount(){
        return mTabList.size();
    }

    /**
     * 获取窗口数量
     * @return
     */
    public int getPageCount(){
        return getCurrentTab().getCount();
    }

    /**
     * 通过一个索引获取窗口
     * @param i
     * @return
     */
    public WebTab getTab(int i){
        return mTabList.get(i);
    }

    /**
     * 所有窗口是否可以返回
     * @return
     */
    public boolean canBack(){
        return getCurrentTab().canBack();
    }

    /**
     * 当前窗口是否可以前进
     * @return
     */
    public boolean canPrev(){
        return getCurrentTab().canPrev();
    }

    /**
     * 后退
     */
    public void back(){
        Log.i(LOG_TAG, "back");
        if (getCurrentPage().canBack()){
            getCurrentPage().back();
        }else{
            WebTab tab = getCurrentTab();
            showForegroundTab(tab, tab.getBrowserView(tab.getCurrentIndex()-1), mForegroundIndex);
        }
    }

    /**
     * 前进
     */
    public void prev(){
        if (getCurrentPage().canPrev()){
            getCurrentPage().prev();
        }else{
            WebTab tab = getCurrentTab();
            showForegroundTab(tab, tab.getBrowserView(tab.getCurrentIndex() + 1), mForegroundIndex);
        }
    }

    /**
     * 通过索引关闭窗口
     * @param index
     */
    public void closeTab(int index){
        if (index == 0){
            showForeGroundTab(index + 1);
        }else{
            showForeGroundTab(index - 1);
        }

        mTabList.remove(index);
        mClientListener.tabChange();
    }

    /**
     * 移除一个窗口
     * @param index
     */
    public void removeTab(int index){
        mTabList.get(index).destory();
        mTabList.remove(index);
        mClientListener.tabChange();
    }

    /**
     * 关闭所有的窗口
     */
    public void closeAll(){
        for (int i = 0; i < mTabList.size(); i++){
            mTabList.get(i).destory();
        }
        mTabList.clear();
        mClientListener.tabChange();
    }
}
