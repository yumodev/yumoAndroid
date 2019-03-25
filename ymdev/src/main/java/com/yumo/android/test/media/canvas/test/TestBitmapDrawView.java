package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.yumo.android.R;

/**
 * Created by yumodev on 17/12/22.
 */

public class TestBitmapDrawView extends View {
    public TestBitmapDrawView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baidu_logo);
        canvas.drawBitmap(bitmap, 200, 100, paint);

        //canvas.drawBitmap(bitmap, new Rect(0,0,100,100), new Rect(100, 100, 400, 400), paint);
    }
}
