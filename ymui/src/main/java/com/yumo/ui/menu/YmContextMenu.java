package com.yumo.ui.menu;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/11/25.
 */

public class YmContextMenu implements YmMenu {
    protected List<YmMenuItem> mItemList = null;

    public YmContextMenu(){
        mItemList = new ArrayList<>();
    }


    @Override
    public boolean add(YmMenuItem item) {
        if (mItemList.contains(item)){
            return false;
        }
        mItemList.add(item);
        return true;
    }

    @Override
    public void removeItem(int id) {

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean hasVisibleItems() {
        return false;
    }

    @Override
    public YmMenuItem findItem(int id) {
        for (YmMenuItem item : mItemList){
            if (item.getItemId() == id){
                return item;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return mItemList.size();
    }

    @Override
    public YmMenuItem getItem(int index) {
        return mItemList.get(index);
    }

    @Override
    public void close() {

    }
}
