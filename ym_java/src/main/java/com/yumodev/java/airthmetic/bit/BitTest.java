package com.yumodev.java.airthmetic.bit;

import junit.framework.TestCase;

/**
 * Created by yumo on 3/17/16.
 * java 位运算
 */
public class BitTest extends TestCase {

    private final String LOG_TAG =  "JavaBitTest";
    public static final int INT_A = 1;
    public static final int INT_B = 1 << 1;
    public static final int INT_C = 1 << 2;
    public static final int INT_D = 1 << 3;
    public static final int INT_E = 1 << 4;

    public void testHelloWorld() throws Exception {
        int expected = 1;
        int reality = 1;
        assertEquals(expected, reality);
    }

    /**
     * 测试左移操作
     */
    public void testLeftMove(){
        int a = 2;
        System.out.println( a + " << 1 = " + (a << 1)); // a * 2;
        System.out.println( a + " << 2 = " + (a << 2)); // a * 2 * 2;
        System.out.println( a + " << 3 = " + (a << 3)); // a * 2 * 2 * 2;
        System.out.println( a + " << 4 = " + (a << 4)); // a * 2 * 2 * 2 * 2;
    }

    /**
     * 测试右移操作
     */
    public void testRightMove(){
        int a = -8;
        System.out.println("算术右移");
        System.out.println( a + " >> 1 = " + (a >> 1) + " 二进制："+ BitUtil.interToBinaryString( a >> 1));
        System.out.println( a + " >> 2 = " + (a >> 2) + " 二进制："+ BitUtil.interToBinaryString( a >> 2));
        System.out.println( a + " >> 3 = " + (a >> 3) + " 二进制："+ BitUtil.interToBinaryString( a >> 3));
        System.out.println( a + " >> 4 = " + (a >> 4) + " 二进制："+ BitUtil.interToBinaryString( a >> 4));

        System.out.println("逻辑右移");
        System.out.println( a + " >>> 1 = " + (a >>> 1) + " 二进制："+ BitUtil.interToBinaryString( a >>> 1));
        System.out.println( a + " >>> 2 = " + (a >>> 2) + " 二进制："+ BitUtil.interToBinaryString( a >>> 2));
        System.out.println( a + " >>> 3 = " + (a >>> 3) + " 二进制："+ BitUtil.interToBinaryString( a >>> 3));
        System.out.println( a + " >>> 4 = " + (a >>> 4) + " 二进制："+ BitUtil.interToBinaryString( a >>> 4));

        System.out.println( -2 >>> 1);
    }

    /**
     * 左移操作的相关运算
     */
    public void testLeftMoveOp(){
        System.out.println(INT_A + " 二进制:"+Integer.toBinaryString(INT_A));
        System.out.println(INT_B + " 二进制:"+Integer.toBinaryString(INT_B));
        System.out.println(INT_C + " 二进制:"+Integer.toBinaryString(INT_C));
        System.out.println(INT_D + " 二进制:"+Integer.toBinaryString(INT_D));
        System.out.println(INT_E + " 二进制:"+Integer.toBinaryString(INT_E));

        int a = INT_A | INT_B | INT_C;
        System.out.println(" a = INT_A | INT_B | INT_C = " + a + " 二进制:"+Integer.toBinaryString(a));

        //判断 a 中是否包含INT_B 可以使用 a & INT_B == INT_B
        int b = a & INT_B;
        System.out.println(" a & INT_B"+ b +" 二进制:"+Integer.toBinaryString(b));

        //将 a 中的INT_B位的值去除，采用的方法为
        int c = a ^ INT_B;
        System.out.println(" "+ c +" 二进制:"+Integer.toBinaryString(c));

    }

    public void testIntToBinary(){
        int a = -10;
        System.out.println("整数：1"+"   二进制："+BitUtil.interToBinaryString(a));
        System.out.println("整数：1"+"   二进制："+Integer.toBinaryString(a));
       // System.out.println(Integer.parseInt(BitUtil.interToBinaryString(1)));
    }


    public void testBit(){
        int a = 00000001;
        int b = 00000010;

        int c = a | b;
        //Log.d(LOG_TAG, "c = a | b:" + c);

    }

    /**
     * 打印位移的值
     */
    public void testBit2(){
        for (int i =0; i < 32; i++){
            //Log.d(LOG_TAG, (1 << i)+"");
        }
    }

    public void testBit3(){
        int a = -1<< 0;
        int b = -1<< 1;

        int c = a | b;
        //Log.d(LOG_TAG, "a:"+a +" b:"+b+" c:"+c);

        //Log.d(LOG_TAG, " c & a :" + (c & a) );

        //Log.d(LOG_TAG, " c & 16 :" + (c & 16) );

        c &= ~a;
        //Log.d(LOG_TAG, " c &= ~a"+ c);
    }


    public void testBit4(){
        int a = 1<< 2;
        int b = 1<< 4;

        int c = a | b;
        //Log.d(LOG_TAG, "a:"+a +" b:"+b+" c:"+c);
        //Log.d(LOG_TAG, " c & a :" + (c & a) );

    }


    public void testBit5() {
        int a = 0;
        int b = 1 << 0;
        int c = 1 << 1;
        int d = 1 << 2;

        int flag = b | c;

        assertTrue((flag & b) == b);
        assertTrue((flag & c) == c);

        flag = flag ^ c ;
        assertFalse((flag & c) == c);

        flag = 0 & 1;
        flag = (~(flag&b))&(flag|b);
        flag = (~(3&1))&(3|1);
        assertTrue(flag == c);
    }


    /**
     * 按位非运算
     */
    public void testNot(){
        System.out.println(~0);
        System.out.println(~1);
    }

    /**
     * 二进制中1的个数
     */
    public void testOneNum(){
        int n = 5;
        int num = 0;
        do {
            if ( (n & 1) == 1){
                num ++;
            }
        }while ( (n = (n >> 1)) > 0);
        System.out.println("testOneNum: "+num);
    }


    /**
     * 二进制中1的个数
     */
    public void testOneNum1(){
        int n = 5;
        int num = 0;
        while (n > 0){
            n = n & (n - 1);
            num ++;
        }
        System.out.println("testOneNum: "+num);
    }


    public void testBit1(){
        int header = 0x011001;

        System.out.println((header & 0x0000ff)  );
        System.out.println((header & 0x00ff00) >> 8 );
        System.out.println((header & 0xff0000) >> 16 );
        if ( (header & 0x0000ff) == 0){
            System.out.println((header & 0x00ff00) >> 8 );
        }else{
            System.out.println((header & 0x00ffff) );
        }





        byte[] bytes = new byte[3];
        bytes[0] = (byte) header;
        bytes[1] = (byte) (header >> 8);
        bytes[2] = (byte) (header >> 16);

        System.out.println(bytes[0]);
        System.out.println(bytes[1]);
        System.out.println(bytes[2]);


    }
}
