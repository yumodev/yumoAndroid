package com.yumodev.java.base;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class TestDate extends TestCase {

    public void testSecondToHour(){
        System.out.println("58000:"+secondToHour(58000));
        System.out.println("60:"+secondToHour(60));
        System.out.println("180:"+secondToHour(180));

        System.out.println("3600:"+secondToHour(3600));
        System.out.println("600:"+secondToHour(600));
    }

    private float secondToHour(int second){
        float hour = second * 1.0f / 3600;
        return (float) new BigDecimal(hour).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
