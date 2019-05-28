package com.yumo.android.test.async.eventbus;

import android.os.Bundle;

import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberMethod;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import androidx.annotation.Nullable;

public class TestEventBus extends YmTestFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(TestEvent event){
//        showToastMessage("收到onEvent main消息："+event.msg);
//    }


    @YmMethodTest(name = "测试EventBus的最基本的使用")
    public void sendMainThreadEvent(){
        EventBus.getDefault().post(new TestEvent("test ThreadMode.MAIN"));
    }


    public static class TestEvent{
        private String msg = "";
        public TestEvent(String msg){
            this.msg = msg;
        }

        public String getMsg(){
            return msg;
        }
    }


    /**
     * 打印类信息
     */
    @YmMethodTest(name = "打印类信息")
    public void printClass(){
        Method[] methods = getClass().getDeclaredMethods();
        for (Method method : methods){
            int modifiers = method.getModifiers();
            if ((modifiers & Modifier.PUBLIC) != Modifier.PUBLIC){
                continue;
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1) {
                Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                if (subscribeAnnotation != null) {
                    Class<?> eventType = parameterTypes[0];
                    ThreadMode threadMode = subscribeAnnotation.threadMode();
                    int priority = subscribeAnnotation.priority();
                    boolean sticky = subscribeAnnotation.sticky();

                }
            }
        }
    }
}
