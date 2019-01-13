/**
 * RandomTest.java
 * yumo
 * 2015-1-5
 */
package com.yumodev.java.advance;

import java.util.Random;
import java.util.UUID;

/**
 * yumodev
 * 一个java随机数练习类
 */
public class RandomTest {
    public static void Test() {
        Random random = new Random(100);

        //随机生成10个int数字  next()
        System.out.println("随机生成5个int数字  next()");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextInt()+",");
        }
        System.out.println();
        //生成5个 0到100之间的数字。
        System.out.println("生成5个 0到100之间的数字。 nextInt(100)");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextInt(100)+", ");
        }
        System.out.println();
        //随机生成5个boolean值
        System.out.println("随机生成5个boolean值 netBoolean");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextBoolean()+", ");
        }
        System.out.println();
        //随机生成5个long值
        System.out.println("随机生成5个long值 random nextLong()");
        for (int n = 0; n < 10; n++) {
            System.out.print(random.nextLong()+", ");
        }
        System.out.println();
        //随机生成5个fload值
        System.out.println("随机生成5个fload值 netFloat");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextFloat() * 100+", ");
        }
        System.out.println();
        //随机生成5个double值
        System.out.println("随机生成5个double值 netDouble");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextDouble() * 100+", ");
        }

        //随机生成10个Gaussian值
        System.out.println("随机生成5个张太随机数  nextGaussian()");
        for (int n = 0; n < 5; n++) {
            System.out.print(random.nextGaussian() * 100+",");
        }


        //
        System.out.println("建立一个字节数组，来接收随机生成的字节。 nextBytes(byte[])");

        byte[] bytes = new byte[10];
        random.nextBytes(bytes);
        for (byte bt : bytes) {
            System.out.print(bt+",");
        }

    }

    public static void printRandom(){
        Random random = new Random(100);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Random random1 = new Random(100);

        //随机生成10个int数字  next()
        System.out.println("随机生成5个int数字  next()");
        for (int n = 0; n < 5; n++) {
            System.out.println(random.nextInt(100)+","+random1.nextInt(100));
        }
    }

    /**
     * 通过Math.random()来获取随机数。实际上，它返回的是0(包含)到1(不包含)之间的double值
     */
    public static void printMathRandom(){
        //随机生成10个int数字  next()
        System.out.println("随机生成5个double数字  next()");
        for (int n = 0; n < 5; n++) {
            System.out.println(Math.random());
            System.out.println((int)(Math.random()*100));
        }
    }

    public static void printSysTime(){
        for (int n = 0; n < 5; n++) {
            System.out.println(System.nanoTime());
            System.out.println(System.currentTimeMillis());
        }
    }


    public static void printUuid(){
        for (int n = 0; n < 5; n++) {
            System.out.println(UUID.randomUUID().toString());

        }
    }



    public static void main(String[] args){
        printSysTime();
        printUuid();
        printMathRandom();
        printRandom();
        Test();
    }
}
