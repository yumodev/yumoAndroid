package com.yumo.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yumodev on 1/16/16.
 */
public class YmDateUtil {

    static final int MISEC_UINTS_SECOND = 1000;
    static final int SECOND_UINTS_MINUTE = 60;
    static final int MINUTE_UNINTS_HOUR = 60;
    static final int HOUR_UNINTS_DAY = 24;
    static final int SECOND_UINTS_HOUR = 60 * 60;
    static final int SECOND_UINTS_DAY = 24 * 60 * 60;

    /**
     * 格式化时间 ： 00：00
     * @param duration 时间间隔。 单位是毫秒
     * @return
     */
    public static String formatDuration(long duration) {
        String strTime = "00:00";
        do {
            if (duration <= 0) {
                break;
            }

            long second = duration / MISEC_UINTS_SECOND;
            if (second < SECOND_UINTS_MINUTE) {
                strTime = String.format(Locale.ENGLISH, "00:%02d", second);
                break;
            }

            if (second < SECOND_UINTS_HOUR) {
                strTime = String.format(Locale.ENGLISH, "%02d:%02d", second / MINUTE_UNINTS_HOUR, second % SECOND_UINTS_MINUTE);
                break;
            }

            if (second < SECOND_UINTS_DAY) {
                strTime = String.format(Locale.ENGLISH, "%02d:%02d:%02d",
                        second / SECOND_UINTS_HOUR,
                        (second % (SECOND_UINTS_HOUR)) / MINUTE_UNINTS_HOUR,
                        (second % (SECOND_UINTS_HOUR)) % SECOND_UINTS_MINUTE);
                break;
            }

            if (second > SECOND_UINTS_DAY) {
                strTime = second + "";
                break;
            }

        } while (false);

        return strTime;
    }

    public static String getStrFromTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.US).format(new Date(time));
    }

    public static String getStrTime() {
        return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.US).format(new Date());
    }


    public static String getStrDay() {
        Calendar now = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(now.getTime());
    }


    public static int getYear(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public static int getMonth(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }

    public static int getDay(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取本月第0天的时间
     * @return
     */
    public static long getTimesMonthMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    /**
     * 比较两个时间的日期是否相同
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDate(long d1, long d2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(d2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }


    /**
     * 获取当前时间的前一天时间
     * @return
     */
    public static long getYesterdayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间的前一天时间
     * @return
     */
    public static String getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
    }

}
