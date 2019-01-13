package com.yumodev.java.advance;

import junit.framework.TestCase;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yumodev on 17/7/18.
 */

public class SystemTest extends TestCase {

    /**
     * //当印当前环境变量
     */
    public void testPrintEnv(){
        Map<String, String> env =  System.getenv();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            sb.append(entry.getKey() +" : " + entry.getValue() + " \n");
        }
        System.out.println(sb.toString());
    }

    /**
     * 打印系统属性
     */
    public void testPrintSystem(){
        Properties properties = System.getProperties();
        Enumeration<String> names = (Enumeration<String>) properties.propertyNames();
        StringBuilder sb = new StringBuilder();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            sb.append(name+" : "+ properties.getProperty(name) + "\n");
        }
        System.out.println(sb.toString());
    }
}
