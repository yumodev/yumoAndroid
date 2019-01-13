package com.yumodev.java.thread.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yumodev on 9/25/16.
 */
public class TestThreadLocal {

    public static class ThreadLocalVariableHolder{

        private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return (new Random()).nextInt(1000);
            }
        };


        public static void increment(){
            value.set(value.get()+1);
        }

        public static int get(){
            return value.get();
        }
    }

    public static class Accessor implements Runnable{

        String mName = "";
        public Accessor(String name){
            mName = name;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                ThreadLocalVariableHolder.increment();
                System.out.println(this);
                Thread.yield();
            }
        }

        @Override
        public String toString(){
            return mName +" "+ThreadLocalVariableHolder.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++){
            exec.execute(new Accessor(i+""));
        }
        TimeUnit.MILLISECONDS.sleep(5);
        exec.shutdownNow();

    }
}
