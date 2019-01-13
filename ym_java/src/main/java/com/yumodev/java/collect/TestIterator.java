package com.yumodev.java.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yumodev on 7/15/16.
 * 测试迭代器
 * LinkedHashIterator
 * ListIterator
 */
public class TestIterator {
    private static final String LOG_TAG = "TestIterator";

    public static void testIterator(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(i);
        }

        Iterator<Integer> itor = list.iterator();
        while (itor.hasNext()){
            System.out.println(LOG_TAG+" "+itor.next());
        }
    }

    public static void main(String args[]){
        testIterator();
    }
}
