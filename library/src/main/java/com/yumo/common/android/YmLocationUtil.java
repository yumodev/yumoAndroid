package com.yumo.common.android;

import android.location.Location;
import android.os.Build;

import com.yumo.common.util.YmDateUtil;

/**
 * Created by yumo on 2018/5/10.
 */

public class YmLocationUtil {

    public static String formatLocation(Location location){
        if (location == null){
            return "location is null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("经度："+location.getLongitude()+" 纬度"+location.getLatitude() +" \n");
        sb.append(" 适配器："+location.getProvider()+ " time: "+ YmDateUtil.getStrFromTime(location.getTime())+"\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            sb.append(" "+location.getElapsedRealtimeNanos()+" \n");
        }

        if (location.hasSpeed()){
            sb.append(" Speed 速度："+location.getSpeed()+" \n");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (location.hasSpeedAccuracy()){
                sb.append(" SpeedAccuracy 速度精度："+location.getSpeedAccuracyMetersPerSecond()+" \n");
            }
        }

        if (location.hasAccuracy()){
            sb.append(" Accuracy 精度："+location.getAccuracy()+" \n");
        }

        if (location.hasAltitude()){
            sb.append(" Altitude 海拔："+location.getBearing()+" \n");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (location.hasVerticalAccuracy()){
                sb.append("VerticalAccuracy 垂直精度："+location.getVerticalAccuracyMeters()+" \n");
            }
        }

        if (location.hasBearing()){
            sb.append(" Bearing 方位："+location.getBearing()+" \n");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (location.hasBearingAccuracy()){
                sb.append("BearingAccuracy 方位精度："+location.getBearingAccuracyDegrees()+" \n");
            }
        }

        if (location.getExtras() != null){
            sb.append(" "+location.getExtras().toString()+" \n");
        }

        return sb.toString();
    }
}
