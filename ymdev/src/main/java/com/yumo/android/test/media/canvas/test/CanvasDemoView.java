package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 */

public class CanvasDemoView extends View {
    /**
     * @param context
     */
    public CanvasDemoView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //首先定义画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //画板背景。 定义白色画板背景
        canvas.drawColor(Color.WHITE);

        //直线。
        canvas.drawLine(0, 0, 200, 200, paint);

        canvas.drawPoint(30, 30, paint);

        canvas.drawPoints(new float[]{30, 30, 40, 40, 50, 50}, paint);

        //巨型
        canvas.drawRect(0, 0, 200, 200, paint);

        Rect rect = new Rect(10, 10, 60, 60);
        canvas.drawRect(rect, paint);

        RectF rect1 = new RectF(80, 80, 160, 160);
        canvas.drawRoundRect(rect1, 10, 20, paint);

        //圆形。
        canvas.drawCircle(200, 200, 100, paint);

        canvas.drawArc(new RectF(400, 400, 500, 500), 0, 230, true, paint);

        canvas.drawOval(new RectF(300, 10, 500, 200), paint);

        Path path = new Path();
        path.moveTo(0, 250);
        path.lineTo(200, 450);
        path.lineTo(450, 20);
        path.close();
        canvas.drawPath(path, paint);

        //绘制文本
        canvas.drawText("绘制文本", 300, 0, paint);

        //圆形路径文本
        Path pathCircle = new Path();
        pathCircle.addCircle(400, 100, 100, Path.Direction.CCW);
        canvas.drawTextOnPath("pathCircle", pathCircle, 10, 20, paint);
    }
}
