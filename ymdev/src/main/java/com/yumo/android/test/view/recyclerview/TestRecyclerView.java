package com.yumo.android.test.view.recyclerview;

import android.graphics.Color;

import com.yumo.android.test.view.recyclerview.nestedscroll.TestCoordinatorLayout;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedScrollFragment;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedScrollRecyclerView;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedTransRecyclerView;
import com.yumo.android.test.view.recyclerview.touchhelper.GridTouchHelperView;
import com.yumo.android.test.view.recyclerview.touchhelper.ListTouchHelperView;
import com.yumo.android.test.view.recyclerview.touchlist.ListAnimatorView;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/12/15.
 */

public class TestRecyclerView extends YmTestFragment {

    public void testFragment(){
        showToastMessage("test");
    }

    public void testNestScrollRecyclerView(){
        TestNestedScrollRecyclerView recyclerView = new TestNestedScrollRecyclerView(getContext());
        showTestView(recyclerView);
    }


    public void testNestScrollFragment(){
        showFragment(new TestNestedScrollFragment());
    }

    public void testNestScrollView(){
        TestNestedScrollRecyclerView recyclerView = new TestNestedScrollRecyclerView(getContext());
        showTestView(recyclerView);
    }

    public void testCoordinatorLayout(){
        TestCoordinatorLayout testCoordinatorLayout = new TestCoordinatorLayout(getContext());
        showTestView(testCoordinatorLayout);
    }

    public void testNestedTransLayout(){
        TestNestedTransRecyclerView recyclerView = new TestNestedTransRecyclerView(getContext());
        showTestView(recyclerView);
    }


    public void testSimpleDemo(){
        SimpleRecyclerView view = new SimpleRecyclerView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }

    public void testGridRecyclerViewDemo(){
        GridRecyclerView view = new GridRecyclerView(getContext());
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

    public void testArrayItemAdapter(){
        ArrayRecyclerView view = new ArrayRecyclerView(getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setupUI();
        showTestView(view);
    }


//    public void testGridDragView(){
//        GridDragView view = new com.yumo.android.Recyclerview.DragRecyclerView.GridDragView(getContext());
//        view.setBackgroundColor(Color.GRAY);
//        showTestView(view);
//    }
}
