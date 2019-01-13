package com.yumodev.java.thread.threadpattern.guardedSuspension;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by yumodev on 18/3/2.
 */

public class TestGrarded {

    public static class Request{
        private final String name;
        public Request(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        @Override
        public String toString() {
            return "[ Request "+name+" ]";
        }
    }


    public static class RequestQueue{
        private final Queue<Request> queue = new LinkedList<>();

        public synchronized  Request getRequest(){
            while (queue.peek() == null){
                try {
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            return queue.remove();
        }

        public synchronized void putRequest(Request request){
            queue.offer(request);
            notifyAll();
        }
    }

    public static class ClientThread extends Thread{
        private final Random random;
        private final RequestQueue requestQueue;
        public ClientThread(RequestQueue requestQueue, String name, long seed){
            super(name);
            random = new Random(seed);
            this.requestQueue = requestQueue;
        }

        public void run(){
            for (int i  =0;i < 1000; i++){
                Request request = new Request("NO."+i);
                System.out.println(Thread.currentThread().getName()+" request:"+request);
                requestQueue.putRequest(request);

                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static class ServerThread extends Thread{
        private final Random random;
        private final RequestQueue requestQueue;
        public ServerThread(RequestQueue requestQueue, String name, long seed){
            super(name);
            random = new Random(seed);
            this.requestQueue = requestQueue;
        }

        public void run(){
            for (int i  =0;i < 1000; i++){
                Request request = requestQueue.getRequest();
                System.out.println(Thread.currentThread().getName()+" request:"+request);


                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args){
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread(requestQueue, "Hello", 3141592L).start();
        new ServerThread(requestQueue, "world", 6535897L).start();
    }
}
