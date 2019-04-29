package com.yumodev.ui.recyclerview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/2/11.
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mHorSpace;
    private int mVerSpace;
    private int mHorMargin = 0;
    private int mTopMargin = 0;
    private int mBottomMargin = 0;
    private int mSpanCount = 0;

    public GridSpaceItemDecoration(int space, int spanCount) {
        super();
        mHorSpace = space;
        mVerSpace = space;
        mBottomMargin = space;
        mTopMargin = space;
        mHorMargin = space;
        mSpanCount = spanCount;
    }

    public void setSpanCount(int spanCount){
        mSpanCount = spanCount;
    }

    public void setHorSpace(int space){
        mHorSpace = space;
    }

    public void setVerSpace(int space){
        mVerSpace = space;
    }

    public void setHorMargin(int space){
        mHorMargin = space;
    }

    public void setTopMargin(int space){
        mTopMargin = space;
    }

    public void setBottomMargin(int BottomMargin){

    }

    public boolean isLastRow(int position, int count, int spanCount){
        if (position / spanCount == count / spanCount ){
            return true;
        }
        return false;
    }

    public int averageSpace(){
        return (mHorMargin * 2 + mHorSpace * (mSpanCount - 1)) / mSpanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = mVerSpace;
        int position = parent.getChildAdapterPosition(view);

        //第一排
        if (position < mSpanCount){
            outRect.top = mTopMargin;
        }else if (isLastRow(position, state.getItemCount(), mSpanCount)){
            outRect.bottom = mBottomMargin;
        }

        int column = position % mSpanCount;
        int averageSpace = averageSpace();

        outRect.left = mHorMargin - (averageSpace - mHorSpace) * column;
        outRect.right = averageSpace - mHorMargin +(averageSpace - mHorSpace) * column;

        Log.d(Define.INSTANCE.getLOG_TAG(), "position:"+position + " outRect:"+outRect.toString());
    }
}
