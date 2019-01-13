package com.yumodev.java.thread.threadpattern.WorkerThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestWorkerThreadExecutors {

    public static class Request implements Runnable{
        private final String name;
        private final int number;
        private final Random random = new Random();

        public Request(String name, int number){
            this.name = name;
            this.number = number;
        }

        public void run(){
            System.out.println(Thread.currentThread().getName()+" executes "+ this);
            try {
                Thread.sleep(random.nextInt(1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "[Request form + "+name + "No. " + number +"]";
        }
    }

    public static class  Channel{
        private static final int MAX_REQUEST = 100;

        private final Request[] requestQueue;

        private int tail;
        private int head;
        private int count;

        private final WorkerThread[] threadPool;

        public Channel(int threads){
            this.requestQueue = new Request[MAX_REQUEST];
            this.head = 0;
            this.tail = 0;
            this.count = 0;

            threadPool = new WorkerThread[threads];

            for (int i = 0; i < threads; i++){
                threadPool[i] = new WorkerThread("Workder-"+i, this);
            }
        }

        public void startWorkers(){
            for (int i = 0; i < threadPool.length; i++){
                threadPool[i].start();
            }
        }

        public synchronized void putRequest(Request request){
            while (count >= requestQueue.length){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            requestQueue[tail] = request;
            tail = (tail + 1) % requestQueue.length;
            count++;
            notifyAll();
        }

        public synchronized  Request takeRequest(){
            while (count <= 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Request request = requestQueue[head];
            head = (head + 1) % requestQueue.length;
            count --;
            notifyAll();
            return request;
        }
    }

    public static class WorkerThread extends Thread{
        private final Channel channel;

        public WorkerThread(String name, Channel channel){
            super(name);
            this.channel = channel;
        }

        @Override
        public void run() {
            while (true){
                Request request = channel.takeRequest();
                request.run();
            }
        }
    }

    public static class ClientThread extends  Thread{
        private final ExecutorService executorService;
        private static final Random random = new Random();

        public ClientThread(String name, ExecutorService executorService){
            super(name);
            this.executorService = executorService;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; true; i++){
                    Request request = new Request(getName(), i);
                    executorService.execute(request);
                    Thread.sleep(random.nextInt(1000));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (RejectedExecutionException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            new ClientThread("Alice", executorService).start();
            new ClientThread("Bobby", executorService).start();
            new ClientThread("Chris", executorService).start();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
