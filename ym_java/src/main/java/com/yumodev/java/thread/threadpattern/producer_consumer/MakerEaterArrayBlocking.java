package com.yumodev.java.thread.threadpattern.producer_consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by guo on 18/3/3.
 */

public class MakerEaterArrayBlocking {
    public static class Table extends ArrayBlockingQueue<String>{
        public Table(int count){
            super(count);
        }

        public void put(String cake) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " puts: "+cake);
            super.put(cake);
        }

        public String take() throws InterruptedException {
            String cake = super.take();
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
        new MakerEaterArrayBlocking.MakeThread("MakeThread-1", table, 100000).start();
        new MakerEaterArrayBlocking.MakeThread("MakeThread-2", table, 100000).start();
        new MakerEaterArrayBlocking.MakeThread("MakeThread-3", table, 100000).start();
        new MakerEaterArrayBlocking.EaterThread("Eaterhread-1", table, 100000).start();
        new MakerEaterArrayBlocking.EaterThread("Eaterhread-2", table, 100000).start();
        new MakerEaterArrayBlocking.EaterThread("Eaterhread-3", table, 100000).start();
    }
}
