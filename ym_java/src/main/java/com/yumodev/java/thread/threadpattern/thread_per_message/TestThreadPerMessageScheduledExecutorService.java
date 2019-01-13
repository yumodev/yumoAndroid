package com.yumodev.java.thread.threadpattern.thread_per_message;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestThreadPerMessageScheduledExecutorService {

    public static class Helper{
        public void handler(int count, char c){
            System.out.println(" hanlder("+count+","+ c+" ) begin");

            for (int i =0; i < count;i++){
                slowly();
                System.out.print(c);
            }

            System.out.println("");
            System.out.println(" hanlder("+count+","+ c+" ) end");
        }

        private void slowly(){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Host{
        private final Helper helper = new Helper();
        private final ScheduledExecutorService executor;
        public Host(ScheduledExecutorService executor){
            this.executor = executor;
        }
        public void request(final int count, final char c){
            System.out.println(" request("+count+","+ c+" ) begin");

            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    helper.handler(count, c);
                }
            }, 3L, TimeUnit.SECONDS);
            System.out.println(" request("+count+","+ c+" ) end");
        }
    }

    public static void main(String[] args){
        System.out.println("main Begin");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        Host host = new Host(executorService);
        try {
            host.request(10, 'A');
            host.request(10, 'B');
            host.request(10, 'C');
        }finally {
            executorService.shutdown();
        }

        System.out.println("main End");
    }
}
