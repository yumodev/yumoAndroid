package com.yumo.android.test.media.canvas.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 */

public class TestDrawRectView extends View {

    public TestDrawRectView(Context context){
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

        canvas.drawRect(0,0,100,100, paint);

        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(0, 100, 100, 200);
        canvas.drawRect(rect, paint);

        paint.setColor(Color.BLUE);
        RectF rectF = new RectF(0, 200, 100, 300);
        canvas.drawRect(rectF, paint);

        canvas.drawRoundRect(100, 400, 300, 600, 50, 50, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRoundRect(100, 650, 300, 850, 50, 50, paint);
        paint.setColor(Color.DKGRAY);
        canvas.drawRoundRect(100, 900, 300, 1150, 50, 50, paint);
    }
}
