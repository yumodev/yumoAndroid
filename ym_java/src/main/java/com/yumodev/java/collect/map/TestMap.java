package com.yumodev.java.collect.map;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * Created by yumodev on 10/8/16.
 */
public class TestMap {

    public static class TestHashMap{

        public TestHashMap(){
            testHashMap();
        }

        public void testHashMap(){
            Random r = new Random();
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < 10; i++){
                map.put(i, r.nextInt(10));
            }

            System.out.println(map.toString());
        }
    }


    public static class TestHashTable{

        public static void testHashTable(){
            Random r = new Random();
            Hashtable table = new Hashtable();
            for (int i = 0; i < 10; i++){
                table.put(i, r.nextInt(10));
            }

            System.out.println(table.toString());
        }


    }



    public static void testLinkedListHashMap(){

    }

    public static void testTreeMap(){

    }

    public static void main(String[] args){
        //TestHashTable.testHashTable();
        new TestHashMap();
    }
}
