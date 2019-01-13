package com.yumodev.java.thread;

import junit.framework.TestCase;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yumo on 7/18/16.
 * 线程池
 */
public class TestPool extends TestCase {
    private final String LOG_TAG = "TestPool";

    private ThreadPoolExecutor mExecutor;

    public void testCachedThreadPool(){
        mExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++){
            executorRunnable(new MyRunnable());
        }
        mExecutor.shutdown();
    }

    public void testFixedThreadPool(){
        mExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++){
            executorRunnable(new MyRunnable());
        }
        mExecutor.shutdown();
    }

    public void testSingleThreadPool(){
        mExecutor = (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++){
            executorRunnable(new MyRunnable());
        }
        //mExecutor.shutdown();
    }

    private void executorRunnable(Runnable runnable){
        mExecutor.execute(runnable);
        StringBuilder sb = new StringBuilder();
        sb.append("poolsize:"+mExecutor.getPoolSize());
        sb.append("activeCount:"+mExecutor.getActiveCount());
        sb.append("completed:"+mExecutor.getCompletedTaskCount());
        System.out.println(sb.toString());

    }

    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
