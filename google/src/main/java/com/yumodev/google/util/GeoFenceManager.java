package com.yumodev.google.util;

/**
 * Created by yumo on 2018/5/9.
 */

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.yumo.common.android.PermissionUtil;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmCollectionUtil;

public class GeoFenceManager implements  GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{
    private final String LOG_TAG = "GeoFenceManager";
    private static final String FENCE_ID = "GEOFENCE_";

    private static GeoFenceManager INSTANCE = null;

    private GoogleApiClient mGoogleApiClient;
    private PendingIntent intent;
    private List<Geofence> mGeoFences;
    private Context mContext;

    private AtomicBoolean isConnected = new AtomicBoolean(false);
    private AtomicBoolean isSetted = new AtomicBoolean(false);

    private Location mLocation = null;

    public synchronized static GeoFenceManager getInstance(Context context, PendingIntent intent) {
        if (INSTANCE == null) {
            INSTANCE = new GeoFenceManager(context, intent);
        }
        return INSTANCE;
    }

    private GeoFenceManager(Context context, final PendingIntent intent) {
        mContext = context;
        mGeoFences = new ArrayList<>();
        this.intent = intent;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOG_TAG, "onConnected");
        // success
        isConnected.set(true);

        if (mLocation != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addGeoFence(mLocation);
                }
            }).start();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "onConnected Suspended");
        isConnected.set(false);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "ConnectionFailed");
        isConnected.set(false);
    }

    /**
     * get geoFence set status
     * @return
     */
    public boolean isSetted() {
        return isSetted.get();
    }

    /**
     * get google service connect status
     * @return
     */
    public boolean isConnected() {
        return isConnected.get();
    }

    /**
     * set geoFence
     * @param location
     */
    public void addGeoFence(Location location) {
        if (!isConnected()){
            mLocation = location;
            return;
        }
        getGeoFence(location.getLatitude(), location.getLongitude(), Arrays.asList(100, 100, 100));
        if (YmCollectionUtil.isEmpty(mGeoFences)) {
            return;
        }

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_EXIT);
        builder.addGeofences(mGeoFences);
        GeofencingRequest geoRequest = builder.build();

        if (!PermissionUtil.checkLocationPermission(mContext)){
            return;
        }

        PendingResult<Status> result = LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, mGeoFences, intent);
        Status status = result.await();
        if(status.isSuccess()){
            isSetted.set(true);
        }
    }

    private void getGeoFence(double latitude, double longitude, List<Integer> radiusList) {
        removeGeoFence();
        mGeoFences.clear();
        for (int i = 0 ; i < radiusList.size() ; i++) {
            mGeoFences.add(new Geofence.Builder()
                    .setCircularRegion(
                            latitude,
                            longitude,
                            radiusList.get(i)
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)//用不过期
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)//表示用户离开地址栏
                    .setRequestId(FENCE_ID+i)
                    .build());
        }
    }

    /**
     * remove geoFence of intent
     */
    public void removeGeoFence(){
        LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, intent);
        isSetted.set(false);
    }

    /**
     * connect google service
     */
    public void connnect() {
        mGoogleApiClient.connect();
    }

    /**
     * disconnect google service
     */
    public void disconnect() {
        mGoogleApiClient.disconnect();
    }

}
