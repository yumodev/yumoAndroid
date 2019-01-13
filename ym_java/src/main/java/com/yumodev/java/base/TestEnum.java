package com.yumodev.java.base;

import junit.framework.TestCase;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Random;

/**
 * Created by yumodev on 10/2/16.
 * 测试枚举
 */
public class TestEnum extends TestCase{

    public enum NumEnum{FIRST, SECOND, THIRD}

    public enum Status{
        FAILED(-1, " failed"),
        NORMAL(0, " normal "),
        ADD(1, "add");

        int code;
        String desc;
        Status(int code, String desc){
            this.code = code;
            this.desc = desc;
        }
    }

    @Test
    public static void testEnum(){
        for (NumEnum num : NumEnum.values()){
            StringBuilder sb = new StringBuilder();
            sb.append("oridinal: "+ num.ordinal() + "  ");
            sb.append("name: "+ num.name() + " ");
            sb.append(" equals first "+ num.equals(NumEnum.FIRST) + " ");
            sb.append(" == first "+ (num == NumEnum.FIRST) + " ");
            sb.append("class:"+ num.getDeclaringClass() + " ");
            System.out.println(sb.toString());
        }

        for (String s : "FIRST,SECOND,THIRD".split(",")){
            NumEnum num = NumEnum.valueOf(s);
            System.out.println(num);

        }
    }

    @Test
    public static void printEnum(){
        for (Type t : NumEnum.class.getGenericInterfaces()){
            System.out.println("type:"+t);
        }

        System.out.println("superClass:"+NumEnum.class.getSuperclass());

        for (Method m : NumEnum.class.getMethods()){
            System.out.println(m.toString());
        }
    }

    public static <T extends  Enum<T>> T random(Class<T> ec){
        T[] value = ec.getEnumConstants();
        Random rand = new Random(100);
        return value[rand.nextInt(value.length)];
    }

    public static void main(String[] args){
        //testEnum();
       // printEnum();
        System.out.println(random(NumEnum.class));
    }


    @Test
    public void testShowStatus(){
        for (Status num : Status.values()){
            StringBuilder sb = new StringBuilder();
            sb.append("ordinal: "+ num.ordinal() + "  ");
            sb.append("name: "+ num.name() + " ");
            sb.append("code: "+ num.code + " ");
            sb.append("desc: "+ num.desc + " ");
            sb.append("class:"+ num.getDeclaringClass() + " ");
            System.out.println(sb.toString());
        }
    }
}
