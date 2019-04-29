package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.ImageView;

import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmHandlerThreadUtil;
import com.yumo.common.media.YmImageUtil;
import com.yumodev.ui.R;

import java.util.List;

/**
 * Created by yumodev on 17/4/18.
 * 拖拽合并管理类
 */

public class DragUtil {

    /**
     * 获取四张图片在文件夹上所画的区域
     *
     * @param width  被画图片的高
     * @param height 被画图片的宽
     * @return
     */
    public static Rect[][] getDrawIconArea(int width,
                                           int height) {
        DragManager sets = DragManager.getInstance();

        int margin = sets.getFolderDrawIconMargin();
        int paddingLeft = sets.getFolderDrawIconPadding();
        int paddingRight = sets.getFolderDrawIconPadding();
        int paddingTop = sets.getFolderDrawIconPadding();
        int paddingBottom = sets.getFolderDrawIconPadding();

        int rowCount = sets.getFolderIconRowCount();
        int columnCount = sets.getFolderIconColumnCount();

        int startLeft = paddingLeft;
        int startTop = paddingTop;
        int smallIconW = (width - (margin + paddingLeft + paddingRight))
                / columnCount;
        int smallIconH = (height - (margin + paddingTop + paddingBottom))
                / rowCount;

        Rect[][] rest = new Rect[rowCount][columnCount];
        int left = 0, top = 0, right = 0, bottom = 0;

        top = startTop;

        for (int i = 0; i < rest.length; i++) {
            Rect[] rect = rest[i];

            left = startLeft;
            bottom = top + smallIconH;
            for (int j = 0; j < rect.length; j++) {

                right = left + smallIconW;
                rest[i][j] = new Rect(left, top, right, bottom);
                left = right + margin;
            }
            top = bottom + margin;

        }
        return rest;
    }

    /**
     * 转换二维数组到一维数组
     *
     * @param rects
     * @return
     */
    private static Rect[] getArrayFrom2Array(Rect[][] rects, int resultLen) {
        Rect[] recs = new Rect[resultLen];
        int index = 0;
        for (int i = 0; i < rects.length; i++) {
            Rect[] r = rects[i];
            for (int j = 0; j < r.length; j++) {
                recs[index++] = r[j];
            }
        }
        return recs;
    }

    private static Bitmap drawFolderIcon(Bitmap[] bitmaps, Bitmap mainBgBitmap,
                                        int width, int height, Rect[] rects) {
        try{
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(mainBgBitmap, null, new Rect(0, 0, width, height),
                    null);

            for (int i = 0; i < bitmaps.length; i++) {
                if (bitmaps[i] == null)
                    continue;
                canvas.drawBitmap(bitmaps[i], null, rects[i], null);
            }
            return bitmap;
        }catch(OutOfMemoryError e){
            e.printStackTrace();
        }

        return mainBgBitmap;
    }


    public static void createFolderIcon(final ImageView view, final List<DragItemBean> dataList){
        YmHandlerThreadUtil.getInstance().post(new Runnable() {
            @Override
            public void run() {
                int iconWidth = DragManager.getInstance().getItemIconWidth();
                int iconHeight = DragManager.getInstance().getItemIconHeight();

                Rect[][] mRect = getDrawIconArea(iconWidth, iconHeight);

                int totalCount = DragManager.getInstance().getFolderIconColumnCount() * DragManager.getInstance().getFolderIconRowCount();
                Rect[] rest = getArrayFrom2Array(mRect, totalCount);

                Bitmap[] folderChunkBmp = new Bitmap[totalCount];

                for (int i = 0; i < dataList.size() && i < totalCount; i++) {
                    folderChunkBmp[i] = YmImageUtil.createBitmapFromColor(YmContext.getInstance().getColor(R.color.blue), iconWidth, iconHeight);
                }

                // 生成文件夹图片
                Bitmap bgBmp = YmImageUtil.createBitmapFromColor(YmContext.getInstance().getColor(R.color.red), iconWidth, iconHeight);

                final Bitmap bitmap = drawFolderIcon(folderChunkBmp, bgBmp, iconHeight, iconHeight, rest);
                view.setImageBitmap(bitmap);
            }
        });
    }
}
