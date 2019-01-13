/**
 * thread2.java
 * yumodev
 * 2015-1-3
 */
package com.yumodev.java.thread;


import java.util.Random;

/**
 * yumodev
 * 终止线程
 */
public class ThreadStop {
    static class StopThread extends Thread{

        private volatile  boolean mStop = false;

        /**
         * 设置中断标志位，并触发中断机制
         */
        public void stopThread(){
            mStop = true;
            //设置标志位后，立即中断线程。
            interrupt();
            System.out.println(Thread.currentThread());
        }

        /**
         * 检测是否设置的终止标志
         * @return
         */
        public boolean isStopThread(){
            return mStop;
        }

        @Override
        public void run() {
            Random random = new Random(100);
            while (true){
                /**
                 * 如果检测到用户主动设置了停止标志，
                 * 那就停止运行。
                 */
//                if (isStopThread()){
//                    break;
//                }

                String content = readFromServer(random);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("isInterrupted():"+isInterrupted()+" "+ Thread.currentThread());
                    //触发了线程中断
                    if (isStopThread()){
                        break;
                    }
                }

                saveContent(content);
            }
        }

        /**
         * 从服务器读取数据
         * @return
         */
        private String readFromServer(Random random){
            return "content:" + random.nextInt(1000);
        }

        /**
         * 将文件写入本地
         * @param content
         */
        private void saveContent(String content) {
            System.out.println("saveContent:"+content);
        }

        private synchronized void mywait(){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Thread2 extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("isInterrupted():"+isInterrupted()+" "+ Thread.currentThread());
            }
        }
    }
    public static void testStopThread(){
        StopThread thread = new StopThread();
        thread.setName("stopThread");
        thread.start();

        Thread2 thread2 = new Thread2();
        thread2.setName("Thread2");
        thread2.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.stopThread();
    }

    public static void main(String[] args) {
        testStopThread();
    }
}
