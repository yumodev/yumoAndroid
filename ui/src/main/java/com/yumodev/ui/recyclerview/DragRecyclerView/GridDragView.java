package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.common.android.YmDisplayUtil;
import com.yumo.common.android.YmResUtil;
import com.yumo.common.util.YmUtil;
import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.touchlist.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumodev on 17/4/10.
 */

public class GridDragView extends FrameLayout {
    private final String LOG_TAG = "GridDragView";

    private List<DragItemBean> mDataList = null;
    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private GridSpaceItemDecoration mItemSpaceDecoration = null;

    private IFolderDragView mFolderDragView = null;

    private int mOrientation = Configuration.ORIENTATION_LANDSCAPE;

    public GridDragView(@NonNull Context context) {
        super(context);
        setupUI();
    }

    public GridDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public GridDragView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI(){
        DragManager.getInstance().init(getContext().getApplicationContext());
        initDragSettings();
        updateDragSize();
        initTestData();

        mListView = new RecyclerView(getContext());
        addView(mListView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), DragManager.getInstance().getColumn());
        mListView.setLayoutManager(layoutManager);
        mItemSpaceDecoration = new GridSpaceItemDecoration(DragManager.getInstance().getItemHorSpace(), DragManager.getInstance().getColumn());
        mItemSpaceDecoration.setHorMargin(DragManager.getInstance().getItemHorMargin());
        mListView.addItemDecoration(mItemSpaceDecoration);

        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        DragItemTouchHelper touchHelper = new DragItemTouchHelper(new DragItemCallBack());
        touchHelper.attachToRecyclerView(mListView);

        mOrientation = getResources().getConfiguration().orientation;

        initFolder();
    }

    private void initFolder(){
        FolderDragView dragView = new FolderDragView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        int leftMargin = DragManager.getInstance().getItemHorMargin();
        int rightMargin = DragManager.getInstance().getItemHorMargin();
        lp.setMargins(leftMargin,  0, rightMargin, 0);
        addView(dragView, lp);

        dragView.setVisibility(View.GONE);
        mFolderDragView = dragView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mFolderDragView.isShowing()){
                mFolderDragView.closeFolder();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化设置的参数
     */
    private void initDragSettings(){
        DragManager.getInstance().setColumn(4, 6);
        DragManager.getInstance().setFolderColumn(4, 6);
        DragManager.getInstance().setItemHorSpace(getResources().getDimensionPixelOffset(R.dimen.drag_item_hor_space));
        DragManager.getInstance().setItemHorMargin(getResources().getDimensionPixelOffset(R.dimen.drag_item_hor_margin));

        //folderIcon
        DragManager.getInstance().setFolderDrawIconPadding((int) YmResUtil.dipToPx(getContext(), 5));
        DragManager.getInstance().setFolderDrawIconMargin((int) YmResUtil.dipToPx(getContext(), 5));
        DragManager.getInstance().setFolderIconColumnCount(2);
        DragManager.getInstance().setFolderIconRowCount(2);
    }

    /**
     * 计算并更新DragItem 的宽高
     */
    private void updateDragSize(){
        // 获取屏幕宽度
        int width = YmDisplayUtil.getScreenWidth(getContext());
        int horMargin = DragManager.getInstance().getItemHorMargin();
        int itemSpace = DragManager.getInstance().getItemHorSpace();
        int column = DragManager.getInstance().getColumn();
        int itemWidth = (width - horMargin * 2 - itemSpace * (column - 1)) / column;
        int itemHeight = (int) (itemWidth * 1.2);
        int iconWidth = itemWidth - getContext().getResources().getDimensionPixelSize(R.dimen.drag_icon_margin) * 2;
        DragManager.getInstance().setItemWidth(itemWidth);
        DragManager.getInstance().setItemHeight(itemHeight);
        DragManager.getInstance().setItemIconWidth(iconWidth);
        DragManager.getInstance().setItemIconHeight(iconWidth);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mOrientation != newConfig.orientation){
            mOrientation = newConfig.orientation;

            updateDragSize();
            mListView.removeItemDecoration(mItemSpaceDecoration);
            mItemSpaceDecoration.setSpanCount(DragManager.getInstance().getColumn());
            mListView.addItemDecoration(mItemSpaceDecoration);

            ((GridLayoutManager)mListView.getLayoutManager()).setSpanCount(DragManager.getInstance().getColumn());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                return handlerActionDown(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean handlerActionDown(MotionEvent ev){
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (mFolderDragView.isShowing()){
            if (!isFolderView(x, y)){
                mFolderDragView.closeFolder();
                return true;
            }
        }

        return false;
    }

    private boolean isFolderView(int x, int y){
        Rect rect = new Rect();
        mFolderDragView.getView().getHitRect(rect);
        return rect.contains(x, y);
    }

    /**
     * 初始化测试数据
     */
    private void initTestData(){
        if (mDataList == null){
            mDataList = new ArrayList<>();
        }

        for (int i = 0; i < 10; i++){
            DragItemBean item = new DragItemBean();
            item.mId = YmUtil.createUUID();
            item.mPosition = i;
            item.mTitle = i+"";
            if (i == 0){
                item.mType = DragItemBean.TYPE_FOLDER;
            }else{
                item.mType = DragItemBean.TYPE_ITEM;
            }

            mDataList.add(item);
        }
    }

    /**
     * 初始化测试数据
     */
    private List<DragItemBean> getTestFolderData(){
        ArrayList<DragItemBean> dataList = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            DragItemBean item = new DragItemBean();
            item.mId = YmUtil.createUUID();
            item.mPosition = i;
            item.mTitle = i+"";
            item.mType = DragItemBean.TYPE_ITEM;

            dataList.add(item);
        }
        return dataList;
    }

    private class ItemAdapter extends RecyclerView.Adapter<GridDragView.ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public GridDragView.ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.drag_item, viewGroup, false);
            return new GridDragView.ItemAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GridDragView.ItemAdapter.ItemViewHolder itemViewHolder, final int i) {
            final DragItemBean itemBean = mDataList.get(i);
            itemViewHolder.mTextView.setText(itemBean.mTitle);

            if (itemBean.mType == DragItemBean.TYPE_FOLDER){
                DragUtil.createFolderIcon(itemViewHolder.mIconView, getTestFolderData());
            }

            itemViewHolder.itemView.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (itemBean.mType == DragItemBean.TYPE_FOLDER){
                        mFolderDragView.openFolder(getTestFolderData());
                    }else{
                        Toast.makeText(getContext(), itemBean.mTitle + " isClick ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
