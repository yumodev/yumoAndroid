package com.yumo.ui.list;

/**
 * Created by guo on 17/12/10.
 */

public class RecyclerLastItemInfo<T> {
    public T mItem;
    public int mLastPosition;
    public int mTop;

    public static RecyclerLastItemInfo newInstance( Object item, int lastPosition, int top){
        RecyclerLastItemInfo data = new RecyclerLastItemInfo<>();
        data.mItem = item;
        data.mLastPosition = lastPosition;
        data.mTop = top;

        return data;
    }
}
