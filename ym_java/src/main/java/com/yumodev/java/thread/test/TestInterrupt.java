package com.yumodev.java.thread.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by yumodev on 9/25/16.
 */
public class TestInterrupt {

    public static ExecutorService exec = Executors.newCachedThreadPool();

    static class SleepBlocked implements Runnable{
        @Override
        public void run() {
            try{
                TimeUnit.SECONDS.sleep(100);
            }catch (InterruptedException e){
                System.out.println("SleepBlocked: InterruptedException");
            }
            System.out.println("SleepBlocked complete");
        }
    }

    static class NioBlocked implements Runnable{
        private final SocketChannel sc;
        public NioBlocked(SocketChannel sc){
            this.sc = sc;
        }

        @Override
        public void run() {
            try {
                System.out.println("waiting for read:"+this);
                sc.read(ByteBuffer.allocate(1));
            }catch (ClosedByInterruptException e){
                System.out.println("interrupted by interrupted block");
            }catch (AsynchronousCloseException e){
                System.out.println("interrupted by asy block");
            }catch (IOException e){
                System.out.println("interrupted by io block");
            }
        }
    }

    static class IOBlocked implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println("wait to read");
                System.in.read();
            }catch (IOException e){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("interrupted by read block");
                }else{
                    throw new RuntimeException(e);
                }
            }

            System.out.println("IOBlocked complete");
        }
    }

    static class SynchronizedBlocked implements Runnable{

        public synchronized void f(){
            while (true){
                Thread.yield();
            }
        }

        public SynchronizedBlocked(){
            new Thread(){
                @Override
                public void run() {
                    f();
                }
            }.start();
        }

        @Override
        public void run() {
            System.out.println("Trying to call f()");
            f();
            System.out.println("Exiting SynchronizedBlocked.run()");
        }
    }

    public static void test(Runnable runnable) throws InterruptedException{
        Future<?> f = exec.submit(runnable);
        TimeUnit.SECONDS.sleep(3);
        System.out.println("interrupting:"+ runnable.getClass().getName());
        f.cancel(true);
        System.out.println("send interrupting:"+runnable.getClass().getName());
    }

    public static void testNio() throws Exception {
        InetSocketAddress isa = new InetSocketAddress("www.baidu.com", 80);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);
        Future<?> f = exec.submit(new NioBlocked(sc1));
        exec.execute(new NioBlocked(sc2));
        exec.shutdown();
        TimeUnit.SECONDS.sleep(1);
        f.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        sc2.close();
    }


    public static class NeedsCleanup{
        private final int id;

        public NeedsCleanup(int id){
            this.id = id;
            System.out.println("needsCleanup:"+id);
        }

        public void cleanUp(){
            System.out.println("cleanup:"+id);
        }
    }

    public static class Cleanup implements Runnable{
        private volatile double d = 0f;

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()){
                    NeedsCleanup n1 = new NeedsCleanup(1);
                    try {
                        System.out.println("sleeping");
                        TimeUnit.SECONDS.sleep(1);

                        NeedsCleanup n2 = new NeedsCleanup(2);
                        try {
                            System.out.println("calculating");
                            for (int i = 1; i < 250000; i++){
                                d = d + (Math.PI+Math.E) / d;
                            }

                            System.out.println("finish calculating");
                        }finally {
                            n2.cleanUp();
                        }
                    }finally {
                        n1.cleanUp();
                    }
                }
            }catch (InterruptedException e){
                System.out.println("vis interruptedException");
            }

        }
    }

    public static void testCleanUp() throws InterruptedException {
        Thread t = new Thread(new Cleanup());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }

    public static void main(String[] args) throws Exception {
        //test(new SleepBlocked());
        //test(new IOBlocked());
        //test(new SynchronizedBlocked());

        //testNio();

        testCleanUp();
    }
}
