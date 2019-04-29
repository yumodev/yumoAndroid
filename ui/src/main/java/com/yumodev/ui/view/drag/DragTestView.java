package com.yumodev.ui.view.drag;


import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.view.drag.drawerlayout.TestDragLayout;
import com.yumodev.ui.view.drag.draggrid.DragGridView;
import com.yumodev.ui.view.drag.test.DragTestView1;
import com.yumodev.ui.view.drag.test.DragTestView2;
import com.yumodev.ui.view.drag.test.DragTestView3;
import com.yumodev.ui.view.drag.test.DragTestView4;
import com.yumodev.ui.view.drag.test.DragTestView5;

/**
 * Created by yumodev on 17/10/15.
 *
 * 使用了ClipData复制TextView的内容
 *
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

    public void testDragTextView2(){
        showTestView(new DragTestView2(getContext()));
    }

    public void testDragTextView3(){
        showTestView(new DragTestView3(getContext()));
    }

    public void testDragTextView4(){
        showTestView(new DragTestView4(getContext()));
    }

    public void testDragTextView5(){
        showTestView(new DragTestView5(getContext()));
    }

    public void testDragGridView(){
        showTestView(new DragGridView(getContext()));
    }


    public void testDragLayout(){
        showTestView(new TestDragLayout(getContext()));
    }
}
