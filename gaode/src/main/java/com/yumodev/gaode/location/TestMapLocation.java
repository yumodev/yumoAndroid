package com.yumodev.gaode.location;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/7.
 * 测试高德定位
 */

public class TestMapLocation extends YmTestFragment {
    private final String LOG_TAG = "MapLocation";
    AMapLocationClient mLocationClient;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new AMapLocationClient(getContext().getApplicationContext());
    }

    public void testA(){

    }

    public void testB(){

    }

    private AMapLocationListener getListener(){
        return new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.i(LOG_TAG, "onLocationChanged:");
                if (aMapLocation.isMock()){
                    showToastMessage(aMapLocation.isMock()+"");
                }
                else{
                    //showToastMessage(aMapLocation.toString());
                    showToastMessage(locToStr(aMapLocation));
                }
            }
        };
    }

    /**
     * 最简单的测试
     */
    public void testLocation(){
        //声明AMapLocationClient类对象
       AMapLocationClient mLocationClient = new AMapLocationClient(getContext().getApplicationContext());

       mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                showToastMessage(aMapLocation.toString());
            }
        });
       mLocationClient.startLocation();
    }

    /**
     * 签到
     */
    public void testSingIn(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationClient.setLocationListener(getListener());
        mLocationClient.setLocationOption(option);
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    /**
     * 出行
     */
    public void testTransport(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    /**
     * 运动
     */
    public void testSport(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);

        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());

        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    /**
     * 获取当前Once
     */
    public void testOnce(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setOnceLocation(true);
        option.setNeedAddress(false);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());

        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    public void testInterval(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setOnceLocation(false);
        option.setNeedAddress(false);
        option.setInterval(5000);
        option.setLocationCacheEnable(true);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());

        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    public void testIntervalDevices(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setOnceLocation(false);
        option.setNeedAddress(false);
        option.setInterval(1000);
        option.setLocationCacheEnable(false);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);

        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());

        mLocationClient.stopLocation();
        mLocationClient.startLocation();

        showToastMessage(mLocationClient.isStarted()+" started");
    }

    public void testIntervalDevices1(){
        AMapLocationClient gdLocationClient = new AMapLocationClient(getContext());
        AMapLocationClientOption gdLocationOption = new AMapLocationClientOption();
        gdLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        gdLocationOption.setLocationCacheEnable(false);
        gdLocationOption.setNeedAddress(false);
        gdLocationOption.setInterval(2000);
        gdLocationClient.setLocationOption(gdLocationOption);
        gdLocationClient.setLocationListener(getListener());
        gdLocationClient.startLocation();

        showToastMessage(mLocationClient.isStarted()+" started");

    }

    public void testOnceDeviceSensors(){
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setOnceLocation(true);
        option.setNeedAddress(false);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);

        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(getListener());

        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }



    private String locToStr(AMapLocation loc){
        if (loc == null){
            return "AMapLocation is null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("卫星个数："+loc.getSatellites()).append("\n");
        sb.append("GPS质量："+loc.getGpsAccuracyStatus()).append("\n");

        sb.append(loc.toStr());
        return sb.toString();
    }
}
