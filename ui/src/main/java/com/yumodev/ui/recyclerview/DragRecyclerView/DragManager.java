package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by yumodev on 17/4/18.
 * 拖拽管理类
 */

public class DragManager {
    private static DragManager mInstance = new DragManager();

    private Context mContext;
    private int mColumn[] = {4,4};
    private int mFolderColumn[] = {4, 4};
    private int mFolderShowRows = 2;

    private int mItemHeight = 0;
    private int mItemWidth = 0;
    private int mItemIconHeight = 120;
    private int mItemIconWidth = 120;
    private int mItemHorMargin = 0;
    private int mItemHorSpace = 0;

    private int mFolderCellMarginLeft = 0;
    private int mFolderCellMarginTop = 0;
    private int mFolderDrawIconMargin = 0;
    private int mFolderDrawIconPadding = 8;
    private int mFolderIconRowCount = 2;
    private int mFolderIconColumnCount = 2;
    private int mBitmapRoundPix = 20;

    private DragManager(){}

    public static DragManager getInstance() {
        return mInstance;
    }

    public void init(Context context){
        mContext = context;
    }

    /**
     * 设置横竖屏显示的列数
     * @param landColumn
     * @param portColumn
     */
    public void setColumn(int portColumn, int landColumn){
        mColumn = new int[2];
        mColumn[0] = landColumn;
        mColumn[1] = portColumn;
    }


    public int getColumn(){
        if (isLand(mContext)){
            return mColumn[0];
        }
        return mColumn[1];
    }

    /**
     * 设置横竖屏显示的列数
     * @param landColumn
     * @param portColumn
     */
    public void setFolderColumn(int portColumn, int landColumn){
        mFolderColumn = new int[2];
        mFolderColumn[0] = landColumn;
        mFolderColumn[1] = portColumn;
    }


    public int getFolderColumn(){
        if (isLand(mContext)){
            return mFolderColumn[0];
        }
        return mFolderColumn[1];
    }

    public void setFolderShowRows(int folderShowRows){
        mFolderShowRows = folderShowRows;
    }

    public int getmFolderShowRows(){
        return mFolderShowRows;
    }

    public int getItemHeight() {
        return mItemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.mItemHeight = itemHeight;
    }

    public int getItemWidth() {
        return mItemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.mItemWidth = itemWidth;
    }

    public int getItemIconHeight() {
        return mItemIconHeight;
    }

    public void setItemIconHeight(int itemIconHeight) {
        this.mItemIconHeight = itemIconHeight;
    }

    public int getItemIconWidth() {
        return mItemIconWidth;
    }

    public void setItemIconWidth(int itemIconWidth) {
        this.mItemIconWidth = itemIconWidth;
    }

    public int getItemHorMargin() {
        return mItemHorMargin;
    }

    public void setItemHorMargin(int itemHorMargin) {
        this.mItemHorMargin = itemHorMargin;
    }

    public int getItemHorSpace() {
        return mItemHorSpace;
    }

    public void setItemHorSpace(int itemHorSpace) {
        this.mItemHorSpace = itemHorSpace;
    }

    public int getFolderCellMarginLeft() {
        return mFolderCellMarginLeft;
    }

    public void setFolderCellMarginLeft(int folderCellMarginLeft) {
        this.mFolderCellMarginLeft = folderCellMarginLeft;
    }

    public int getFolderCellMarginTop() {
        return mFolderCellMarginTop;
    }

    public void setFolderCellMarginTop(int folderCellMarginTop) {
        this.mFolderCellMarginTop = folderCellMarginTop;
    }

    public int getFolderDrawIconMargin() {
        return mFolderDrawIconMargin;
    }

    public void setFolderDrawIconMargin(int folderDrawIconMargin) {
        this.mFolderDrawIconMargin = folderDrawIconMargin;
    }

    public int getFolderDrawIconPadding() {
        return mFolderDrawIconPadding;
    }

    public void setFolderDrawIconPadding(int folderDrawIconPadding) {
        this.mFolderDrawIconPadding = folderDrawIconPadding;
    }

    public int getFolderIconRowCount() {
        return mFolderIconRowCount;
    }

    public void setFolderIconRowCount(int folderIconRowCount) {
        this.mFolderIconRowCount = folderIconRowCount;
    }

    public int getFolderIconColumnCount() {
        return mFolderIconColumnCount;
    }

    public void setFolderIconColumnCount(int folderIconColumnCount) {
        this.mFolderIconColumnCount = folderIconColumnCount;
    }

    public int getBitmapRoundPix() {
        return mBitmapRoundPix;
    }

    public void setBitmapRoundPix(int bitmapRoundPix) {
        this.mBitmapRoundPix = bitmapRoundPix;
    }


    private boolean isLand(Context context){
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }else{
            return false;
        }
    }


}
