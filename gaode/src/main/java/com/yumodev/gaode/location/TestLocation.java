package com.yumodev.gaode.location;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yumo.demo.view.YmTestFragment;

import java.util.Iterator;


public class TestLocation extends YmTestFragment {

    private final String LOG_TAG = "AndroidLocation";
    private TextView mTextView = null;
    protected StringBuilder sb = new StringBuilder();
    private LocationManager mLocationManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected View getFooterView() {
        mTextView = new TextView(getContext());
        return mTextView;
    }

    public void testGpsOnce(){
        mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new TestLocationListener(), null);
        mLocationManager.addGpsStatusListener(new GpsListener());
    }


    public void testGps(){
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, new TestLocationListener());
        mLocationManager.addGpsStatusListener(new GpsListener());
    }


    private class TestLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            //showToastMessage(location.toString());
            showMessage(location.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(LOG_TAG, "onStateChanged:"+provider+" status"+status+" "+extras.toString());
            //GPS状态为可见时
            switch (status){
                case LocationProvider.AVAILABLE:
                    Log.i(LOG_TAG, "当前GPS状态为可见状态");
                    showMessage("当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(LOG_TAG, "当前GPS状态为服务区外状态");
                    showMessage("当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(LOG_TAG, "当前GPS状态为暂停服务状态");
                    showMessage("当前GPS状态为暂停服务状态");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(LOG_TAG, "onProviderEnable:"+provider);
            showMessage("onProviderEnable:"+provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(LOG_TAG, "onProviderDisabled:"+provider);
            showMessage("onProviderDisEnable:"+provider);

        }

    }


    private class GpsListener implements GpsStatus.Listener {

        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i(LOG_TAG, "第一次定位");
                    showMessage("第一次定位 ");
                    break;
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.i(LOG_TAG, "卫星状态改变:");
                    //获取当前状态
                    GpsStatus gpsStatus= mLocationManager.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                    Log.i(LOG_TAG, "搜索到："+count+"颗卫星");
                    showMessage("搜索到"+count+"颗卫星");
                    break;
                //定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.i(LOG_TAG, "定位启动");
                    showMessage("定位成功");
                    break;
                //定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i(LOG_TAG, "定位结束");
                    showMessage("定位失败");
                    break;
            }
        }
    }


    private void showMessage(String msg){
        sb.append(msg).append("\n");
        mTextView.setText(sb.toString());
    }

}
