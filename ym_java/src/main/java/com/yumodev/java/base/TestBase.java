package com.yumodev.java.base;

import junit.framework.TestCase;

/**
 * Created by yumo on 2018/7/2.
 */

public class TestBase extends TestCase {

    public void test(){
        boolean a = false;
        boolean b = false;

        boolean c = a == b;
        assertTrue(c);
    }
}
