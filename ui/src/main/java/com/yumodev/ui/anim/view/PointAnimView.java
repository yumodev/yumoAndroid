package com.yumodev.ui.anim.view;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yumodev.ui.R;

/**
 * Created by yumodev on 17/12/18.
 */

public class PointAnimView extends View {

    private final int RADIUS = 50;
    private Point mCurPoint;
    private Paint mPaint;

    public PointAnimView(Context context) {
        super(context);
        init();
    }

    public PointAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurPoint == null){
            mCurPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else{
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(mCurPoint.x, mCurPoint.y, RADIUS, mPaint);
    }


    private void startAnimation(){
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointTypeEvaluator(), startPoint, endPoint);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.setDuration(5000);
        animator.start();
    }


    static class PointTypeEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int x = startValue.x + (int)(fraction*(endValue.x - startValue.x));
            int y = startValue.y + (int)(fraction*(endValue.y - startValue.y));

            return new Point(x, y);
        }
    }
}
