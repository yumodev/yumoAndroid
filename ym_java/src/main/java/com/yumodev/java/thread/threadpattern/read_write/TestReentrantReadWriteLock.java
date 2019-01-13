package com.yumodev.java.thread.threadpattern.read_write;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestReentrantReadWriteLock {
    public static class Data{
        private final char[] buffer;
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();
        public Data(int size){
            this.buffer = new char[size];
            for (int i = 0; i < buffer.length; i++){
                buffer[i] = '*';
            }
        }

        public char[] read() throws InterruptedException {
            readLock.lock();
            try {
                return doRead();
            }finally {
                readLock.unlock();
            }
        }

        public void write(char c) throws InterruptedException {
            writeLock.lock();
            try{
                doWrite(c);
            }finally {
                writeLock.unlock();
            }
        }

        private char[] doRead(){
            char[] newBuf = new char[buffer.length];
            for (int i = 0; i < buffer.length; i++){
                newBuf[i] = buffer[i];
            }
            slowly();
            return newBuf;
        }

        private void doWrite(char c){
            for (int i = 0; i < buffer.length; i++){
                buffer[i] = c;
                slowly();
            }
        }

        private void slowly(){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class WriteThread extends Thread{
        private final Random random = new Random();
        private final Data data;
        private final String filler;
        private int index = 0;

        public WriteThread(Data data, String filler){
            this.data = data;
            this.filler = filler;
        }

        @Override
        public void run() {
            try {
                while (true){
                    char c = nextChar();

                    data.write(c);
                    Thread.sleep(random.nextInt(3000));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        private char nextChar(){
            char c = filler.charAt(index);
            index++;
            if (index >= filler.length()){
                index = 0;
            }
            return c;
        }
    }

    public static class ReaderThread extends Thread{
        private final Data data;
        public ReaderThread(Data data){
            this.data = data;
        }

        @Override
        public void run() {
            try {
                while (true){
                    char[] readbuf = data.read();
                    System.out.println(Thread.currentThread().getName()+" reads:"+String.valueOf(readbuf));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        Data data = new Data(10);
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();

        new WriteThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
        new WriteThread(data, "acbcefghijklmnopqrstuvwxyz").start();
    }
}
