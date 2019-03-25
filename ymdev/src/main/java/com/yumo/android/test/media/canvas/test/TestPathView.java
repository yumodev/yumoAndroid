package com.yumo.android.test.media.canvas.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 * http://hencoder.com/ui-1-1/
 * 绘制一个桃心
 */

public class TestPathView extends View {

    Paint mPaint = new Paint();
    Path mPath  = new Path();
    public TestPathView(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path1 = new Path();
        path1.addArc(200, 200, 400, 400, -225, 225);
        path1.arcTo(400, 200, 600, 400, -180, 225, false);
        path1.lineTo(400, 542);
        canvas.drawPath(path1, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        Path path2 = new Path();
        path2.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        path2.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线

        canvas.drawPath(path2, mPaint);
    }
}
