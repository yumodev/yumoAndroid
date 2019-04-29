package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.graphics.Bitmap;

/**
 * Created by yumodev on 17/4/18.
 */

public class DragItemBean implements IDragItemBean{
    public static int TYPE_FOLDER = 0;
    public static int TYPE_ITEM = 1;
    public String mId;
    public String mTitle;
    public int mType;
    public int mParentId;
    public int mPosition;

    @Override
    public Bitmap getItemBitmap() {
        return null;
    }
}
