/**
 * Thread3.java
 * yumodev
 * 2015-3-11
 */
package com.yumodev.java.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * yumodev
 * 该类包含线程的一些基础操作，睡眠，优先级，中断机制，获取线程的属性等等。
 */
public class Thread3 {

    /**
     *
     */
    public Thread3() {
    }

    public void Action() {
        //sleep 线程休眠
        //new Thread(new ThreadSleep()).start();

        //TimeUinit 线程休眠。
        //new Thread(new ThreadTimeUnitSleep()).start();

        //线程异常
        Thread threadException = new Thread(new ThreadUncaughtException(), "threadExceptionHandler");
        threadException.setUncaughtExceptionHandler(new ExceptionHandler());
        threadException.start();

        //测试本地环境变量
        TestThreadLocal threadLocal = new TestThreadLocal();
//		for(int n = 0; n < 5 ; n ++)
//		{
//			new Thread(threadLocal,"threadLocal"+n).start();
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

        //测试线程组
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        for (int n = 0; n < 5; n++) {
            new Thread(threadGroup, threadLocal).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("this threadgroup has the active thread num is :" + threadGroup.activeCount());
        threadGroup.list();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            System.out.println(thread.getName() + " " + thread.getState());
        }

        //工厂方法。
        MyThreadFactory threadFactory = new MyThreadFactory("myThreadFactory");
        for (int n = 0; n < 5; n++) {
            threadFactory.newThread(new ThreadRunnable()).start();
        }
        System.out.println("threadfactoryStats \r\n" + threadFactory.getStats());
    }

    //未处理的异常。
    class ExceptionHandler implements UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // TODO Auto-generated method stub
            System.out.println(thread.getName() + " " + ex.getClass().getName() + " " + ex.getMessage());
        }
    }

    //线程工厂
    class MyThreadFactory implements ThreadFactory {

        private int count = 0;
        private String name = "";
        private List<String> stats = new ArrayList<String>();

        public MyThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            // TODO Auto-generated method stub
            if (r == null) return null;
            count++;
            Thread t = new Thread(r, "thread factory name" + count);
            stats.add(String.format("Thread:name(%s), Thread:id(%s),Thread:CreateTime(%s)", t.getName(), t.getId(), new Date()));
            return t;
        }

        public String getStats() {
            StringBuffer buffer = new StringBuffer();
            for (String stat : stats) {
                buffer.append(stat);
                buffer.append("\r\n");

            }

            return buffer.toString();
        }

    }

    //重新线程组，并设置异常信息
    class MyThreadGroup extends ThreadGroup {

        public MyThreadGroup(String name) {
            super(name);
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            super.uncaughtException(t, e);
            System.out.println(t.getName() + " " + e.getClass().getName() + " " + e.getMessage());
            //中断所有的线程。
            this.interrupt();
        }

    }

    //线程睡眠测试类
    class ThreadSleep implements Runnable {

        @Override
        public void run() {
            int n = 0;
            while (true) {
                System.out.println(Thread.currentThread().getName() + " " + new Date());
                //中断线程
                if (n > 5) Thread.currentThread().interrupt();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                n++;
            }
        }
    }

    class ThreadRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //线程睡眠测试类 TimeUnit.SECONDS.sleep
    class ThreadTimeUnitSleep implements Runnable {

        public ThreadTimeUnitSleep() {

        }

        @Override
        public void run() {
            int n = 0;
            while (true) {
                System.out.println(Thread.currentThread().getName() + " " + new Date());
                if (n > 5) Thread.currentThread().interrupt();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                n++;
            }
        }
    }

    //测试异常的线程
    class ThreadUncaughtException implements Runnable {

        @Override
        public void run() {
            int n = Integer.parseInt("ddddddd");
        }

    }

    //本地线程变量。
    class TestThreadLocal implements Runnable {
        private ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                return new Date();
            }
        };

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " begin  " + startDate.get());

            try {
                Thread.sleep((int) (Math.random() * 10) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end " + startDate.get());
        }

    }

}
