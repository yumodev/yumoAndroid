package com.yumodev.java.jdk8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;

/**
 * Created by yumodev on 17/11/10.
 *
 * lambda语法：
 *(params) -> expression
 (params) -> statement
 (params) -> { statements }
 (type1 arg1, type2 arg2...) -> { body }

 以下是lambda表达式的重要特征:
 可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
 可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
 可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
 可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
 */

public class lambda {

    public static void testThreadRunnable(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread running:"+Thread.currentThread().getName());
            }
        }).start();
    }

    /**
     * 用lambda表达式实现Runnable
     */
    public static void testThreadRunnable1(){
        new Thread(()->System.out.println("thread running:"+Thread.currentThread().getName())).start();
    }


    public static void testButtonAction(){
        JButton show =  new JButton("Show");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event handling without lambda expression is boring");
            }
        });
    }

    public static int add(int a, int b){
        return  a + b;
    }

    public static void testForEach(){
        Arrays.asList(1,2,3).forEach(n -> System.out.println(n));
    }

    public static void main(String[] args){
        lambda.testThreadRunnable();
        lambda.testThreadRunnable1();
        //lambda.testButtonAction();
        lambda.testForEach();
    }
}
