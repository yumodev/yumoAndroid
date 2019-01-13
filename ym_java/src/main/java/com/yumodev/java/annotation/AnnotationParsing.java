package com.yumodev.java.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * Created by yumodev on 18/2/14.
 */

public class AnnotationParsing {
    public static void main(String[] args){
        try {
            for (Method method: AnnotationParsing.class.getClassLoader().loadClass("com.yumodev.java.annotation.AnnotationExample").getMethods()){
                if (method.isAnnotationPresent(com.yumodev.java.annotation.YmTestAnnotation.class)){
                    for (Annotation anno : method.getDeclaredAnnotations()){
                        System.out.println("method"+method.toString()+" "+anno.toString());
                        YmTestAnnotation methodInfo = method.getAnnotation(YmTestAnnotation.class);
                        if (methodInfo.revision() == 1){
                            System.out.println("ddd"+methodInfo.comments());
                        }
                    }
                }
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
