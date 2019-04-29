package com.yumodev.gaode.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MarkerOptions;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.gaode.R;
import com.yumodev.gaode.util.Constants;


/**
 * Created by yumo on 2018/7/9.
 */

public class Map3DInteractionFragment extends YmTestFragment {
    private String[] mListArrs = {
            "SHOW_ZOOM",//缩放控件
            "SHOW_ZOOM_RIGHT_BUTTOM",//缩放控件
            "SHOW_ZOOM_RIGHT_CENTER",//缩放控件
            "SHOW_COMPASS",//指南针
            "SHOW_GESTURE", //关闭所有手势
            "SHOW_LOCATION", //是否显示定位
            "SHOW_LOCATION_BUTTON", //是否显示定位
            "SCALE_CONTROL", //显示比例尺
            "SHOW_SCALE_MESSAGE",// 显示像素
            "SHOW_LOGO_MARGIN_LEFT",//左边
            "SHOW_LOGO_MARGIN_BOTTOM",//底部
            "SHOW_LOGO_MARGIN_RIGHT",//右边
            "SHOW_LOGO_POSITION_BOTTOM_CENTER",//底部剧中
            "SHOW_LOGO_POSITION_BOTTOM_LEFT",//左下角
            "SHOW_LOGO_POSITION_BOTTOM_RIGHT",//右下角
            "SHOW_ZOOM_GESTURE",
            "SHOW_SCROLL_GESTURE",
            "SHOW_ROTATE_GESTURE",
            "SHOW_TILT_GESTURE",
            "CENTER_ZHONGGUNCUN",
            "CAMERA_ZOOM_IN",//缩小
            "CAMERA_ZOOM_OUT",//放大

    };


