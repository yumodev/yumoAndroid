package com.yumodev.java.collect.list;

import com.yumodev.java.utils.YmTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumo on 7/30/16
 * 测试Java列表
 */
public class TestList{
    private static final String LOG_TAG = "TestList";

    public static void testArrayList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
         list.add(i+"");
        }

        YmTestUtil.printListThroughIterator(list, LOG_TAG);
    }


    public static void testSubList(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 51; i++){
            list.add(i+1);
        }

        int num = 0;
        int maxNum = 10;

        while (list.size() >= (num+1) * maxNum){
            List<Integer> tempList = list.subList(num * maxNum, (num + 1) * maxNum);
            num++;
        }
        List<Integer> tempList = list.subList(num * maxNum, list.size());

        int i = 1;

    }

    public static void main(String args[]){
       // testArrayList();
        testSubList();
    }
}
