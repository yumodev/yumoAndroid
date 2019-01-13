package com.yumodev.java.thread.threadpattern.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

/**
 * Created by guo on 18/3/3.
 */

public class Balking {

    public static class Data{
        private final String fileName;
        private String content;
        private boolean changed;

        public Data(String fileName, String content){
            this.fileName = fileName;
            this.content = content;
            this.changed = true;
        }

        public synchronized void change(String newContent){
            content = newContent;
            changed = true;
        }

        public synchronized void save() throws IOException{
            if (!changed){
                return;
            }

            doSave();
            changed = false;
        }

        private void doSave(){
            System.out.println(Thread.currentThread().getName()+" calls doSave, content = "+content);
            Writer writer= null;
            try {
                writer = new FileWriter(fileName);
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class SaverThread extends Thread{

        private final Data data;
        public SaverThread(String name, Data data){
            super(name);
            this.data = data;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    data.save();
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public static class ChangerThread extends Thread{
        private final Data data;
        private final Random random = new Random();
        public ChangerThread(String name, Data data){
            super(name);
            this.data = data;
        }

        @Override
        public void run() {
            try{
                for (int i =0; true; i++){
                    data.change("No."+i);
                    Thread.sleep(random.nextInt(1000));
                    data.save();
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread("changerThred", data).start();
        new SaverThread("SaverThread", data).start();
    }
}
