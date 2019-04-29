package com.yumodev.ui.recyclerview.grid;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/4/10.
 * 一个最基本的GridView。
 */

public class TestGridView extends FrameLayout {
    private final String LOG_TAG = "GridDragView";

    private List<Item> mDataList = null;
    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private GridSpaceItemDecoration mItemSpaceDecoration = null;

    private int mOrientation = Configuration.ORIENTATION_LANDSCAPE;


    public TestGridView(@NonNull Context context) {
        super(context);
        setupUI();
    }

    public TestGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestGridView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI(){
        initTestData();

        mListView = new RecyclerView(getContext());
        addView(mListView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getColumnNum());
        mListView.setLayoutManager(layoutManager);
        mItemSpaceDecoration = new GridSpaceItemDecoration(60, getColumnNum());
        mItemSpaceDecoration.setHorMargin(0);
        mListView.addItemDecoration(mItemSpaceDecoration);

        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        mOrientation = getResources().getConfiguration().orientation;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mOrientation != newConfig.orientation){
            mOrientation = newConfig.orientation;
            mListView.removeItemDecoration(mItemSpaceDecoration);
            mItemSpaceDecoration.setSpanCount(getColumnNum());
            mListView.addItemDecoration(mItemSpaceDecoration);

            ((GridLayoutManager)mListView.getLayoutManager()).setSpanCount(getColumnNum());
        }
    }

    /**
     * 初始化测试数据
     */
    private void initTestData(){
        if (mDataList == null){
            mDataList = new ArrayList<>();
        }

        for (int i = 0; i < 10; i++){
            Item item = new Item();
            item.mTitle = "Item"+i;
            mDataList.add(item);
        }
    }

    private int getColumnNum(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            return 4;
        }else{
            return 6;
        }
    }


    class Item{
        public String mTitle;
    }

    private class ItemAdapter extends RecyclerView.Adapter<TestGridView.ItemAdapter.ItemViewHolder> {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public TestGridView.ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recyclerview_grid_item_text, viewGroup, false);

            return new TestGridView.ItemAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TestGridView.ItemAdapter.ItemViewHolder itemViewHolder, final int i) {
            itemViewHolder.mTextView.setText(mDataList.get(i).mTitle);
        }

        @Override
        public int getItemCount() {
            if (mDataList == null){
                return 0;
            }

            return mDataList.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.drag_title_id);
            }

        }
    }

    class GridSpaceItemDecoration extends RecyclerView.ItemDecoration{
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

            Log.d(LOG_TAG, "position:"+position + " outRect:"+outRect.toString());
        }
    }



}
