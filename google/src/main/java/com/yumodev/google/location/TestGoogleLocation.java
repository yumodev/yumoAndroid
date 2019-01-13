package com.yumodev.google.location;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsApi;
import com.yumo.common.android.PermissionUtil;
import com.yumo.common.android.YmLocationUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.google.Define;
import com.yumodev.google.MyReceiver;
import com.yumodev.google.service.LocationService;
import com.yumodev.google.util.GeoFenceManager;
import com.yumodev.google.util.GoogleLocationModule;

/**
 * Created by yumo on 2018/5/9.
 */

public class TestGoogleLocation extends YmTestFragment {
    private final String LOG_TAG = TestGoogleLocation.class.getSimpleName();
    private FusedLocationProviderApi mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLocation = null;
    private MyReceiver myReceiver;
    public void testA() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.FusedLocationApi;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyReceiver.GEO_FENCE);
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showToastMessage(connectionResult.getErrorMessage()+connectionResult.toString());
                    }
                })
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        //GeoFenceManager.getInstance(getActivity(), null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
    }

    public void testLastLocation() {
        FusedLocationProviderApi fusedLocationClient = LocationServices.FusedLocationApi;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLocation = fusedLocationClient.getLastLocation(mGoogleApiClient);
        showToastMessage(YmLocationUtil.formatLocation(mLocation));

        Log.i(Define.TAG_LOCATION, YmLocationUtil.formatLocation(mLocation), true);

//        fusedLocationClient.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                showToastMessage(location.toString());
//                Log.i("location", location.toString()+" "+location.getLatitude()+" "+location.getLongitude());
//                mLocation = location;
//            }
//        });
    }

    public void testGoogleLocation() {
        SettingsApi client = LocationServices.SettingsApi;
        //FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext().getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        client.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                showToastMessage(location.toString());
//                Log.i("location", location.toString()+" "+location.getLatitude()+" "+location.getLongitude());
//                mLocation = location;
//            }
//        });
    }

    public void testLocation() {
        mFusedLocationClient = LocationServices.FusedLocationApi;
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (PermissionUtil.checkLocationPermission(getActivity())){
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showToastMessage(location.toString());
            }
        });
    }

    public void testLocationModule(){
        GoogleLocationModule locationModule = new GoogleLocationModule(getActivity());
        locationModule.LocationInit(getActivity());
        locationModule.googleClientConnect();
        locationModule.setLocationChangedListener(new GoogleLocationModule.LocationChangedListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null){
                    showToastMessage(location.toString());
                    mLocation = location;
                    Log.i("location", location.toString()+" "+location.getLatitude()+" "+location.getLongitude());
                }

            }
        });
        locationModule.startLocationUpdates();
    }

    public void testGeoFence(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(MyReceiver.GEO_FENCE);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    GeoFenceManager.getInstance(getActivity(), pendingIntent);
                    GeoFenceManager.getInstance(getActivity(), pendingIntent).addGeoFence(mLocation);
                }catch (Exception e){
                    showToastMessage(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void testShowNotifitation(){
        Intent intent = new Intent(this.getContext(), MyReceiver.class);
        intent.setAction(MyReceiver.GEO_FENCE);
        getActivity().sendBroadcast(intent);
    }

    public void testSeviceConnected(){
        servicesConnected();
    }
    /**
     * 驗證Google Play Services是否可用
     *
     * @return 可用返回真否則返回假
     */
    private boolean servicesConnected() {
        // 檢查服務是否可用 2: 需更新
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        // 如果可用
        if (ConnectionResult.SUCCESS == resultCode) {
            // Log狀態
            Log.d(LOG_TAG, "谷歌服务可用");
            showToastMessage("谷歌服务可用");
            return true;
            // 如果不可用
        } else {
            // 顯示錯誤提示對話框
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = ErrorDialogFragment.newInstance(dialog, null);
                errorFragment.show(getActivity().getFragmentManager(), LOG_TAG);
            }
            return false;
        }
    }

    public void testStartService(){
        getContext().startService(new Intent(getContext(), LocationService.class));
    }
}
