package com.yumodev.ui.recyclerview.swipeleft;

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
import com.yumodev.ui.recyclerview.helper.YmItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumodev on 18/1/26.
 *
 * 通过ItemHelper来实现侧滑菜单的难度还是比较大的。
 * 目前暂时放弃。
 */

public class SwipeLeftLayout extends FrameLayout {

    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private RecyclerView mListView = null;
    private SwipeLeftLayout.ItemAdapter mAdapter = null;
    private List<String> mDataList = null;
    private SwipeItemTouchHelper mItemTouchHelper = null;
    private boolean mIsGrid = false;

    public SwipeLeftLayout(Context context) {
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

        mItemTouchHelper = new SwipeItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
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

    private class ItemAdapter extends RecyclerView.Adapter<SwipeLeftLayout.ItemAdapter.ItemViewHolder> implements YmDragSwipeListener {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public SwipeLeftLayout.ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.swipe_left, viewGroup, false);
            return new SwipeLeftLayout.ItemAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SwipeLeftLayout.ItemAdapter.ItemViewHolder itemViewHolder, final int i) {
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
                //viewHolder.itemView.setBackgroundResource(R.color.gray);
            }
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder) {
            Log.i(LOG_TAG, "clearView");
//            viewHolder.itemView.setBackgroundResource(R.color.transparent);
//            viewHolder.itemView.setAlpha(1);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            ItemViewHolder itemViewHolder = (ItemViewHolder)viewHolder;
            Log.i(LOG_TAG, "onChildDraw:"+dX+" "+ itemViewHolder.getSwipeBtnWidth());
            int swipeBtnWidth = itemViewHolder.getSwipeBtnWidth();
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                if (Math.abs(dX) < swipeBtnWidth){
                    itemViewHolder.mSwipeContentView.setTranslationX(dX);
                }else{
                    dX = -swipeBtnWidth;
                    itemViewHolder.mSwipeContentView.setTranslationX(-swipeBtnWidth);
                }
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            View mSwipeView;
            View mSwipeContentView;
            View mSwipeBtnView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.title);
                mSwipeView = itemView.findViewById(R.id.swipe);
                mSwipeContentView = itemView.findViewById(R.id.swipe_content);
                mSwipeBtnView = itemView.findViewById(R.id.swipe_btn);
            }

            public int getSwipeBtnWidth(){
                return mSwipeBtnView.getMeasuredWidth();
            }
        }
    }


    private class ItemTouchHelperCallback extends SwipeItemTouchHelper.Callback{
        protected YmDragSwipeListener mDragSwipeListener;
        public ItemTouchHelperCallback(YmDragSwipeListener listener) {
            mDragSwipeListener = listener;
        }


        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d(LOG_TAG, "getMovementFlags");
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = 0;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = 0;
                final int swipeFlags = ItemTouchHelper.START;
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
//            if (mDragSwipeListener != null){
//                mDragSwipeListener.removeItem(viewHolder.getAdapterPosition());
//            }
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
            //super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            Log.i(LOG_TAG, "onChildDraw:"+actionState+" "+isCurrentlyActive+" "+dX);
            if (mDragSwipeListener != null && actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                //mDragSwipeListener.onChildDraw(c, viewHolder, dX, dY, actionState, isCurrentlyActive);
                ItemAdapter.ItemViewHolder itemViewHolder = (ItemAdapter.ItemViewHolder)viewHolder;
                Log.i(LOG_TAG, "onChildDraw:"+dX+" "+ itemViewHolder.getSwipeBtnWidth());
                int swipeBtnWidth = itemViewHolder.getSwipeBtnWidth();
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    if (Math.abs(dX) < swipeBtnWidth){
                        itemViewHolder.mSwipeContentView.setTranslationX(dX);
                    }else{
                        dX = -swipeBtnWidth;
                        itemViewHolder.mSwipeContentView.setTranslationX(dX);
                    }
                }
            }else{
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }
}
