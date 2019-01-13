package com.yumodev.java.annotation;

/**
 * Created by yumodev on 18/2/14.
 */

public class AnnotationExample{
    public static void main(String[] args){

    }

    @YmTestAnnotation(comments = "comments method", date="Nov 22 2018")
    public static void testMethod(){
        System.out.println("test");
    }
}
