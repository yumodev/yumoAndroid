package com.yumodev.ui.scroll.testview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by yumodev on 17/12/21.
 */

public class TestScrollView3 extends LinearLayout {
    public TestScrollView3(Context context) {
        super(context);
        setupUI();
    }

    public TestScrollView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestScrollView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setupUI(){
        Button btn = new Button(getContext());
        btn.setText("moving");

        addView(btn);

        btn.setOnTouchListener(new OnTouchListener() {
            int lastX;
            int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x =
                        (int)event.getRawX();
                int y = (int)event.getRawY();
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:{
                        lastX = (int)event.getRawX();
                        lastY = (int)event.getRawX();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        int delatX = lastX - x;
                        int delatY = lastY - y;
                        v.setTranslationX(x);
                        v.setTranslationY(y);

                        lastY = y;
                        lastX = x;

                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        break;
                    }
                }
                return true;
            }
        });
    }


}
