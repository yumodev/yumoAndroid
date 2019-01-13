package com.yumo.ui.menu;

import android.support.annotation.StringRes;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by yumodev on 17/11/25.
 * YmMenu接口从android.view.menu类中摘取
 */

public interface YmMenu {
    public boolean add(YmMenuItem item);
    public void removeItem(int id);
    public void clear();
    public boolean hasVisibleItems();
    public YmMenuItem findItem(int id);
    public int size();
    public YmMenuItem getItem(int index);
    public void close();

}
