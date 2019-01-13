package com.yumo.common.util;

import org.junit.Test;

/**
 * Created by yumo on 2018/4/8.
 */
public class YmDateUtilTest {
    @Test
    public void getStrDay() throws Exception {
    }

    @Test
    public void getYesterDayTime() throws Exception {
        String today = YmDateUtil.getStrDay();
        String yesterday = YmDateUtil.getStrFromTime(YmDateUtil.getYesterdayTime());

        System.out.print(today+"  "+yesterday);
    }

    @Test
    public void getYesterday() throws Exception {
        String yesterday = YmDateUtil.getYesterday();

        System.out.print(yesterday);
    }

}