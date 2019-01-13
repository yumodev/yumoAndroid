package com.yumodev.java.thread.threadpattern.producer_consumer;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Created by guo on 18/3/3.
 */

public class TestExchanger {

    public static class ProducerThread extends Thread{
        private final Exchanger<char[]> exchanger;
        private char[] buffer = null;
        private char index = 0;
        private final Random random;


        public ProducerThread(String name, Exchanger<char[]> exchanger, char[] buffer, long seed){
            super(name);
            this.exchanger = exchanger;
            this.buffer = buffer;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            try {
                while (true){
                    for (int i = 0; i < buffer.length; i++){
                        buffer[i] = nextChar();
                        System.out.println(Thread.currentThread().getName()+":"+buffer[i]+" ->");
                    }
                    System.out.println(Thread.currentThread().getName()+" : before exchange");
                    buffer = exchanger.exchange(buffer);
                    System.out.println(Thread.currentThread().getName()+" : after exchange");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        private char nextChar() throws InterruptedException{
            char c = (char)('A'+index%26);
            index++;
            Thread.sleep(random.nextInt(1000));
            return c;
        }
    }

    public static class ConsumerThread extends Thread{
        private final Exchanger<char[]> exchanger;
        private char[] buffer = null;
        private final Random random;

        public ConsumerThread(String name, Exchanger<char[]> exchanger, char[] buffer, long seed){
            super(name);
            this.exchanger = exchanger;
            this.buffer = buffer;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            try {
                while (true){
                    System.out.println(Thread.currentThread().getName()+" : before exchange");
                    buffer = exchanger.exchange(buffer);
                    System.out.println(Thread.currentThread().getName()+" : after exchange");

                    for (int i = 0; i < buffer.length; i++){
                        System.out.println(Thread.currentThread().getName()+":"+buffer[i]+" ->");
                        Thread.sleep(1000);
                    }

                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Exchanger<char[]> exchanger = new Exchanger<>();
        char[] buffer1 = new char[10];
        char[] buffer2 = new char[10];

        new ProducerThread("ProducerThread", exchanger, buffer1, 10000).start();
        new ConsumerThread("ConsumeThread", exchanger, buffer2, 10000).start();
    }
}
