package com.yumodev.java.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by yumodev on 17/9/7.
 * 创建线程
 */

public class ThreadCreate {

    /**
     * 定义Thread的子类来创建一个线程。
     */
    static class MyThread extends Thread {
        public MyThread() {
            setName("MyThread");
        }

        @Override
        public void run() {
            System.out.println("当前线程名字为："+Thread.currentThread().getName());
        }
    }

    public static void startThreadByInnerClass(){
        /**
         * new Thread类的实例创建，然后调用start()，启动一个线程。
         */
        new Thread(){
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());

            }
        }.start();
    }

    public static void starThreadByRunnable(){
        /**
         * 使用java.lang.Runnable来创建并启动一个线程
         */
        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());
            }
        }).start();
    }

    public static void startThreadByThreadFactory(){
        /**
         * 通过ThreadFactory类创建一个实例。
         */
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };

        factory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());
            }
        }).start();
    }

    public static void startThreadByDefaultThreadFactory(){
        /**
         * 通过Executors.defaultThreadFactory()获取一个ThreadFactory类，然后创建一个实例。
         */
        Executors.defaultThreadFactory().newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());
            }
        }).start();
    }

    public static void startThreadByExecutors(){
        new Executor(){
            @Override
            public void execute(Runnable runnable) {
                new Thread(runnable).start();
            }
        }.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());
            }
        });
    }

    public static void startThreadByExecutorService(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程名字为："+Thread.currentThread().getName());

            }
        });
        executorService.shutdown();
    }


    public static void main(String[] args) {
        (new MyThread()).start();
        startThreadByInnerClass();
        starThreadByRunnable();
        startThreadByThreadFactory();
        startThreadByDefaultThreadFactory();
        startThreadByExecutors();
        startThreadByExecutorService();
    }
}
