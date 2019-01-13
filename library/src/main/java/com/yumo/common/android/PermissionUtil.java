package com.yumo.common.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by yumo on 2018/5/9.
 */

public class PermissionUtil {

    public static void requestPermission(Activity activity){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Log.i("permissions", Manifest.permission.ACCESS_FINE_LOCATION + "：" + permission.granted);
                        }else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Log.i("permissions", Manifest.permission.WRITE_EXTERNAL_STORAGE + "：" + permission.granted);
                        }else if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                            Log.i("permissions", Manifest.permission.READ_PHONE_STATE + "：" + permission.granted);
                        }
                    }
                });
    }


    public static boolean checkLocationPermission(Context activity){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }

        return true;
    }
}
