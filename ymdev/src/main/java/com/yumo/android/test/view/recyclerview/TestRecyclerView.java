package com.yumo.android.test.view.recyclerview;

import android.graphics.Color;

import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedScrollRecyclerView;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedTransRecyclerView;
import com.yumo.android.test.view.recyclerview.touchlist.ListAnimatorView;
import com.yumo.demo.view.YmTestFragment;
import com.yumo.android.Recyclerview.DragRecyclerView.GridDragView;
import com.yumo.android.Recyclerview.hormenu.HorMenuRecyclerView;
import com.yumo.android.Recyclerview.swipeleft.SwipeLeftLayout;
import com.yumo.android.Recyclerview.test.TouchHelperRecyclerView;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestCoordinatorLayout;
import com.yumo.android.test.view.recyclerview.nestedscroll.TestNestedScrollFragment;
import com.yumo.android.Recyclerview.arraylist.ArrayRecyclerView;
import com.yumo.android.Recyclerview.test.GridRecyclerView;
import com.yumo.android.Recyclerview.test.SimpleRecyclerView;
import com.yumo.android.Recyclerview.touchlist.GridTouchHelperView;
import com.yumo.android.Recyclerview.touchlist.ListTouchHelperView;

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


    public void testGridDragView(){
        GridDragView view = new GridDragView(getContext());
        view.setBackgroundColor(Color.GRAY);
        showTestView(view);
    }

    public void testTouchGridRecclerView(){
        TouchHelperRecyclerView touchHelperRecyclerView = new TouchHelperRecyclerView(getContext());
        touchHelperRecyclerView.setGrid(true);
        touchHelperRecyclerView.setupUI();
        showTestView(touchHelperRecyclerView);
    }

    public void testTouchListRecclerView(){
        TouchHelperRecyclerView touchHelperRecyclerView = new TouchHelperRecyclerView(getContext());
        touchHelperRecyclerView.setGrid(false);
        touchHelperRecyclerView.setupUI();
        showTestView(touchHelperRecyclerView);
    }


    public void testSwipeLeftRecclerView(){
        SwipeLeftLayout touchHelperRecyclerView = new SwipeLeftLayout(getContext());
        touchHelperRecyclerView.setGrid(false);
        touchHelperRecyclerView.setupUI();
        showTestView(touchHelperRecyclerView);
    }

    public void testHorMenuRecyclerView(){
        HorMenuRecyclerView recyclerView = new HorMenuRecyclerView(getContext());
        recyclerView.setBackgroundColor(Color.GRAY);
        recyclerView.setupUI();
        showTestView(recyclerView);
    }
}
