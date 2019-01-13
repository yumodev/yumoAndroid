package com.yumodev.java.thread.test;

import java.util.concurrent.*;

/**
 * Created by yumodev on 9/29/16.
 */
public class TestFutureCall {


    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName()+"running");
            Thread.sleep(3000);
            return 1000;
        }
    }

    public static void executeTask(){
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<Integer> result = exec.submit(new Task());
        exec.shutdown();

        System.out.println(Thread.currentThread().getName()+" running");
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void executeFutureTask(){
        ExecutorService exec = Executors.newCachedThreadPool();
        FutureTask<Integer> result = new FutureTask<Integer>(new Task());
        exec.submit(result);
        exec.shutdown();

        System.out.println(Thread.currentThread().getName()+" running");

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        executeTask();
        //executeFutureTask();
    }

}
