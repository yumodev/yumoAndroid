package com.yumo.android.test.media.canvas.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 *
 * 绘制点
 * drawPoint(x,y,paint)绘制点
 * drawPoint(float[] pts,paint) 批量画点
 * drawPints(float[] pts, offset, count paint)
 * paint.setStrokeWidth(width) 设置点的大小
 *
 * paint.setStrokeCap()设置点的形状，ROUND:圆头， BUTT:平头， SQUARE:方头
 */

public class TestDrawPointView extends View {

    public TestDrawPointView(Context context){
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        //设置绘制颜色
        paint.setColor(Color.RED);
        //设置填充类型
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        paint.setStrokeWidth(50);
        //画板背景。 定义白色画板背景
        canvas.drawColor(Color.WHITE);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(0,30, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(100,30, paint);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(200,30, paint);
    }
}
