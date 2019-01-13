package com.yumo.ui.web;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.yumo.common.log.Log;

/**
 * Created by yumodev on 16/11/22.
 * 抽象的网页类
 */

public class AbstractWebView extends FrameLayout implements IBrowserView {
    private final String LOG_TAG = "AbstractBrowserView:"+Log.LIB_TAG;
    protected String mTabId = "";
    protected String mUrl = "";
    protected String mTitle = "";
    protected IBrowserClientListener mClientListener = null;
    protected int mProgress = 0;
    public AbstractWebView(Context context) {
        super(context);
    }

    @Override
    public void setTabId(String tabId) {
        mTabId = tabId;
    }

    @Override
    public void loadUrl(String url) {
        mUrl = url;
    }

    @Override
    public void reload() {

    }

    @Override
    public void stopLoad() {

    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public byte[] getIcon() {
        return null;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public int getProgress() {
        return mProgress;
    }

    @Override
    public String getTabId() {
        return mTabId;
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public boolean canPrev() {
        return false;
    }

    @Override
    public void back() {

    }

    @Override
    public void prev() {

    }

    @Override
    public void destroy() {

    }

    public void setBrowserClientListener(IBrowserClientListener listener){
        Log.i(LOG_TAG, "setBrowserClientListener");
        mClientListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(LOG_TAG, "onAttachedToWindow");
        show();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(LOG_TAG, "onDetachedToWindow");
        hide();
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        mClientListener.setTopBarVisible(View.VISIBLE, false);
    }
}
