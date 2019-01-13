package com.yumodev.java.thread.threadpattern.producer_consumer;

import java.util.Random;


/**
 * Created by guo on 18/3/3.
 */

public class MakerEater {
    public static class Table{
        private final String[] buffer;
        private int tail;
        private int head;
        private int count;

        public Table(int count){
            this.buffer = new String[count];
            this.head = 0;
            this.tail = 0;
            this.count = 0;
        }

        public synchronized void put(String cake) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " puts: "+cake);
            while (count >= buffer.length){
                wait();
            }

            buffer[tail] = cake;
            tail = (tail + 1) % buffer.length;
            count++;
            notifyAll();
        }

        public synchronized String take() throws InterruptedException {
            while (count <= 0){
                wait();
            }

            String cake = buffer[head];
            head =(head+1)%buffer.length;
            count --;
            notifyAll();
            System.out.println(Thread.currentThread().getName() + " take: "+cake);
            return cake;
        }
    }


    public static class MakeThread extends Thread{
        private final Random random;
        private final Table table;
        private static volatile int id = 0;

        public MakeThread(String name, Table table, long seed){
            super(name);
            this.table = table;
            this.random = new Random(seed);

        }

        @Override
        public void run() {
            try {
                while (true){
                    Thread.sleep(random.nextInt(1000));
                    String cacke = "[ Cake No:"+nextId()+" by "+getName()+" }";
                    table.put(cacke);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        private static synchronized int nextId(){
            return id++;
        }
    }

    public static class EaterThread extends Thread{
        private final Random random;
        private final Table table;

        public EaterThread(String name, Table table, long seed){
            super(name);
            this.table = table;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            try {
                while (true){
                    String cake = table.take();
                    Thread.sleep(random.nextInt(1000));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Table table = new Table(3);
        new MakerEater.MakeThread("MakeThread-1", table, 100000).start();
        new MakerEater.MakeThread("MakeThread-2", table, 100000).start();
        new MakerEater.MakeThread("MakeThread-3", table, 100000).start();
        new MakerEater.EaterThread("Eaterhread-1", table, 100000).start();
        new MakerEater.EaterThread("Eaterhread-2", table, 100000).start();
        new MakerEater.EaterThread("Eaterhread-3", table, 100000).start();
    }
}
