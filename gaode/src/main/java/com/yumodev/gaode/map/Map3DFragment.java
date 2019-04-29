package com.yumodev.gaode.map;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.yumo.common.android.YmToastUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.gaode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumo on 2018/6/14.
 */

public class Map3DFragment extends YmTestFragment {

    private String[] mListArrs =
            {"SHOW_NORMAL",
                    "SHOW_LOCATION_STYLE",
                    "SHOW_NAVI",
                    "SHOW_NIGHT",
                    "SHOW_SATELLITE",
                    "SHOW_BUS",
                    "SHOW_TRAFFIC",//交通图
                    "SHOW_INDOOR_MAP",//室内图
                    "SHOW_ENGLISH",//英文地图
                    "SHOW_ZOOM",//缩放控件
                    "SHOW_COMPASSE",//指南针
                    "GESTURE", //关闭所有手势
                    "CHANGE_CENTER",//移动中心点
                    "MARKER",//做标记
                    "POLYLINE",//画线
                    "CIRCLE",//画圆形
                    "TILEOVERLAY"//热力图

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

                initMap();
                String type = mListArrs[pos];
                if (mListArrs[pos].equals("SHOW_LOCATION_STYLE")) {
                    showLocationStyle();
                } else if (type.equals("SHOW_NAVI")) {
                    mAmap.setMapType(AMap.MAP_TYPE_NAVI);
                } else if (mListArrs[pos].equals("SHOW_NORMAL")) {
                    //设置白昼地图
                    if (mAmap != null) {
                        mAmap.setMapType(AMap.MAP_TYPE_NORMAL);
                    }
                    mAmap.setMapType(AMap.MAP_TYPE_NORMAL);
                } else if (mListArrs[pos].equals("SHOW_NAVI")) {
                    //设置导航地图
                    mAmap.setMapType(AMap.MAP_TYPE_NAVI);
                } else if (mListArrs[pos].equals("SHOW_NIGHT")) {
                    //设置夜间地图
                    mAmap.setMapType(AMap.MAP_TYPE_NIGHT);
                } else if (mListArrs[pos].equals("SHOW_BUS")) {
                    //设置夜间地图
                    mAmap.setMapType(AMap.MAP_TYPE_BUS);
                } else if (mListArrs[pos].equals("SHOW_SATELLITE")) {
                    //设置卫星图
                    mAmap.setMapType(AMap.MAP_TYPE_SATELLITE);
                } else if (mListArrs[pos].equals("SHOW_INDOOR_MAP")) {
                    //开启室内地图后，如果可见区域内包含室内地图覆盖区域（如：凯德Mall等知名商场），且缩放达到一定级别，便可直接在地图上看到精细室内地图效果。
                    //缩放级别≥17级时，地图上可以显示室内地图。
                    //缩放级别≥18级时，不仅可以看到室内地图效果，还允许操作切换楼层，显示精细化室内地图。
                    mAmap.showIndoorMap(true);
                } else if (mListArrs[pos].equals("SHOW_TRAFFIC")) {
                    //是否显示交通图
                    if (mAmap.isTrafficEnabled()) {
                        mAmap.setTrafficEnabled(false);
                    } else {
                        mAmap.setTrafficEnabled(true);
                    }
                } else if (type.equals("SHOW_ENGLISH")) {
                    mAmap.setMapLanguage(AMap.ENGLISH);
                } else if (type.equals("SHOW_ZOOM")) {
                    /**
                     * 是否允许显示缩放按钮
                     */
                    mUiSettings = mAmap.getUiSettings();
                    if (mUiSettings.isZoomControlsEnabled()) {
                        mUiSettings.setZoomControlsEnabled(true);
                        mUiSettings.setZoomPosition(mUiSettings.getZoomPosition() + 100);
                    } else {
                        mUiSettings.setZoomControlsEnabled(false);
                    }
                } else if (type.equals("SHOW_COMPASS")) {
                    /**
                     * 指南针，用于向App端展示地图方向， 默认不显示
                     */
                    if (mUiSettings.isCompassEnabled()) {
                        mUiSettings.setCompassEnabled(true);
                    } else {
                        mUiSettings.setCompassEnabled(false);
                    }
                } else if (type.equals("GESTURE")) {
                    mUiSettings.setAllGesturesEnabled(false);
                } else if (type.equals("CHANGE_CENTER")) {
                    changeCenter();
                } else if (type.equals("MARKER")) {
                    showMarker();
                } else if (type.equals("POLYLINE")) {
                    showPliyline();
                } else if (type.equals("CIRCLE")) {
                    showCircle();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_id, mapFragment).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        initMap();
    }

    private void initMap() {
        if (mAmap != null) {
            return;
        }

        mapFragment = ((SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment_id));

        if (mapFragment == null) {
            return;
        }

        mAmap = mapFragment.getMap();
        if (mAmap != null) {
            mUiSettings = mAmap.getUiSettings();
            mAmap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    //YmToastUtil.showMessage("定位改变：" + location.toString());
                }
            });


            mAmap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    YmToastUtil.showMessage("地图加载完成");
                }
            });
        }

    }

    /**
     * 显示定位蓝点。
     */
    private void showLocationStyle() {
        mAmap = mapFragment.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(android.R.color.holo_red_dark);
        myLocationStyle.strokeWidth(20);
        myLocationStyle.anchor(500, 500);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        myLocationStyle.showMyLocation(true);

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
    }

    private void changeCenter() {
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(39.977290, 116.337000), 18, 30, 0));

        mAmap.animateCamera(mCameraUpdate, 1000, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
                YmToastUtil.showMessage("onFinish");
            }

            @Override
            public void onCancel() {
                YmToastUtil.showMessage("onFailed");

            }
        });

    }

    private void showMarker() {
        LatLng latLng = new LatLng(39.906901, 116.397972);
        final Marker marker = mAmap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
    }

    private void showPliyline() {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(39.999391, 116.135972));
        latLngs.add(new LatLng(39.898323, 116.057694));
        latLngs.add(new LatLng(39.900430, 116.265061));
        latLngs.add(new LatLng(39.955192, 116.140092));
        Polyline polyline = mAmap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
    }


    private void showCircle() {
        LatLng latLng = new LatLng(39.984059, 116.307771);
        Circle circle = mAmap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                fillColor(Color.argb(255, 1, 1, 1)).
                strokeColor(Color.argb(255, 1, 1, 1)).
                strokeWidth(15));
    }

    //3d 地图才可以
    private void showTileOverlay() {
        //生成热力点坐标列表
        LatLng[] latlngs = new LatLng[500];
        double x = 39.904979;
        double y = 116.40964;

        for (int i = 0; i < 500; i++) {
            double x_ = 0;
            double y_ = 0;
            x_ = Math.random() * 0.5 - 0.25;
            y_ = Math.random() * 0.5 - 0.25;
            latlngs[i] = new LatLng(x + x_, y + y_);
        }

//        // 构建热力图 HeatmapTileProvider
//        TileProvider.Builder builder = new TileProvider.Builder();
//        builder.data(Arrays.asList(latlngs)) // 设置热力图绘制的数据
//                .gradient(ALT_HEATMAP_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
//// Gradient 的设置可见参考手册
//// 构造热力图对象
//        HeatmapTileProvider heatmapTileProvider = builder.build();
//
//
//        // 初始化 TileOverlayOptions
//        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
//        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
//// 向地图上添加 TileOverlayOptions 类对象
//        mAmap.addTileOverlay(tileOverlayOptions);

    }


}
