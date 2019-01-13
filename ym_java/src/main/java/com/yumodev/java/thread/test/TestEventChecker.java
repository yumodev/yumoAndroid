package com.yumodev.java.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yumodev on 9/25/16.
 */
public class TestEventChecker {
    public static abstract class IntGenerator{
        private volatile boolean canceled = false;
        public abstract int next();
        public void setCanceled(boolean cancel){
            canceled = cancel;
        }
        public boolean isCanceled(){
            return canceled;
        }
    }

    public static class EventGenerator extends IntGenerator{
        private int currentEventValue = 0;
        @Override
        public int next() {
            currentEventValue++;
            Thread.yield();
            currentEventValue++;
            return currentEventValue;
        }
    }

    public static class SynchronizedEventGenerator extends IntGenerator{
        private int currentEventValue = 0;

        @Override
        public synchronized int next() {
            currentEventValue++;
            currentEventValue++;
            return currentEventValue;
        }
    }

    public static class MutexEventGenerator extends IntGenerator{
        private int currentEventValue = 0;

        private Lock lock = new ReentrantLock();
        @Override
        public int next(){
            lock.lock();
            try {
                currentEventValue++;
                currentEventValue++;
                return currentEventValue;
            }finally {
                lock.unlock();
            }
        }
    }

    public static class AtomicEventGenerator extends IntGenerator{
        private AtomicInteger currentEventValue = new AtomicInteger(0);

        @Override
        public int next(){
           return currentEventValue.addAndGet(2);
        }
    }

    public static class EventChecker implements Runnable{
        private IntGenerator intGenerator;
        private int id;

        public EventChecker(IntGenerator ig, int n){
            intGenerator = ig;
            id = n;
        }

        @Override
        public void run() {
            while (!intGenerator.isCanceled()){
                int val = intGenerator.next();
                if (val % 2 != 0){
                    System.out.println(val + "  no event");
                    intGenerator.setCanceled(true);
                }
            }
        }

        public static void test(IntGenerator ig, int num){
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < num; i++){
                exec.execute(new EventChecker(ig, i));
            }
        }

        public static void test(IntGenerator ig){
            test(ig, 10);
        }
    }

    public static void main(String[] args){
        //EventChecker.test(new EventGenerator());
        //EventChecker.test(new SynchronizedEventGenerator());
        // EventChecker.test(new MutexEventGenerator());
        EventChecker.test(new AtomicEventGenerator());
    }
}
