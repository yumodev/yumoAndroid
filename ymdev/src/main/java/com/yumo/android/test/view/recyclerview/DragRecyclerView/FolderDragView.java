package com.yumo.android.Recyclerview.DragRecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumo.common.util.YmUtil;
import com.yumo.android.R;
import com.yumo.android.Recyclerview.touchlist.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumodev on 17/4/18.
 */

public class FolderDragView extends FrameLayout implements IFolderDragView{
    private final String LOG_TAG = "FolderDragView";

    private List<DragItemBean> mDataList = null;
    private RecyclerView mListView = null;
    private FolderDragView.ItemAdapter mAdapter = null;
    private FolderDragView.GridSpaceItemDecoration mItemSpaceDecoration = null;

    private int mOrientation = Configuration.ORIENTATION_LANDSCAPE;

    public FolderDragView(@NonNull Context context) {
        super(context);
        setupUI();
    }

    public FolderDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public FolderDragView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI(){
        updateDragSize();

        View view = View.inflate(getContext(), R.layout.drag_folder, null);
        addView(view);

        mListView = (RecyclerView) findViewById(R.id.drag_folder_list);
        mListView.setBackgroundResource(R.color.red);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), DragManager.getInstance().getFolderColumn());
        mListView.setLayoutManager(layoutManager);
        mItemSpaceDecoration = new FolderDragView.GridSpaceItemDecoration(DragManager.getInstance().getItemHorSpace(), DragManager.getInstance().getColumn());
        mItemSpaceDecoration.setHorMargin(DragManager.getInstance().getItemHorMargin());
        mListView.addItemDecoration(mItemSpaceDecoration);

        mAdapter = new FolderDragView.ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams)mListView.getLayoutParams();
        lp.height = DragManager.getInstance().getItemHeight() * 2 + DragManager.getInstance().getItemHorSpace() * 4;
        mListView.setLayoutParams(lp);


        DragItemTouchHelper touchHelper = new DragItemTouchHelper(new FolderDragView.DragItemCallBack());
        touchHelper.attachToRecyclerView(mListView);

        mOrientation = getResources().getConfiguration().orientation;

        EditText mTitleEditText = (EditText)findViewById(R.id.drag_item_title);
        mTitleEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    closeFolder();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean openFolder(List<DragItemBean> dataList) {
        mDataList = dataList;
        mAdapter.notifyDataSetChanged();
        setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean closeFolder() {
        setVisibility(View.GONE);
        return true;
    }

    @Override
    public boolean isShowing() {
        return getVisibility() == View.VISIBLE;
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * 计算并更新DragItem 的宽高
     */
    private void updateDragSize(){
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mOrientation != newConfig.orientation){
            mOrientation = newConfig.orientation;

            updateDragSize();
            mListView.removeItemDecoration(mItemSpaceDecoration);
            mItemSpaceDecoration.setSpanCount(DragManager.getInstance().getFolderColumn());
            mListView.addItemDecoration(mItemSpaceDecoration);

            ((GridLayoutManager)mListView.getLayoutManager()).setSpanCount(DragManager.getInstance().getFolderColumn());
        }
    }

    /**
     * 初始化测试数据
     */
    private List<DragItemBean> getTestFolderData(){
        ArrayList<DragItemBean> dataList = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            DragItemBean item = new DragItemBean();
            item.mId = YmUtil.createUUID();
            item.mPosition = i;
            item.mTitle = i+"";
            item.mType = DragItemBean.TYPE_ITEM;

            dataList.add(item);
        }
        return dataList;
    }

    private class ItemAdapter extends RecyclerView.Adapter<FolderDragView.ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public FolderDragView.ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.drag_item, viewGroup, false);
            return new FolderDragView.ItemAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final FolderDragView.ItemAdapter.ItemViewHolder itemViewHolder, final int i) {
            DragItemBean itemBean = mDataList.get(i);
            itemViewHolder.mTextView.setText(itemBean.mTitle);

            if (itemBean.mType == DragItemBean.TYPE_FOLDER){
                DragUtil.createFolderIcon(itemViewHolder.mIconView, getTestFolderData());
            }
        }

        @Override
        public int getItemCount() {
            if (mDataList == null){
                return 0;
            }

            return mDataList.size();
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mDataList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {

        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            ImageView mIconView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.drag_title_id);
                mIconView = (ImageView) itemView.findViewById(R.id.drag_item_icon);

                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams)itemView.getLayoutParams();
                lp.height = DragManager.getInstance().getItemHeight();
                Log.i(LOG_TAG, lp.width+"");
                itemView.setLayoutParams(lp);



                LayoutParams iconLp = (LayoutParams)mIconView.getLayoutParams();
                iconLp.width = DragManager.getInstance().getItemIconWidth();
                iconLp.height = DragManager.getInstance().getItemIconHeight();
                mIconView.setLayoutParams(iconLp);
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

    public class DragItemCallBack extends DragItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d(LOG_TAG, "getMovementFlags");
            int dragFlags = DragItemTouchHelper.UP | DragItemTouchHelper.DOWN | DragItemTouchHelper.LEFT | DragItemTouchHelper.RIGHT;
            int swipeFlags = DragItemTouchHelper.START | DragItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Log.d(LOG_TAG, "onMove");
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }
            // Notify the adapter of the move
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(LOG_TAG, "onSwiped");
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            Log.d(LOG_TAG, "clearView");
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            Log.d(LOG_TAG, "onSelectedChanged");
        }

        /**
         * 长按拖拽
         * @return
         */
        @Override
        public boolean isLongPressDragEnabled() {
            Log.d(LOG_TAG, "isLongPressDragEnabled");
            return super.isLongPressDragEnabled();
        }

        /**
         * 是否需要滑动
         * @return
         */
        @Override
        public boolean isItemViewSwipeEnabled() {
            Log.d(LOG_TAG, "isItemViewSwipeEnabled");
            return false;
        }

        /**
         * 是否可以把当前的ViewHolder 拖动到Target上面呢。
         * @param recyclerView
         * @param current
         * @param target
         * @return
         */
        @Override
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
            return super.canDropOver(recyclerView, current, target);
        }

        /**
         * 获取可以下放的目标。
         * @param selected
         * @param dropTargets
         * @param curX
         * @param curY
         * @return
         */
        @Override
        public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder selected, List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
            return super.chooseDropTarget(selected, dropTargets, curX, curY);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            //Log.i(LOG_TAG, "onChildDraw");
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            //Log.i(LOG_TAG, "onChildDrawOver");
        }
    }
}
