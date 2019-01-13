package com.yumodev.java.thread.threadpattern.read_write;

import java.util.Random;

/**
 * Created by yumodev on 18/3/5.
 */

public class TestReadWriteLock {
    public static class Data{
        private final char[] buffer;
        private final ReadWriteLock lock = new ReadWriteLock();
        public Data(int size){
            this.buffer = new char[size];
            for (int i = 0; i < buffer.length; i++){
                buffer[i] = '*';
            }
        }

        public char[] read() throws InterruptedException {
            lock.readLock();
            try {
                return doRead();
            }finally {
                lock.readUnlock();
            }
        }

        public void write(char c) throws InterruptedException {
            lock.writeLock();
            try{
                doWrite(c);
            }finally {
                lock.writeUnlock();
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

    public static class ReadWriteLock{
        private int readingReader = 0;
        private int waitingWriters = 0;
        private int writingWriter = 0;
        private boolean preferWriter = true;

        public synchronized void readLock() throws InterruptedException {
            while (writingWriter > 0 || (preferWriter && waitingWriters > 0)){
                wait();
            }

            readingReader++;
        }

        public synchronized void readUnlock(){
            readingReader--;
            preferWriter= true;
            notifyAll();
        }

        public synchronized void writeLock() throws InterruptedException {
            waitingWriters++;
            try {
                while (readingReader > 0 || writingWriter > 0){
                    wait();
                }
            }finally {
                waitingWriters--;
            }
            writingWriter++;
        }

        public synchronized void writeUnlock(){
            writingWriter--;
            preferWriter = false;
            notifyAll();
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
