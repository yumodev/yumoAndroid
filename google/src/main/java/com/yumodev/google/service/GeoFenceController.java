package com.yumodev.google.service;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.yumo.common.android.PermissionUtil;
import com.yumo.common.android.YmLocationUtil;
import com.yumo.common.log.Log;
import com.yumodev.google.Define;
import com.yumodev.google.MyReceiver;
import com.yumodev.google.util.GeoFenceManager;

/**
 * Created by yumo on 2018/5/11.
 */

public class GeoFenceController implements GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks
{
    private Context mContext = null;
    private GoogleApiClient mGoogleApiClient = null;

    public void init(Context context){
        mContext = context;
        buildGoogleApiClient();
    }


    protected synchronized void buildGoogleApiClient() {
        Log.d(Define.TAG_LOCATION, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(Define.TAG_LOCATION, "onConnected");
        getLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(Define.TAG_LOCATION, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(Define.TAG_LOCATION, "onConnectionFailed");
    }


    public void getLastLocation() {

        if (!PermissionUtil.checkLocationPermission(mContext)){
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        try {
            Intent intent = new Intent(MyReceiver.GEO_FENCE);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            GeoFenceManager.getInstance(mContext, pendingIntent).addGeoFence(location);
        }catch (Exception e){
            Log.e(Define.TAG_LOCATION, e.getMessage());
            e.printStackTrace();
        }
    }
}
