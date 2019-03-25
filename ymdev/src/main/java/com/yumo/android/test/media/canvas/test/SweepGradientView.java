package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

/**
 * Created by yumodev on 17/12/22.
 * 扫描渐变
 */

public class SweepGradientView extends View {

    public SweepGradientView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Shader shader = new SweepGradient(300, 300, Color.RED, Color.BLUE);

        Paint paint = new Paint();
        paint.setShader(shader);

        canvas.drawCircle(300, 300, 200, paint);



        Shader shader1 = new SweepGradient(600, 600, Color.RED, Color.BLUE);
        paint.setShader(shader1);
        canvas.drawCircle(600, 600, 200, paint);
    }
}
