package com.yumodev.java.airthmetic.stack;

import com.yumodev.java.utils.YmTestUtil;

import java.util.Stack;

/**
 * Created by yumo on 16-7-11.
 * java stack 测试使用类
 */
public class TestStack {
    private final String LOG_TAG = "TestStack";

    /**
     * 打印一个stack,通过list和iterator 两种方式。
     */
    public void testPrintStack(){
        Stack<String> stack = new Stack<>();
        stack.push("1111");
        stack.push("1112");
        stack.push("1113");

        YmTestUtil.printListThroughIterator(stack, LOG_TAG);
        YmTestUtil.printListThroughRandomAccess(stack, LOG_TAG);
    }
}
