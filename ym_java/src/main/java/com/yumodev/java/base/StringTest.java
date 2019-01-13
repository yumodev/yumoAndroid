package com.yumodev.java.base;

import junit.framework.TestCase;

import java.util.Locale;

/**
 * Created by yumodev on 17/9/17.
 * 字符串测试
 */

public class StringTest extends TestCase {

    public void testNew(){
        String nameA = "a";
        String nameB = nameA;

        equals(nameA == nameB);
    }

    public  String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }
    public void testFormat(){
        System.out.print(format("%.1f", 100.01f));
    }
}
