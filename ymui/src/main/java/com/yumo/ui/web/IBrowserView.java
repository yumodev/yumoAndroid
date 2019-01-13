package com.yumo.ui.web;

import android.view.View;

/**
 * Created by yumodev on 16/11/22.
 * 一个具体的网页
 */

public interface IBrowserView {
    void setTabId(String tabId);
    void loadUrl(String url);
    void reload();
    void stopLoad();
    String getTitle();
    String getUrl();
    byte[] getIcon();
    View getView();
    int getProgress();

    String getTabId();
    boolean canBack();
    boolean canPrev();
    void back();
    void prev();

    void setBrowserClientListener(IBrowserClientListener listener);
    void destroy();

    void show();
    void hide();
}
