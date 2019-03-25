package com.yumo.android.test.media.canvas.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 * 绘制椭圆
 * drawOval(left,top,right,bottom,paint)
 */

public class TestOvalView extends View {

    public TestOvalView(Context context){
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
        paint.setStrokeWidth(5);
        //画板背景。 定义白色画板背景
        canvas.drawColor(Color.WHITE);

        //昨上角绘制一个原型。
        canvas.drawOval(0, 0, 100, 200, paint);

        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0, 200, 200, 300);
        canvas.drawOval(rectF, paint);
    }
}
