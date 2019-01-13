/**
 * ConnectionReceiver.java
 * yumodev
 * 2015-3-15
 */
package com.yumo.android.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * yumodev
 * 监听网络变化广播
 */
public class ConnectionReceiver extends BroadcastReceiver {

    private final String TAG = ConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            do {
                if (networkInfo == null) {
                    Toast.makeText(context, "当前没有网络", Toast.LENGTH_SHORT).show();
                    break;
                }

                String netType = "";

                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = "wifi";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    netType = "mobile";
                } else {
                    netType = "" + networkInfo.getTypeName();
                }

                String netState = "";

                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    netState = "CONNECTED";
                } else if (networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                    netState = "CONNECTING";
                } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                    netState = "DISCONNECTED";
                } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTING) {
                    netState = "DISCONNECTING";
                } else if (networkInfo.getState() == NetworkInfo.State.SUSPENDED) {
                    netState = "SUSPENDED";
                } else if (networkInfo.getState() == NetworkInfo.State.UNKNOWN) {
                    netState = "UNKNOWN";
                }

                Log.d(TAG, "nettype:" + netType + "  netState:" + netState);
            } while (false);


        }

    }

}
