package com.yumodev.google.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.yumo.common.log.Log;
import com.yumodev.google.util.LocationModule;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by yumo on 2018/5/9.
 */

public class GoogleLocationModule implements
        LocationModule,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {
    private String LOG_TAG = "GoogleLocationModule";
    private Context context;
    private Activity mActivity;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    public static LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    protected String mLastUpdateTime;
    protected Boolean mRequestingLocationUpdates = false;
    protected Boolean mStartLocationUpdates = false;
    private LocationChangedListener locationChangedListener;

    public GoogleLocationModule(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void LocationInit(Context context) {
        this.context = context;
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();  //检查并请求打开位置权限
    }

    @Override
    public void startLocationUpdates() {
        if (!mGoogleApiClient.isConnected()){//未连接，设置标志位，连接时启动
            mRequestingLocationUpdates = true;
        }else{                              //已连接，判断是否已启动
            if (mStartLocationUpdates)
                return;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            );
            mStartLocationUpdates = true;
        }
    }

    @Override
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        );
        mRequestingLocationUpdates = false;
        mStartLocationUpdates = false;
    }

    @Override
    public void googleClientConnect() {
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    public void googleClientDisconnect() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void setLocationChangedListener(LocationChangedListener locationChangedListener) {
        this.locationChangedListener = locationChangedListener;
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    protected synchronized void buildGoogleApiClient() {
        Log.d(LOG_TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        locationChangedListener.onLocationChanged(location);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "GoogleApiClient onConnected");
        if (mRequestingLocationUpdates)
            startLocationUpdates();
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            if (mCurrentLocation != null) {
                //T.showShort(context, "getLastLocation: " + mCurrentLocation.getLatitude());
                Log.d(LOG_TAG, "mCurrentLocation: " + mCurrentLocation.getAltitude());
            } else {
                Log.d(LOG_TAG, "mCurrentLocation null");
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "GoogleApiClient onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "GoogleApiClient onConnectionFailed: " + connectionResult.getErrorMessage());
    }

    //位置权限请求返回结果
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.d(LOG_TAG, "All location settings are satisfied.");
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.d(LOG_TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.w(LOG_TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                //T.showShort(context, context.getString(R.string.map_open_gps_remind));
                Log.e(LOG_TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                break;
        }
    }


    public static interface LocationChangedListener{
        void onLocationChanged(Location location);
    }
}
