package com.yumo.android.test.view.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/10/15.
 *
 * 使用了ClipData复制TextView的内容
 DragShadowBuilder的作用是创建一个Drag或Drop操作时的图片.
 DragEvent的几个动作，ACTION_DRAG_STARTED–表示view开始drag，返回true表示这个view的drag和drop动作可用。
 ACTION_DRAG_ENTERED–表示有drag的view进入到当前view的区域
 ACTION_DRAG_LOCATION–表示drag的view在当前的view的区域中，改变了它的位置
 ACTION_DRAG_EXITED–表示drag的view离开了当前的view的区域
 ACTION_DROP–真正的drop操作，就是你的drag动作松开手指
 ACTION_DRAG_ENDED–drag和drop操作都结束。
 */
public class DragTestView extends YmTestFragment {
    private static final String LOG_TAG = "Drag";
    public void testDragTextView1(){
        showTestView(new DragTestView1(getContext()));
    }

    public static class DragTestView1 extends LinearLayout{

        public DragTestView1(@NonNull Context context) {
            super(context);
            initView();
        }

        private void initView(){
            for (int i = 0; i < 3; i++){
                Button btn = new Button(getContext());
                btn.setText(String.valueOf(i));
                addView(btn);

                btn.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipData data = ClipData.newPlainText("value", Button.class.cast(v).getText());
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                            v.startDrag(data, new View.DragShadowBuilder(v), null, 0);
                        }else{
                            v.startDragAndDrop(data, new View.DragShadowBuilder(v), null, 0);
                        }
                        return false;
                    }
                });

                btn.setOnDragListener(new View.OnDragListener(){
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        switch (event.getAction()) {
                            case DragEvent.ACTION_DRAG_STARTED:
                                return true;

                            case DragEvent.ACTION_DRAG_ENTERED:
                                v.setBackgroundColor(Color.YELLOW);
                                return true;

                            case DragEvent.ACTION_DRAG_LOCATION:
                                return true;

                            case DragEvent.ACTION_DRAG_EXITED:
                                v.setBackgroundColor(Color.RED);
                                return true;

                            case DragEvent.ACTION_DROP:
                                v.setBackgroundColor(Color.GREEN);
                                //得到拖动的值
                                int dragVal = Integer.parseInt(event.getClipData().getItemAt(0).getText().toString());
                                //得到textview的值
                                int viewVal = Integer.parseInt(((Button) v).getText().toString());
                                ((Button) v).setText("" + (dragVal + viewVal));
                                return true;

                            case DragEvent.ACTION_DRAG_ENDED:
                                v.setBackgroundColor(Color.WHITE);
                                return true;
                        }

                        return false;
                    }
                });
            }
        }
    }
}
