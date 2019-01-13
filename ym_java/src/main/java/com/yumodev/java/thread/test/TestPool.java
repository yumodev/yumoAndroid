package com.yumodev.java.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by yumodev  on 9/29/16.
 */
public class TestPool {

    public static ThreadFactory mThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            System.out.println(thread.getName()+" create");
            return thread;
        }
    };

    public static void testSingleThreadExecutor(){

        ExecutorService exec = Executors.newSingleThreadExecutor();

        for (int i = 1; i < 10; i++){
            final String name = " runnable "+ i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+name);
                }
            });
        }

    }

    public static void testSingleThreadExecutorFactory(){

        ExecutorService exec = Executors.newSingleThreadExecutor(mThreadFactory);

        for (int i = 1; i < 10; i++){
            final String name = " runnable "+ i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+name);
                }
            });
        }

    }

    public static void testFixedThreadExecutorFactory(){

        ExecutorService exec = Executors.newFixedThreadPool(3, mThreadFactory);

        for (int i = 1; i < 10; i++){
            final String name = " runnable "+ i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+name);
                }
            });
        }

    }

    public static void testCachedThreadExecutorFactory(){

        ExecutorService exec = Executors.newCachedThreadPool(mThreadFactory);

        for (int i = 1; i < 10; i++){
            final String name = " runnable "+ i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+name);
                }
            });
        }

    }


    public static void testScheduledThreadExecutorFactory(){

        ExecutorService exec = Executors.newScheduledThreadPool(3, mThreadFactory);

        for (int i = 1; i < 10; i++){
            final String name = " runnable "+ i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+name);
                }
            });
        }

    }

    public static void main(String[] args){
        //testSingleThreadExecutor();
        //testSingleThreadExecutorFactory();
        //testFixedThreadExecutorFactory();
        //testCachedThreadExecutorFactory();
        testScheduledThreadExecutorFactory();
    }
}
