package com.yumodev.ui.recyclerview.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

/**
 * Created by yumodev on 18/1/26.
 */

public interface YmDragSwipeListener {
    void moveItem(int fromPos, int toPos);
    void removeItem(int pos);
    void selectView(RecyclerView.ViewHolder viewHolder, int actionState);
    void clearView(RecyclerView.ViewHolder viewHolder);
    void onChildDraw(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive);
}