    private AMap mAmap;
    private UiSettings mUiSettings;
    private MapView mMapView;
    private Spinner mSpinner;
    private SupportMapFragment mapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_base_fragment, null);
        initView(view);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void initView(View root) {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mListArrs);
        mSpinner = root.findViewById(R.id.spinner);
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                String type = mListArrs[pos];
                initMap();
                if (type.equals("SHOW_ZOOM")) {
                    /**
                     * 是否允许显示缩放按钮
                     */
                    mUiSettings = mAmap.getUiSettings();
                    if (mUiSettings.isZoomControlsEnabled()) {
                        mUiSettings.setZoomControlsEnabled(false);
                        mUiSettings.setZoomPosition(mUiSettings.getZoomPosition() + 100);
                    } else {
                        mUiSettings.setZoomControlsEnabled(true);
                        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
                    }
                } else if (type.equals("SHOW_ZOOM_RIGHT_BUTTOM")) {
                    mUiSettings.setZoomControlsEnabled(true);
                    mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);

                } else if (type.equals("SHOW_ZOOM_RIGHT_CENTER")) {
                    mUiSettings.setZoomControlsEnabled(true);
                    mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
                } else if (type.equals("SHOW_COMPASS")) {
                    /**
                     * 指南针，用于向App端展示地图方向， 默认不显示
                     */
                    if (mUiSettings.isCompassEnabled()) {
                        mUiSettings.setCompassEnabled(false);
                    } else {
                        mUiSettings.setCompassEnabled(true);
                    }
                } else if (type.equals("SHOW_GESTURE")) {
                    //启用禁用
                    if (!mUiSettings.isRotateGesturesEnabled()) {
                        mUiSettings.setAllGesturesEnabled(true);
                    } else {
                        mUiSettings.setAllGesturesEnabled(false);
                    }
                } else if (type.equals("SHOW_LOCATION_BUTTON")){
                    if (mUiSettings.isMyLocationButtonEnabled()){
                        mUiSettings.setMyLocationButtonEnabled(false);
                    }else {
                        mUiSettings.setMyLocationButtonEnabled(true);
                    }
                }else if (type.equals("SHOW_LOCATION")){
                    if (mAmap.isMyLocationEnabled()){
                       mAmap.setMyLocationEnabled(false);
                    }else {
                        mAmap.setMyLocationEnabled(true);
                    }
                }else if (type.equals("SCALE_CONTROL")) {
                    if (mUiSettings.isScaleControlsEnabled()) {
                        mUiSettings.setScaleControlsEnabled(false);

                    } else {
                        mUiSettings.setScaleControlsEnabled(true);
                    }
                }else if (type.equals("SHOW_SCALE_MESSAGE")){
                    showToastMessage("每像素："+mAmap.getScalePerPixel()+"米");
                }else if (type.equals("SHOW_LOGO_MARGIN_LEFT")){
                    mUiSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_LEFT);
                }else if (type.equals("SHOW_LOGO_MARGIN_BOTTOM")){
                    mUiSettings.setLogoLeftMargin(AMapOptions.LOGO_MARGIN_BOTTOM);
                }else if (type.equals("SHOW_LOGO_MARGIN_RIGHT")){
                    mUiSettings.setLogoLeftMargin(AMapOptions.LOGO_MARGIN_RIGHT);
                }else if (type.equals("SHOW_LOGO_POSITION_BOTTOM_LEFT")){
                    mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
                }else if (type.equals("SHOW_LOGO_POSITION_BOTTOM_CENTER")){
                    mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
                }else if (type.equals("SHOW_LOGO_POSITION_BOTTOM_RIGHT")){
                    mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
                }else if (type.equals("SHOW_ZOOM_GESTURE")){//缩放手势
                    if (mUiSettings.isZoomGesturesEnabled()){
                        mUiSettings.setZoomGesturesEnabled(false);
                    }else{
                        mUiSettings.setZoomGesturesEnabled(true);
                    }
                }else if (type.equals("SHOW_SCROLL_GESTURE")){//滑动手势
                    if (mUiSettings.isScrollGesturesEnabled()){
                        mUiSettings.setScrollGesturesEnabled(false);
                    }else{
                        mUiSettings.setScrollGesturesEnabled(true);
                    }
                }else if (type.equals("SHOW_ROTATE_GESTURE")){//旋转3D手势地图
                    if (mUiSettings.isRotateGesturesEnabled()){
                        mUiSettings.setRotateGesturesEnabled(false);
                    }else{
                        mUiSettings.setRotateGesturesEnabled(true);
                        //mAmap.setMapType(Amap.);
                    }
                }else if (type.equals("SHOW_TILT_GESTURE")){//倾斜手势
                    if (mUiSettings.isTiltGesturesEnabled()){
                        mUiSettings.setTiltGesturesEnabled(false);
                    }else{
                        mUiSettings.setTiltGesturesEnabled(true);
                    }
                }else if (type.equals("CENTER_ZHONGGUNCUN")){
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(Constants.ZHONGGUANCUN, 18, 0,0));
                    mAmap.moveCamera(cameraUpdate);
                    mAmap.clear();
                    mAmap.addMarker(new MarkerOptions().position(Constants.ZHONGGUANCUN)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }else if (type.equals("CAMERA_ZOOM_IN")){
                    changeCamrea(true, CameraUpdateFactory.zoomIn(), new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            showToastMessage("onFinish");
                        }

                        @Override
                        public void onCancel() {
                            showToastMessage("onCancel");
                        }
                    });
                }else if (type.equals("CAMERA_ZOOM_OUT")){
                    changeCamrea(true, CameraUpdateFactory.zoomOut(), new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            showToastMessage("onFinish");
                        }

                        @Override
                        public void onCancel() {
                            showToastMessage("onCancel");
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_id, mapFragment).commit();
        initMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAmap != null){
            mUiSettings = mAmap.getUiSettings();
        }

        initMap();
    }

    private void initMap() {
        if (mAmap == null){
            mAmap = mapFragment.getMap();

            if (mAmap != null){
                mAmap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                    @Override
                    public void onMapLoaded() {
                        showToastMessage("地图加载完毕："+mAmap.getCameraPosition().toString());
                    }
                });

                mAmap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        //showToastMessage("onCameraChange："+mAmap.getCameraPosition().toString());

                    }

                    @Override
                    public void onCameraChangeFinish(CameraPosition cameraPosition) {
                        showToastMessage("onCameraChangeFinish："+mAmap.getCameraPosition().toString());

                    }
                });
            }

        }

    }


    private void changeCamrea(boolean isAnima, CameraUpdate cameraUpdate, AMap.CancelableCallback callback){
        if (isAnima){
            mAmap.animateCamera(cameraUpdate,1000,  callback);
        }else{
            mAmap.moveCamera(cameraUpdate);
        }
    }
}
