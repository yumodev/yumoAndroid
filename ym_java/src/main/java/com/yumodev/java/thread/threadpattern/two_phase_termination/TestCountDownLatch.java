package com.yumodev.java.thread.threadpattern.two_phase_termination;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestCountDownLatch {

    public static class MyTask implements Runnable{
        private final CountDownLatch doneLatch;
        private final int context;
        private static final Random random = new Random(314159);

        public MyTask(CountDownLatch doneLatch, int context){
            this.doneLatch = doneLatch;
            this.context = context;
        }

        @Override
        public void run() {
            doTask();
            doneLatch.countDown();
        }

        protected void doTask(){
            String name = Thread.currentThread().getName();
            System.out.println(name+" :MyTask:Begin:context "+context);
            try {
                Thread.sleep(random.nextInt(3000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(name+" :MyTask:End:context "+context);
        }
    }

    public static void main(String[] args){
        System.out.println("Begin:");
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch doneLatch = new CountDownLatch(10);

        try {
            for (int i = 0; i < 10; i++){
                service.execute(new MyTask(doneLatch, i));
            }
            System.out.println("Swait");
            doneLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
            System.out.println("END");
        }

    }
}
