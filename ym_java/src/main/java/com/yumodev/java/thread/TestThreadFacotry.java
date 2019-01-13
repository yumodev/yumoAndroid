package com.yumodev.java.thread;


import junit.framework.TestCase;

import java.util.concurrent.ThreadFactory;

/**
 * Created by yumodev on 7/18/16.
 * 线程工厂的测试使用方法。
 */
public class TestThreadFacotry extends TestCase{

    public void testThreadFacory(){
        MyThreadFactory facotry = new MyThreadFactory();
        for (int n = 0; n < 10; n++){
            facotry.newThread(new MyRunnable()).start();
        }
    }

    public class MyThreadFactory implements ThreadFactory{
        int mThreadCount = 0;

        @Override
        public Thread newThread(Runnable r) {
            String name = "thread_"+ ++mThreadCount;
            return new Thread(r, name);
        }
    }

    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("TreadName"+Thread.currentThread().getName());
        }
    }
}
