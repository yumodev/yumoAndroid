package com.yumodev.java.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by yumo on 9/25/16.
 */
public class TestJoin {

    static class Sleeper extends Thread{
        private int duration = 0;
        public Sleeper(String name, int time){
            super(name);
            duration = time;
            start();
        }

        @Override
        public void run() {
            try {
                sleep(duration);
            }catch (InterruptedException e){
               // e.printStackTrace();
                System.out.println(getName() + " was interrupted, isInterrupt() is "+ isInterrupted());
            }

            System.out.println(getName() + " isAwakened");
        }
    }

    static class Joiner extends Thread{

        Sleeper sleeper;
        public Joiner(String name, Sleeper sleeper){
            super(name);
            this.sleeper = sleeper;
            start();
        }

        public void run(){
            try {
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " join completed");
        }

    }

    public static void main(String[] args){
        Sleeper sleepy = new Sleeper("sleepy", 1500);
        Sleeper grumpy = new Sleeper("grumpy", 1500);

        Joiner dopey = new Joiner("dopey", sleepy);
        Joiner doc = new Joiner("doc", grumpy);

        grumpy.interrupt();
    }

    /**
     * Created by yumodev on 9/25/16.
     */
    public static class TextUncaughtException {

        public static class TestRunnable implements Runnable{

            @Override
            public void run() {
                Thread t = Thread.currentThread();
                System.out.println(t.getName()+" test RuntimeException");
                throw new RuntimeException();
            }
        }

        public static class MyDefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName()+" has uncaughtExceptionHandler ");
            }
        }

        public static class HandlerThreadFactory implements ThreadFactory {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new MyDefaultUncaughtExceptionHandler());
                System.out.println("factory:"+t.getName() + " set unCaughtExceptionHandler");
                return t;
            }
        }

        public static void main(String[] args){
            // 设置默认的
            //Thread.setDefaultUncaughtExceptionHandler(new MyDefaultUncaughtExceptionHandler());
            //new Thread(new TestRunnable()).start();

            ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
            exec.execute(new TestRunnable());

        }
    }
}
