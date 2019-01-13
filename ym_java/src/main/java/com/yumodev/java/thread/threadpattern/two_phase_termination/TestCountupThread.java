package com.yumodev.java.thread.threadpattern.two_phase_termination;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestCountupThread {

    public static class CountupThread extends Thread{
        private long counter = 0;

        private volatile boolean shutdownRequested = false;

        public void setShutdownRequested(){
            shutdownRequested = true;
            interrupt();
        }

        public boolean isShutdownRequested(){
            return shutdownRequested;
        }

        public final void run(){
            try {
                while (!isShutdownRequested()){
                    doWork();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                doShutDown();
            }
        }

        private void doWork() throws InterruptedException {
            counter++;
            System.out.println("doWork: counter= "+counter);
            Thread.sleep(500);
        }

        private void doShutDown(){
            System.out.println("doShutDown: counter = "+counter);
        }
    }

    public static void main(String[] args){
        System.out.println("main: BEGIN");

        try {

            CountupThread t = new CountupThread();

            t.start();

            Thread.sleep(10000);

            System.out.println("main:shutDownRequest");
            t.setShutdownRequested();
            System.out.println("main:join");

            t.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("main:End");
    }
}
