package com.yumodev.java.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 创建线程
 */
public class Thread1 {

    public void action() {
        //ThreadRunnable();
        ThreadSynchronize synchronize = new ThreadSynchronize();
        synchronize.action();

        AtomicLong longg = new AtomicLong();
    }

    private void ThreadAction() {
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
        new MitiSay("A").start();
        new MitiSay("B").start();
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
    }

    private void ThreadRunnable() {
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
        TestMitiThread1 test = new TestMitiThread1();
        Thread thread1 = new Thread(test);
        Thread thread2 = new Thread(test);
        thread1.start();
        thread2.start();
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
    }

    class MitiSay extends Thread {
        public MitiSay(String threadName) {
            super(threadName);
        }

        public void run() {
            System.out.println(getName() + " 线程运行开始!");
            for (int i = 0; i < 10; i++) {
                System.out.println(i + " " + getName());
                try {
                    sleep((int) Math.random() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(getName() + " 线程运行结束!");
        }
    }

    public class TestMitiThread1 implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
            for (int i = 0; i < 10; i++) {
                System.out.println(i + " " + Thread.currentThread().getName());
                try {
                    Thread.sleep((int) Math.random() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
        }
    }

    public class ClsB {
        int x = 100;

        public synchronized int getX() {
            return x;
        }

        public synchronized int fixX(int y) {
            return x = x - y;
        }
    }
}
