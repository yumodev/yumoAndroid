package com.yumo.common.util;

import java.util.Random;

/**
 * Created by yumodev on 18/3/22.
 * 字符串，文本等等相关的操作
 */

public class YmStringUtil {
    public static String convertIntArrayToString(int[] ran){
        return convertIntArrayToString(ran, " ");
    }

    /**
     * 将in[] 转换转换为字符串
     * @param ran
     * @param split
     * @return
     */
    public static String convertIntArrayToString(int[] ran, String split){
        StringBuilder s = new StringBuilder();
        for (int n = 0; n < ran.length; n++) {
            s.append(ran[n]+ split);
        }

        return s.toString();
    }

    /**
     * 随机生成制定长度的随机数
     * @param length
     * @return
     */
    public static int[] randomIntArray(int length){
        int[] ran = new int[length];
        Random random = new Random();
        for (int n = 0; n < ran.length; n++) {
            ran[n] = random.nextInt(100);
        }
        return ran;
    }
}
