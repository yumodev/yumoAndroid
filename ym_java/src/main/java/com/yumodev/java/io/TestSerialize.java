package com.yumodev.java.io;

import java.io.*;

/**
 * Created by yumodev on 10/2/16.
 */
public class TestSerialize {


    static class Data implements Serializable{
        private int n;
        public Data(int n){
            this.n = n;
        }

        public String toString(){
            return getClass().getSimpleName() + " "+ n;
        }
    }

    public static void saveSerializeFile(String fileName, Object object){
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            FileUtils.close(out);
        }
    }

    public static Object readSerializeFile(String fileName){
        ObjectInputStream in = null;
        Object object = null;
        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            object = in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtils.close(in);
        }

        return object;
    }

    public static void testSaveData(){
        Data data = new Data(100);
        String fileName = "data.txt";
        saveSerializeFile(fileName, data);
        Data data1 = (Data)readSerializeFile(fileName);
        System.out.println("data1:" + data1.toString());

    }

    public static void main(String[] args){
        testSaveData();
    }
}
