/**
 * ThreadPool.java
 * yumodev
 * 2015-1-10
 */
package com.yumodev.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * yumodev
 */
public class ThreadPool {

    public static void main(String[] args){
        ThreadPool pool = new ThreadPool();
        //创建单个执行的线程池
        pool.createSingleThread1();

        //创建固定个数的线程池
        //pool.createFixThread();

        //创建缓存线程池。
        //pool.createCachedThread();

        //创建定时线程池。
        //pool.scheduledThread();

        //创建有返回结果的线程
       // pool.action1();
    }

    /**
     * 单个执行的线程。
     * yumodev
     * void
     * 2015-1-10
     */
    public void createSingleThread() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            MyThread myThread = new MyThread();
            pool.execute(myThread);
        }

        pool.shutdown();
    }

    public void createSingleThread1(){
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 运行");
            }
        });
    }


    /**
     * 创建个数固定的线程池
     * yumodev
     * void
     * 2015-1-10
     */
    public void createFixThread() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            MyThread myThread = new MyThread();
            pool.execute(myThread);
        }
        pool.shutdown();
    }

    /**
     * 创建缓存线程池
     * yumodev
     * void
     * 2015-3-11
     */
    public void createCachedThread() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            MyThread myThread = new MyThread();
            pool.execute(myThread);
        }
        pool.shutdown();
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 运行");
        }

    }

    class myRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 运行");
        }

    }

    /**
     * 创建定时执行的线程池。
     * yumodev
     * void
     * 2015-3-11
     */
    public void scheduledThread() {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new myRunnable(), 5000, 5000, TimeUnit.MILLISECONDS);
    }

    /********************创建有返回结果的任务**************************/
    public void action1() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        //Future<Integer>实例
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();

        Random random = new Random();
        for (int n = 0; n < 10; n++) {
            int num = random.nextInt(10);
            MyCallable mycallable = new MyCallable(num);
            //将任务提交给执行者
            Future<Integer> result = pool.submit(mycallable);
            resultList.add(result);
        }

        do {
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> result = resultList.get(i);
                System.out.printf("action1: num:%d isdone:%s \r\n", i, result.isDone());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (pool.getCompletedTaskCount() < resultList.size());

        //当所有的任务都完成的时候打印结果
        for (int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            try {
                Integer num = result.get();
                System.out.printf("action1: num:%d result:%d \r\n", i, num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //关闭线程池
        pool.shutdown();
    }

    /**
     * yumodev
     * 定义一个任务，实现阶乘
     */
    class MyCallable implements Callable<Integer> {

        private Integer num = 0;

        public MyCallable(Integer num) {
            this.num = num;
        }

        /* (non-Javadoc)
         * @see java.util.concurrent.Callable#call()
         * 实现阶乘
         */
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " callable call begin num:" + num);
            if (num <= 1) return num;
            int result = 1;
            for (int n = 2; n < num; n++) {
                result = result * n;
                Thread.sleep(200);
            }
            System.out.println(Thread.currentThread().getName() + " callable call result:" + result);
            return result;
        }

    }
}
