package com.yumo.common.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yumodev on 7/14/16.
 * 一些测试工具类
 */
public class JTestUtil {


    /************************begin airthmetic ****************************/
    public static void printListThroughRandomAccess(List list, String logTag){
        StringBuilder sb = new StringBuilder();
        for (int i =0; i <list.size(); i++){
            sb.append(list.get(i)+" ");
        }

        System.out.println(logTag+" "+"printListThroughRandomAccess::"+sb.toString());
    }

    public static void printListThroughIterator(List list, String logTag){
        StringBuilder sb = new StringBuilder();
        for (Iterator iter = list.iterator(); iter.hasNext();){
            sb.append(iter.next()+" ");
        }

        System.out.println(logTag+ " "+"printListThroughRandomAccess::"+sb.toString());
    }

    public static String StringFromMap(Map<String, String> map){
        StringBuilder sb = new StringBuilder();
        Set<String> set = map.keySet();

        for (Iterator iter = set.iterator(); iter.hasNext();){
            String key = (String)iter.next();
            sb.append(key+"    "+map.get(key)+"\n");
        }

        return sb.toString();
    }

    /************************end airthmetic ****************************/


    /************************begin Thread 相关的方法****************************/
    public static String getThreadInfo(Thread thread){
        String info = String.format("id:%s, Name:%s, State:%s , Status:%d",
                thread.getId(), thread.getName(), thread.getState(), thread.getPriority());
        return info;
    }
    /************************end Thread 相关的方法****************************/
}
