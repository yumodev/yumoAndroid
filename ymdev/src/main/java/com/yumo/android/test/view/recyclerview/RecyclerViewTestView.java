package com.yumo.android.test.view.recyclerview;

import android.graphics.Color;
import android.view.View;

import com.yumo.android.R;
import com.yumo.android.Recyclerview.TestRecyclerViewOperation;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedScrollRecyclerView;
import com.yumo.demo.view.YmTestFragment;
import com.yumo.android.test.view.recyclerview.touchhelper.GridTouchHelperView;
import com.yumo.android.test.view.recyclerview.touchhelper.ListTouchHelperView;

/**
 * Created by yumodev on 15/11/22.
 */
public class RecyclerViewTestView extends YmTestFragment {

    public void testSimpleDemo(){
        SimpleRecyclerView view = new SimpleRecyclerView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }

    public void testGridRecyclerViewDemo(){
        GridRecyclerView view = new GridRecyclerView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }

    public void testItemAnimator(){
        ListAnimatorView view = new ListAnimatorView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }

    public void testItemTouchHelperList(){
        ListTouchHelperView view = new ListTouchHelperView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);

    }

    public void testItemTouchHelperGrid(){
        GridTouchHelperView view = new GridTouchHelperView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }

    public void testStaggeredGridRecyclerView(){

    }

    public void testArrayItemAdapter(){
        ArrayRecyclerView view = new ArrayRecyclerView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }


    public void testRecyclerViewOperation(){
        TestRecyclerViewOperation view = new TestRecyclerViewOperation(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }


    public void testGridDragView(){
       com.yumo.android.Recyclerview.DragRecyclerView.GridDragView view = new com.yumo.android.Recyclerview.DragRecyclerView.GridDragView(getContext());
        view.setBackgroundColor(Color.GRAY);
        showTestView(view);
    }

    public void testNestScrollRecyclerView(){
        TestNestedScrollRecyclerView view = (TestNestedScrollRecyclerView) View.inflate(getContext(), R.layout.nested_layout, null);
        view.setBackgroundColor(Color.GRAY);
        showTestView(view);
    }
}
