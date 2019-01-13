/**
 * TreadAirthmetic.java
 * yumo
 * 2015-3-18
 * TODO
 */
package com.yumodev.java.thread;

/**
 * yumo
 * 编写一个程序使两个线程陷入思索。
 */
public class ThreadDeadLock {

    public static void main(String[] args) {
        ThreadDeadLock deadLock = new ThreadDeadLock();
        deadLock.action();
    }

    private String resourceOne = "资源1";
    private String resourceTwo = "资源2";

    Thread t1 = new Thread("线程1") {
        public void run() {
            while (true) {
                System.out.println("线程1启动");
                synchronized (resourceOne) {
                    synchronized (resourceTwo) {
                        System.out.printf("线程1 已拥有(%s),需要(%s)\r\n", resourceOne, resourceTwo);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    Thread t2 = new Thread("线程3") {
        public void run() {
            System.out.println("线程2启动");
            while (true) {
                synchronized (resourceTwo) {
                    synchronized (resourceOne) {
                        System.out.printf("线程2 已拥有(%s),需要(%s)\r\n", resourceTwo, resourceOne);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    public void action() {
        t1.start();
        t2.start();
    }
}
