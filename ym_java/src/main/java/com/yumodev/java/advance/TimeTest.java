/**
 * TimeTest.java
 * yumodev
 * 2015-1-5
 * java 所有时间相关的测试使用方法的集合。
 */
package com.yumodev.java.advance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * yumo
 */
public class TimeTest {

    public static void main(String[] args) {
        getNow();
    }

    public void Test() {
        //获取当前时间
        Date date = new Date();
        System.out.println(date.toString());

        Calendar time = Calendar.getInstance();
        System.out.println(time.getTime().toString());

        //格式化输出时间
        SimpleDateFormat simDateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeF = simDateF.format(date);
        System.out.println(timeF);
    }

    /**
     * 这个方面里面，测试了和实现了多种获取当前时间的方法。
     * yumodev
     * void
     * 2015-1-5
     */
    public static void getNow() {
        //通过 Date() 获取 分配 Date 对象并初始化此对象，以表示分配它的时间（精确到毫秒）。
        Date date = new Date();
        System.out.println(date.toString());

        Calendar time = Calendar.getInstance();
        System.out.println(time.getTime());
    }
}
