package com.yumodev.java.collect.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yumodev on 17/9/8.
 *
 * * Key和Value都可以为空
 * Value允许重复
 * 数据有序，
 * 线程非安全
 */

public class TestLinkedHashMap {

    private static void testHashMapAssessOrder(){
        System.out.println("开始访问顺序的测试");
        //accessOrder 为true，按照访问顺序排序，false：按照插入顺序排序
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);
        for (int i = 0; i < 10; i ++){
            map.put(i, i);
        }

        System.out.println(map.toString());

        System.out.println(map.get(6));

        System.out.println(map.toString());
        System.out.println("结束访问顺序的测试");
    }


    private static void testHashMapInsertOrder(){
        System.out.println("开始插入顺序的测试");
        //accessOrder 为true，按照访问顺序排序，false：按照插入顺序排序
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, false);
        for (int i = 0; i < 10; i ++){
            map.put(i, i);
        }

        System.out.println(map.toString());

        System.out.println(map.get(6));

        System.out.println(map.toString());
        System.out.println("结束插入顺序的测试");
    }

    private static void testLruCache(){
        final int maxSize = 10;
        LruCache<Integer,Integer> lruCache = new LruCache<Integer, Integer>(maxSize){
            @Override
            protected int sizeOf(Integer key, Integer value) {
                return value;
            }
        };

        for (int i = 0; i < maxSize; i ++){
            lruCache.put(i, i);
        }
        System.out.println("当前数据："+lruCache.toString());
        System.out.println("key为6的数据"+lruCache.get(6));
        System.out.println("访问key为6的数据后："+lruCache.toString());
        lruCache.put(11,11);
        System.out.println("插入value为11的数据后："+lruCache.toString());
    }

    private static void testLruCache1(){
        //map的最大容量是10
        final int maxSize = 10;
        Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(0, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > maxSize;
            }
        };

        System.out.println(map.size());
        for (int i = 0; i < 10; i ++){
            map.put(i, i);
        }
        System.out.println("当前有+"+map.size()+"个数据："+map.toString());
        System.out.println("key为6的数据："+map.get(6));
        System.out.println("访问key为6的数据后："+map.toString());
        map.put(11,11);
        System.out.println("插入第11个数据后："+map.toString());
    }

    public static void main(String[] args){
        //TestLinkedHashMap.testHashMapAssessOrder();
        //TestLinkedHashMap.testHashMapInsertOrder();
        TestLinkedHashMap.testLruCache();
        //TestLinkedHashMap.testLruCache1();
    }
}
