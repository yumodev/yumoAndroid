package com.yumodev.ui.view;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.view.test.TestGestureDetectorView;
import com.yumodev.ui.view.test.TestMultitouchView;
import com.yumodev.ui.view.test.TestTouchEvent;
import com.yumodev.ui.view.test.TestVelocityTrackerView;

/**
 * Created by yumodev on 17/12/19.
 *
 * ViewGroup.dispatchTouchEvent:
 * true:事件会分发给当前的View并由DispatchTouchEvent方法进行消费，同时事件会停止向下传递
 * false: 返回其父控件或者Activity的onTouchEvent进行处理。
 * super.dispatchTouchEvent,事件会自动的分发给当前View的onInterceptTouchEvent方法
 *
 * onInterceptTouchEvent :
 * true: 拦截事件，交由自身的onTouchEvent消费事件。
 * false: 放行事件，交给子View的dispatchTouchEvent来开始这个事件的分发。
 * super.onInterceptTouchEvent(ev):事件默认会被拦截，交给当前的View的onTouchEvent进行处理
 *
 * onTouchEvent:控件消费触摸控件
 * true:接受并消费该事件
 * false: 事件从该View向上传递，并由上层的onTouchEvent来接收，如果上传的onTouchEvent依然返回false，该事件将消失，将接收不到下一次事件。
 *
 * requestDisallowInterceptTouchEvent：
 * 用于禁止父View拦截此次触摸事件中后续的TouchEvent,
 * 之后所有的TouchEvent将不会传递到父View和onInterceptTouchEvent,而直接传递到该View中。
 *
 *
 * 实现事件监听有setOnTouchListener和实现重写3个TouchEvent方法。
 * 先处理onTouch后TouchEvent，如果onTouch返回true，onTouchEvent就不会再调用了。
 *
 *
 * MotionEvent
 * getX, getY：相对于其父视图的左上点的坐标
 * getRawX, getRawY: 获得x,y值的绝对坐标
 */

public class TestView extends YmTestFragment {

    public void testTouchEvent(){
        showTestView(new TestTouchEvent(getContext()));
    }

    public void testMultitouchvent(){
        showTestView(new TestMultitouchView(getContext()));
    }


    public void testVelocityView(){
        showTestView(new TestVelocityTrackerView(getContext()));
    }


    public void testGestureDetectorView(){
        showTestView(new TestGestureDetectorView(getContext()));
    }
}
