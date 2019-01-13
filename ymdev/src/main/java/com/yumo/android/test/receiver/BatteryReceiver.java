/**
 * BatteryReceiver.java
 * yumo
 * 2015-3-15
 * TODO
 */
package com.yumo.android.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * yumodev
 * 电池监听
 */
public class BatteryReceiver extends BroadcastReceiver {

    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            Bundle bundle = intent.getExtras();
            int level = bundle.getInt("level");
            int scale = bundle.getInt("scale");
            StringBuffer buffer = new StringBuffer();
            buffer.append("当前电量为：" + level * 100 / scale + "%");

            if (level * 1.0 / scale < 0.1) {
                buffer.append("当前电量偏低请充电。");
            } else {
                buffer.append("电量足够");
            }

            Log.d("BatteryReceiver", buffer.toString());

        } else if (action.equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(context, "Intent.ACTION_BATTERY_LOW", Toast.LENGTH_SHORT).show();
        } else if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
            Toast.makeText(context, "Intent.ACTION_BATTERY_OKAY", Toast.LENGTH_SHORT).show();
        }
    }

}
