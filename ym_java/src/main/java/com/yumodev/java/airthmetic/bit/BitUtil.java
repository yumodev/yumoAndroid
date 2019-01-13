package com.yumodev.java.airthmetic.bit;

/**
 * Created by yumodev on 17/7/6.
 * 位运算工具方法,进制转换大全
 */

public class BitUtil {


    /**
     * 整数值转换为二进制字符串
     * @param a
     * @return
     */
    public static String interToBinaryString(int a){
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb.append((a >> i) & 1);
        }
        return sb.toString();
    }
}
