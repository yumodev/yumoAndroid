package com.yumodev.gaode.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/7.
 */

public class TestMap extends YmTestFragment {

    AMap mAap = null;
    MapView mAapView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAapView = new MapView(getContext());
        mAapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAapView.onSaveInstanceState(outState);
    }

    public void testShowMapView(){
        showTestView(mAapView);
    }

    public void testShowMapFragment(){
        SupportMapFragment fragment = new SupportMapFragment();
        showFragment(new SupportMapFragment());
        mAap = fragment.getMap();
        testShowLocationStyle();
    }

    public void testShow2dFragment(){
        showFragment(new MapFragment());
    }


    public void testShow3dFragment(){
        showFragment(new Map3DFragment());
    }

    public void testShowLocationStyle(){
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }



}
