package com.yumo.android.test.view.view;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.yumo.demo.view.YmTestFragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yumo on 4/2/16.
 * 测试时间相关的东东
 */
public class DateTestView extends YmTestFragment {
    private final String TAG = "DateTestView";

    public void testShowDialogDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Log.d(TAG, "datePickerDialog:" + year + " " + monthOfYear + " " + dayOfMonth);
            }
        }, year, month, day).show();

    }

    public void testShowDialogTime(){
        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(TAG,"timeSetListener:"+hourOfDay+":"+ minute);
            }
        },hour, minute, true).show();
    }

    public void testShowDataPicker(){
        LinearLayout rootView = new LinearLayout(getActivity());

        DatePicker datePicker = new DatePicker(getContext());
        rootView.addView(datePicker);
        showTestView(rootView);
    }

    public void testShowTimePicker(){
        LinearLayout rootView = new LinearLayout(getActivity());

        TimePicker timePicker = new TimePicker(getContext());
        rootView.addView(timePicker);
        showTestView(rootView);
    }


    /**
     * 将当前时间转换为日期
     */
    public void testStrDate(){
        Calendar now = Calendar.getInstance();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(now.getTime());
        showToastMessage(date);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void testisSameDate(){
        Date d1 = new Date();
        Date d2 = new Date();
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();

        showToastMessage(localDate1.isEqual(localDate2)+"");
    }

    public void testSameDate1(){
        Date d1 = new Date();
        Date d2 = new Date();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        boolean result =   cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        showToastMessage(result+"");
    }
}
