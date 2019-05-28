package com.yumodev.process.location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.text.TextUtils;

import com.yumo.common.android.YmLocationUtil;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmDateUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.util.LocationUtil;
import com.yumodev.process.util.MediaClientService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yumo on 2018/3/30.
 * https://blog.csdn.net/qq_33689414/article/details/54136922
 */

public class TestLocation extends YmTestFragment {
    private final String LOG_TAG = "location";

    public void testA() {
        showToastMessage("hello");
    }

    public void testB() {
        showToastMessage("B");
    }

    public void testC() {
        showToastMessage("D");
    }

    public void testHasGps(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 0);
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showToastMessage("已启用 gps");
        }else{
            showToastMessage("disable gps");
        }
    }

    public void testOpenGPS() {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(getContext(), 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void testOpenGPS1(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public void testLocationGPS() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 0);
            showToastMessage("没有位置权限");
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i(LOG_TAG, location.toString() + " " + YmDateUtil.getStrFromTime(location.getTime()));
            showToastMessage(YmLocationUtil.formatLocation(location));
        }else{
            showToastMessage("disable gps");
        }
    }

    public void testLocationNet() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 0);
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.i(LOG_TAG, location.toString() + " " + YmDateUtil.getStrFromTime(location.getTime()));
            showToastMessage(YmLocationUtil.formatLocation(location));
        }else{
            showToastMessage("disable netxss");
        }
    }

    public void testBestLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = null;
        if (!checkoutPermission()) {
            return;
        }
        if (!TextUtils.isEmpty(provider)) {
            location = locationManager.getLastKnownLocation(provider);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        Log.i(LOG_TAG, location.toString() + " " + YmDateUtil.getStrFromTime(location.getTime()));
        showToastMessage(YmLocationUtil.formatLocation(location));
    }

    public void testBestLocation1() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                showToastMessage("未获取权限");

                return;
            }

            Location l = null;
            while (l == null) {
                l = locationManager.getLastKnownLocation(provider);

            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if (bestLocation != null){
            Log.i(LOG_TAG, bestLocation.toString()+" "+YmDateUtil.getStrFromTime(bestLocation.getTime()));

            showToastMessage(bestLocation.toString());
        }else{
            showToastMessage("获取失败");
        }
    }

    private boolean checkoutPermission(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 0);
            return false;
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 0);
            return false;
        }
        return true;
    }

    public void testAddLocationListener(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!checkoutPermission()){
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged"+location.getTime()+" "+ YmDateUtil.getStrFromTime(location.getTime()));
                Log.i(LOG_TAG, "onLocationChanged"+location.toString());
                //showToastMessage(location.toString());
                showToastMessage(YmLocationUtil.formatLocation(location));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStateChanged:"+provider+" status"+status+" "+extras.toString());
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(LOG_TAG, "onProviderEnable:"+provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(LOG_TAG, "onProviderDisabled:"+provider);
            }
        });
    }

    public void testGPSLocationListener(){
        final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!checkoutPermission()){
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged"+YmLocationUtil.formatLocation(location));
                //showToastMessage(location.toString());
                showToastMessage(YmLocationUtil.formatLocation(location));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStateChanged:"+provider+" status"+status+" "+extras.toString());
                //GPS状态为可见时
                switch (status){
                    case LocationProvider.AVAILABLE:
                        Log.i(LOG_TAG, "当前GPS状态为可见状态");
                        showToastMessage("当前GPS状态为可见状态");
                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i(LOG_TAG, "当前GPS状态为服务区外状态");
                        showToastMessage("当前GPS状态为服务区外状态");
                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i(LOG_TAG, "当前GPS状态为暂停服务状态");
                        showToastMessage("当前GPS状态为暂停服务状态");
                        break;
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(LOG_TAG, "onProviderEnable:"+provider);
                showToastMessage("onProviderEnable:"+provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(LOG_TAG, "onProviderDisabled:"+provider);
                showToastMessage("onProviderDisEnable:"+provider);

            }
        });

        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    //第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.i(LOG_TAG, "第一次定位");
                        showToastMessage("第一次定位 ");
                        break;
                    //卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.i(LOG_TAG, "卫星状态改变");
                        //获取当前状态
                        GpsStatus gpsStatus= locationManager.getGpsStatus(null);
                        //获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        //创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                        int count = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = iters.next();
                            count++;
                        }
                        System.out.println("搜索到："+count+"颗卫星");
                        break;
                    //定位启动
                    case GpsStatus.GPS_EVENT_STARTED:
                        Log.i(LOG_TAG, "定位启动");
                        showToastMessage("定位成功");
                        break;
                    //定位结束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        Log.i(LOG_TAG, "定位结束");
                        showToastMessage("定位失败");
                        break;
                }

            }
        });
    }

    public void testNetWorkListenerInThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

                if (!checkoutPermission()){
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(LOG_TAG, "onLocationChanged"+YmLocationUtil.formatLocation(location));
                        //showToastMessage(location.toString());
                        showToastMessage(YmLocationUtil.formatLocation(location));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.i(LOG_TAG, "onStateChanged:"+provider+" status"+status+" "+extras.toString());
                        //GPS状态为可见时
                        switch (status){
                            case LocationProvider.AVAILABLE:
                                Log.i(LOG_TAG, "当前GPS状态为可见状态");
                                showToastMessage("当前GPS状态为可见状态");
                                break;
                            //GPS状态为服务区外时
                            case LocationProvider.OUT_OF_SERVICE:
                                Log.i(LOG_TAG, "当前GPS状态为服务区外状态");
                                showToastMessage("当前GPS状态为服务区外状态");
                                break;
                            //GPS状态为暂停服务时
                            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                                Log.i(LOG_TAG, "当前GPS状态为暂停服务状态");
                                showToastMessage("当前GPS状态为暂停服务状态");
                                break;
                        }
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Log.i(LOG_TAG, "onProviderEnable:"+provider);
                        showToastMessage("onProviderEnable:"+provider);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Log.i(LOG_TAG, "onProviderDisabled:"+provider);
                        showToastMessage("onProviderDisEnable:"+provider);

                    }
                });

                Looper.loop();

            }
        }).start();

    }


    public void testUtil(){
        LocationUtil.register(getContext(), 5000, 1, new LocationUtil.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                Log.i(LOG_TAG, "getLastKnownLocation"+location.toString());
                showToastMessage(location.toString());
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged"+location.toString());
                if (isAdded()){
                    showToastMessage(location.toString());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStatusChanged "+ provider + " status");

            }
        });
    }

    public void testPlay(){
        MediaClientService.getInstance().startDrvingPlayer();
    }


    public void testIsGeoCodeerPersent(){
        Geocoder geocoder = new Geocoder(getContext());
        if (geocoder.isPresent()){
            showToastMessage("GeoCode可用");
        }else {
            showToastMessage("GeoCode不可用");
        }
    }


    public void testGeoAddress(){
       getAddress(LocationUtil.getLocation(getContext()), getContext());
    }

    private String getAddress(Location location, Context context) {
        //Geocoder初始化
        Geocoder geocoder = new Geocoder(context);
        //判断Geocoder地理编码是否可用
        boolean falg = geocoder.isPresent();
        if (!falg){
            showToastMessage("GeoCode不可用");
            return "";
        }
        try {
            //获取纬度和经度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //根据经纬度获取地理信息
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                //返回当前位置，精度可调
                Address address = addresses.get(0);
                showToastMessage(address.toString());
                String sAddress;
                if (!TextUtils.isEmpty(address.getLocality())) {
                    if (!TextUtils.isEmpty(address.getFeatureName())) {
                        //存储 市 + 周边地址
                        sAddress = address.getLocality() + " " + address.getFeatureName();

                        //address.getCountryName() 国家
                        //address.getPostalCode() 邮编
                        //address.getCountryCode() 国家编码
                        //address.getAdminArea() 省份
                        //address.getSubAdminArea() 二级省份
                        //address.getThoroughfare() 道路
                        //address.getSubLocality() 二级城市
                    } else {
                        sAddress = address.getLocality();
                    }
                } else {
                    sAddress = "定位失败";
                }
                return sAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
