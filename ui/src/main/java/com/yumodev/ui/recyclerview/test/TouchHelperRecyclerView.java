package com.yumodev.ui.recyclerview.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;
import com.yumodev.ui.recyclerview.helper.YmDragSwipeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumodev on 15/11/22.
 * 一个普通的GrideRecyclerView Demo
 */
public class TouchHelperRecyclerView extends FrameLayout {
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private List<String> mDataList = null;
    private ItemTouchHelper mItemTouchHelper = null;
    private boolean mIsGrid = false;

    public TouchHelperRecyclerView(Context context) {
        super(context);
    }

    public void setGrid(boolean isGrid){
        mIsGrid = isGrid;
    }

    public void setupUI() {
        initTestData();

        mListView = new RecyclerView(getContext());
        if (mIsGrid){
            mListView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }else{
            mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
        mItemTouchHelper.attachToRecyclerView(mListView);

        addView(mListView);
    }

    private void initTestData() {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        }

        for (int n = 0; n < 50; n++) {
            mDataList.add("test " + n);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements YmDragSwipeListener {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recycler_simple_array_one, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
            itemViewHolder.mTextView.setText(mDataList.get(i));
        }

        @Override
        public int getItemCount() {
            if (mDataList == null) {
                return 0;
            }

            return mDataList.size();
        }

        @Override
        public void moveItem(int fromPos, int toPos) {
            Collections.swap(mDataList, fromPos, toPos);
            notifyItemMoved(fromPos, toPos);
        }

        @Override
        public void removeItem(int pos) {
            mDataList.remove(pos);
            notifyItemRemoved(pos);
        }

        @Override
        public void selectView(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundResource(R.color.gray);
            }
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder) {
            viewHolder.itemView.setBackgroundResource(R.color.transparent);
            viewHolder.itemView.setAlpha(1);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                float value = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(value);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.title);
            }
        }
    }


    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback{
        YmDragSwipeListener mDragSwipeListener;
        public ItemTouchHelperCallback(YmDragSwipeListener listener) {
            mDragSwipeListener = listener;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d(LOG_TAG, "getMovementFlags");
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            if (mDragSwipeListener != null){
                mDragSwipeListener.moveItem(from, to);
            }

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (mDragSwipeListener != null){
                mDragSwipeListener.removeItem(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (mDragSwipeListener != null){
                mDragSwipeListener.selectView(viewHolder, actionState);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (mDragSwipeListener != null){
                mDragSwipeListener.clearView(viewHolder);
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (mDragSwipeListener != null && actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                mDragSwipeListener.onChildDraw(c, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }

}
