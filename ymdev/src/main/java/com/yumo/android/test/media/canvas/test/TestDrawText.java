package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 *
 * drawText(String text, float x, float y, Paint paint)
 * Paint.setTextSize(textSize) 设置字体大小
 */

public class TestDrawText extends View {

    public TestDrawText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(36);
        paint.setColor(Color.RED);

        canvas.drawText("hellow wold", 200, 200, paint);
    }
}
